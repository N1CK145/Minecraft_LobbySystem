# Minecraft LobbySystem
v.1.2.2

by N1CK145
## Features
* Lobby compass (Custom commands)
  * /server command compatible
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
| `/spawn`  | Teleport to spawn | /l /lobby /hub |
| `/message <player> <message>` | Send private message to Player | /msg |
| `/warp <warp>` | Teleport to warp |  |

### Admin
| Command | Description |
| ------- | ----------- |
| `/lobbyc`  | Configure the Plugin |
| `/lobbyc help [page]`   | Shows help |
| `/lobbyc setspawn` | Set lobbyspawn to current location |
| `/build [player]` | Toggles targets build mode |
| `/setwarp <warp>` | Creates new warp or change warps location to players |

## Permissions
### General
| Permission | Description |
| ---------- | ----------- |
| `lobbySystem.cmd.spawn` | Allows to use command /spawn |
| `lobbySystem.cmd.message` | Allows to use command /message |
| `lobbySystem.cmd.warp` | Allow to use command /warp |

### Admin
| Permission | Description |
| ---------- | ----------- |
| `lobbySystem.cmd.build` | Allows to set oneself to buildmode |
| `lobbySystem.cmd.build.other` | Allows to set others to buildmode |
| `lobbySystem.cmd.admin` | Allows to configure the plugin |
| `lobbySystem.cmd.setwarp` | Allows to use command /setwarp |
