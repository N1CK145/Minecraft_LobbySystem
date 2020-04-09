# Minecraft LobbySystem
v.1.2.0

by N1CK145
## Features
* Lobby compass (Custom commands)
* Lobby teleport (lobby)
* Custom plugin messages (Editable in config)
* Custom join and quit messages
* Custom title on join
* Enable and disable build
* MySQL connection
* Message command (Private messages)
* Warp system

## Commands
### General
| Command | Description | Alias |
| ------- | ----------- | ----- |
| /spawn  | Teleport to spawn | /l /lobby /hub |
| <p>/message <player> <message></p> | Send private message to Player | /msg |
| /warp <warp> | Teleport to warp |  |

### Admin
| Command | Description |
| ------- | ----------- |
| /lobbyc  | Configure the Plugin |
| /lobbyc help [page]   | Shows help |
| /lobbyc setspawn | Set lobbyspawn to current location |
| /build [player] | Toggles targets build mode |
| /setwarp <warp> | Creates new warp or change warps location to players |

## Permissions
### General
| Permission | Description |
| ---------- | ----------- |
| lobby.cmd.spawn | Allows to use command /spawn |
| lobby.cmd.message | Allows to use command /message |
| lobby.cmd.warp | Allow to use command /warp |

### Admin
| Permission | Description |
| ---------- | ----------- |
| lobby.cmd.build | Allows to set oneself to buildmode |
| lobby.cmd.build.other | Allows to set others to buildmode |
| lobby.cmd.admin | Allows to configure the plugin |
| lobby.cmd.setwarp | Allows to use command /setwarp |
