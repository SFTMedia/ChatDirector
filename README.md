# ChatDirector

Manages chat however you specify in the config. Not plug and play, but very customizable.

# Configuration documentation:
See config.yml

# Valid Contexts
USE `%CONTEXT_NAME%` and it will replace in most messages if it can. If an item doesn't format, let me know.
## bukkit
- SERVER_NUM_PLAYERS
- SERVER_MAX_PLAYERS
- SERVER_NAME
- SERVER_MOTD
- PLAYER_NAME
- PLAYER_UUID
### Sometimes Depending on Who Fired the Input
- PLAYER_NAME
## Discord
## Sponge
- SERVER_NUM_PLAYERS
- SERVER_MAX_PLAYERS
- SERVER_MOTD
- PLAYER_NAME
- PLAYER_UUID
## Bungee
- PLAYER_NAME
- PLAYER_UUID
- PLAYER_SERVER_NAME
- PLAYER_SERVER_MOTD
## LuckPerms
- SERVER_NAME
- PLAYER_PREFIX
- PLAYER_SUFFIX
- PLAYER_GROUP
## Vault
- PLAYER_BALANCE
- PLAYER_PREFIX
- PLAYER_SUFFIX
- PLAYER_GROUP

# TODO
- Anything that starts work should have context
- Add format to discord-input
- Add more contexts as needed.