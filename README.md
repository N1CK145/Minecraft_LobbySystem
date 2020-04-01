# Minecraft LobbySystem
v.1.1.0
by N1CK145
## Features
* Lobby compass (Custom commands)
* Lobby teleport (lobby)
* Custom plugin messages (Editable in config)
* Custom join and quit messages
* Custom title on join
* Enable and disable build
* MySQL connection
* msg command

## Commands
### General
| Command | Description | Alias |
| ------- | ----------- | ----- |
| /spawn  | Teleport to spawn | /l /lobby /hub |
| /message <player> <message> | Send private message to Player | /msg |

### Admin
| Command | Description |
| ------- | ----------- |
| /lobbyc  | Configure the Plugin |
| /lobbyc help [page]   | Shows help |
| /lobbyc setspawn | Set lobbyspawn to current location |
| /build [player] | Toggles target build mode |

## Permissions
### General
| Permission | Description |
| ---------- | ----------- |
| lobby.cmd.spawn | Allows to use command /spawn |
| lobby.cmd.message | Allows to use command /message |

### Admin
| Permission | Description |
| ---------- | ----------- |
| lobby.cmd.build | Allows to set oneself to buildmode |
| lobby.cmd.build.other | Allows to set others to buildmode |
| lobby.cmd.admin | Allows to configure the plugin |
