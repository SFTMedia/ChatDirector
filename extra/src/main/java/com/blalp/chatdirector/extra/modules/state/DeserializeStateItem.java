package com.blalp.chatdirector.extra.modules.state;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class DeserializeStateItem implements IItem {

    private String token;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(token) && token.length() > 32;
    }

    @Override
    public Context process(Context context) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(token.getBytes(), "HmacSHA512"));
            String input = context.getCurrent().substring(mac.getMacLength());
            byte[] hash = mac.doFinal(input.getBytes());

            if (hash == context.getCurrent().substring(0, mac.getMacLength()).getBytes()) {
                // All is dandy
                ObjectMapper om = new ObjectMapper(new JsonFactory())
                        .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);

                return om.readValue(input, Context.class);
            } else {
                System.err.println("Someone attempted to run commands without the proper token " + input);
                return new Context().halt();
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SOMETHING IS SERIOUSLY WRONG WE NEED OUR HmacSHA512");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println("YOU GAVE ME A BAD TOKEN!");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.err.println("Could not serialize context");
            e.printStackTrace();
        }
        return new Context().halt();
    }

}
