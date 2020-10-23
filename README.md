# ChatDirector

Manages chat however you specify in the config. Not plug and play, but very customizable.

# Configuration documentation:

```yaml
modules:
  - discord:
    bots:
      - bot-name: TOKEN
  - file
  - replacement
pipes:
  - documentation:
    - bukkit-input:
      - chat
      - leave
      - join
      - server
      - checkCanceled
    - file-input:
      path: PATH_TO_FIFO
      delay: 500
    - discord-input:
      server: SERVER_ID
      channel: CHANNEL_ID
      bot: bot_name
    - bukkit-playerlist: null
    - discord-resolve:
      server: SERVER_ID
      bot: bot_name
      toDiscord: false
      toPlain: true
    - regex:
      "REGEX_PATTERN": "REGEX_PATTERN"
    - remove-color: null
    - discord-output:
      server: SERVER_ID
      channel: CHANNEL_ID
      bot: bot_name
    - bukkit-output:
      permission: "" # ['' or permission node]
    - file-output:
      path: PATH_TO_FIFO
  - example-bukkit-to-discord:
    - bukkit-input:
        - chat
        - leave
        - join
        - server
    - discord-resolve:
      server: SERVER_ID
      bot: bot_name
      toDiscord: true
      toPlain: false
    - remove-color: null
    - discord-output:
      server: SERVER_ID
      channel: CHANNEL_ID
      bot: bot_name
  - example-discord-to-bukkit:
    - discord-input:
      server: SERVER_ID
      channel: CHANNEL_ID
      bot: bot_name
    - discord-resolve:
        server: SERVER_ID
        bot: bot_name
        toDiscord: true
        toPlain: false
    - remove-color: null
    - regex:
        '^\[.*?\] (.*?): playerlist$': ""
        '^\[(.*?)\](.*?):(.*?)': "[&7$1&r]&7$2&r:$3"
    - regex:
        '^\[&7(.*?) JMod&r\]&7(.*?)&r:(.*?)': "[&2$1 JMod&r]&2$2&r:$3"
        '^\[&7(.*?) Mod&r\]&7(.*?)&r:(.*?)': "[&9$1 Mod&r]&9$2&r:$3"
        '^\[&7(.*?) Manager&r\]&7(.*?)&r:(.*?)': "[&4$1 Manager&r]&4$2&r:$3"
        '^\[&7(Retired)&r\]&7(.*?)&r:(.*?)': "[&d$1&r]&d$2&r:$3"
        '^\[&7(.*?) Admin&r\]&7(.*?)&r:(.*?)': "&0[&c$1 &cA&4d&6m&ei&an&0]&c$2&r:$3"
        '^\[&7(.*?) Owner&r\]&7(.*?)&r:(.*?)': "&0[&c$1 &cO&4w&6n&ee&ar&0]&c$2&r:$3"
        '^\[&7(Premium|Patreon|Hype Beast)&r\]&7(.*?)&r:(.*?)': "[&5$1&r]&5$2&r:$3"
    - regex:
        "^(.+)$": "[&7D&r]$1"
    - bukkit-output:
        permission: ""
  - example-discord-playerlist:
    - discord-input:
        server: SERVER_ID
        channel: CHANNEL_ID
        bot: bot_name
    - regex:
      "^.*playerlist": "playerlist"
      "^(?!playerlist).*$": ""
    - bukkit-playerlist: null
    - discord-ouput:
      server: SERVER_ID
      channel: CHANNEL_ID
      bot: bot_name
```
