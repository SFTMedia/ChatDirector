package com.blalp.chatdirector.extra.modules.state;

import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.model.IItem;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class SerializeStateItem implements IItem {

    private String token;

    @Override
    public boolean isValid() {
        return ValidationUtils.hasContent(token) && token.length() > 32;
    }

    @Override
    public Context process(Context context) {
        try {
            ObjectMapper om = new ObjectMapper(new JsonFactory())
                    .setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
            String input = om.writeValueAsString(context);

            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(token.getBytes(), "HmacSHA512"));
            byte[] hash = mac.doFinal(input.getBytes());

            String output = new String(hash) + input;

            return new Context(output);
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
