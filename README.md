# ChatDirector

Manages chat however you specify in the config. Not plug and play, but very customizable.

# Configuration documentation:

See config.yml

# Valid Contexts

USE `%CONTEXT_NAME%` and it will replace in most messages if it can. If an item doesn't format, let me know.

## Common

- CURRENT (Used in Echo to get the previous value)

## Bukkit

- CURRENT (Used in playerlist to get the previous value)
- SERVER_NUM_PLAYERS
- SERVER_MAX_PLAYERS
- SERVER_NAME
- SERVER_MOTD
- PLAYER_NAME
- PLAYER_UUID
- COMMAND_NAME
- CHAT_FORMAT
- CHAT_MESSAGE
- PLAYER_QUIT_MESSAGE

## Bungee

- CURRENT (Used in playerlist to get the previous value)
- PLAYER_NAME
- PLAYER_DISPLAYNAME
- PLAYER_UUID
- PLAYER_SERVER_NAME
- PLAYER_SERVER_MOTD
- SERVER_NAME
- SERVER_MOTD
- CHAT_MESSAGE
- COMMAND*ARG*##
- COMMAND_NAME
- COMMAND_PERMISSION
- SERVER\_##\_PLAYERS
- SERVER\_##\_NUM_PLAYERS

## Context

- CURRENT

## Discord Javacord

- DISCORD_SERVER_NAME
- DISCORD_SERVER_ID
- DISCORD_MESSAGE
- DISCORD_MESSAGE_ID
- DISCORD_DM_USER_ID
- DISCORD_AUTHOR_ID
- DISCORD_SELF_ID
- DISCORD_CHANNEL_ID
- DISCORD_AUTHOR_NAME
- DISCORD_AUTHOR_DISPLAY_NAME
- DISCORD_AUTHOR_NICK_NAME
- DISCORD_AUTHOR_ROLE

## Discord JDA

- DISCORD_MESSAGE
- DISCORD_AUTHOR_ID
- DISCORD_SELF_ID
- DISCORD_CHANNEL_ID
- DISCORD_AUTHOR_NAME
- DISCORD_AUTHOR_NICK_NAME
- DISCORD_AUTHOR_ROLE

## File

- FILE_PATH
- FILE_DELAY

## LuckPerms

- SERVER_NAME
- PLAYER_PREFIX
- PLAYER_SUFFIX
- PLAYER_GROUP

## MultiChat

- BROADCAST_MESSAGE
- CHAT_MESSAGE_FORMAT
- CHAT_MESSAGE
- MULTICHAT_MODE
- MULTICHAT_MODE_SHORT
- PLAYER_NAME
- PLAYER_PREFIX
- SERVER_NAME
- PLAYER_SUFFIX
- PLAYER_PREFIX
- PLAYER_SUFFIX
- PLAYER_NICK
- PLAYER_WORLD
- PLAYER_NAME

## Sponge

- SERVER_NUM_PLAYERS
- SERVER_MAX_PLAYERS
- SERVER_MOTD
- PLAYER_NAME
- PLAYER_UUID
- CHAT_MESSAGE
- CHAT_MESSAGE_FORMATTED
- CHAT_MESSAGE_ORIGINAL

## SQL

- SQL_RESULT

## Vault

- PLAYER_BALANCE
- PLAYER_PREFIX
- PLAYER_SUFFIX
- PLAYER_GROUP

# TODO

- implement onMessage on bungee/bukkit
- implement dynamic command loading on bukkit
- console command item
- more conditional support for support for switch statements
- fallback config mechanism
- config versioning mechanism

# Maven Info

## Tests

Skip test with `mvn install -DskipTests`

## Submodules

Skip submodule with the `-pl '!SUBMODULENAME'` flag. For example `mvn -pl '!universal,!minimal' package` would skip the building modules.
You can also just run it on one module with `-pl 'SUMMODULE'`
