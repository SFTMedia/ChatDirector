package com.blalp.chatdirector.discord.modules.discord;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import com.blalp.chatdirector.core.ChatDirector;
import com.blalp.chatdirector.core.model.Context;
import com.blalp.chatdirector.core.utils.ValidationUtils;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscordResolveItem extends DiscordItem {
	String server;

	boolean toDiscord, toPlain;

	@Override
	public Context process(Context context) {
		String s = context.getCurrent();
		DiscordApi api = ((DiscordBots) ChatDirector.getConfig().getOrCreateDaemon(DiscordBots.class)).get(bot)
				.getDiscordApi();
		Server serverObj = api.getServerById(server).get();
		String output = "";
		boolean found = false;
		for (int i = 0; i < s.length(); i++) {
			found = false;
			if (s.charAt(i) == '\n') {
				output += ' ';
			} else if (toPlain && (s.charAt(i) == '<' && i + 1 < s.length() && s.charAt(i + 1) == '@')) {
				for (int i1 = i; i1 < s.length(); i1++) {
					if (s.charAt(i1) == '>') {
						try {
							if (s.charAt(i + 2) == '!') {
								output += "@" + api.getUserById(s.substring(i + 3, i1)).get().getName();
							} else {
								output += "@" + api.getUserById(s.substring(i + 2, i1)).get().getName();
							}
							i += i1 - i;
						} catch (ExecutionException | InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			} else if (toPlain && (s.charAt(i) == '<' && i + 1 < s.length() && s.charAt(i + 1) == '#')) {
				for (int i1 = i; i1 < s.length(); i1++) {
					if (s.charAt(i1) == '>') {
						output += "#"
								+ api.getChannelById(s.substring(i + 2, i1)).get().asServerChannel().get().getName();
						i += i1 - i;
						break;
					}
				}
			} else if (toDiscord && s.charAt(i) == '@') {// i=10
				// System.out.println(s);
				if (!s.substring(i, s.length()).contains(" "))
					s += " ";
				// System.out.println(s.substring(i,s.length()));
				for (int i1 = i; i1 < s.length(); i1++) {
					// System.out.println(s.charAt(i1));
					if (s.charAt(i1) == ' ') {
						// System.out.println(">"+s.substring(i,i1)+"<");
						Collection<User> users = serverObj.getMembersByNicknameIgnoreCase(s.substring(i + 1, i1));
						if (!users.isEmpty()) {
							output += users.iterator().next().getMentionTag();
							i = i1 - 1;
							found = true;
							break;
						}
						users = serverObj.getMembersByDisplayNameIgnoreCase(s.substring(i + 1, i1));
						if (!users.isEmpty()) {
							output += users.iterator().next().getMentionTag();
							i = i1 - 1;
							found = true;
							break;
						}
						users = serverObj.getMembersByNameIgnoreCase(s.substring(i + 1, i1));
						if (!users.isEmpty()) {
							output += users.iterator().next().getMentionTag();
							i = i1 - 1;
							found = true;
							break;
						}
						break;
					}
				}
				if (!found) {
					// if we are still here that means it failed.
					output += '@';
				}
			} else if (toDiscord && s.charAt(i) == '#') {// i=10
				// System.out.println(s);
				if (!s.substring(i, s.length()).contains(" "))
					s += " ";
				// System.out.println(s.substring(i,s.length()));
				for (int i1 = i; i1 < s.length(); i1++) {
					// System.out.println(s.charAt(i1));
					if (s.charAt(i1) == ' ') {
						if (!serverObj.getChannelsByNameIgnoreCase(s.substring(i + 1, i1)).isEmpty()) {
							output += "<#"
									+ serverObj.getTextChannelsByName(s.substring(i + 1, i1)).get(0).getIdAsString()
									+ '>';
							found = true;
							i = i1 - 1;
						}
					}
				}
				if (!found) {
					// if we are still here it failed
					output += '#';
				}
			} else {
				output += s.charAt(i);
			}
		}
		return new Context(output);
	}

	@Override
	public boolean isValid() {
		return ValidationUtils.anyOf(toDiscord, toPlain) && ValidationUtils.hasContent(server) && super.isValid();
	}

}