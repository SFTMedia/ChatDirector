package com.blalp.chatdirector.modules.jda;

import java.util.Collection;
import java.util.Map;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class DiscordResolveItem extends DiscordItem {
	public DiscordResolveItem(String botName, String serverID,boolean toDiscord,boolean toPlain) {
		super(botName);
		this.serverID=serverID;
		this.toDiscord=toDiscord;
		this.toPlain=toPlain;
	}

	public boolean toDiscord, toPlain;

	@Override
	public String process(String string, Map<String,String> context) {
		String s = string;
		Guild guild = DiscordModule.discordBots.get(botName).getDiscordApi().getGuildById(serverID);
		String output = "";
		boolean found = false;
		for (int i = 0; i < s.length(); i++) {
			found = false;
			if (s.charAt(i) == '\n') {
				output += ' ';
			} else if (toPlain && (s.charAt(i) == '<' && i + 1 < s.length() && s.charAt(i + 1) == '@')) {
				for (int i1 = i; i1 < s.length(); i1++) {
					if (s.charAt(i1) == '>') {
						if (s.charAt(i + 2) == '!') {
							output += "@" + DiscordModule.discordBots.get(botName).getDiscordApi().getUserById(s.substring(i + 3, i1)).getName();
						} else {
							output += "@" + DiscordModule.discordBots.get(botName).getDiscordApi().getUserById(s.substring(i + 2, i1)).getName();
						}
						i += i1 - i;
						break;
					}
				}
			} else if (toPlain && (s.charAt(i) == '<' && i + 1 < s.length() && s.charAt(i + 1) == '#')) {
				for (int i1 = i; i1 < s.length(); i1++) {
					if (s.charAt(i1) == '>') {
						output += "#" + DiscordModule.discordBots.get(botName).getDiscordApi().getTextChannelById(s.substring(i + 2, i1)).getName();
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
						Collection<Member> users = guild.getMembersByNickname(s.substring(i + 1, i1),true);
						if (!users.isEmpty()) {
							output += users.iterator().next().getAsMention();
							i = i1 - 1;
							found = true;
							break;
						}
						users = guild.getMembersByEffectiveName(s.substring(i + 1, i1),true);
						if (!users.isEmpty()) {
							output += users.iterator().next().getAsMention();
							i = i1 - 1;
							found = true;
							break;
						}
						users = guild.getMembersByName(s.substring(i + 1, i1),true);
						if (!users.isEmpty()) {
							output += users.iterator().next().getAsMention();
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
						if (!guild.getTextChannelsByName(s.substring(i + 1, i1),true).isEmpty()) {
							output += guild.getTextChannelsByName(s.substring(i + 1, i1),true).get(0).getAsMention();
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
		return output;
	}

}