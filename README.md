# ![Logo](amacarpet_icon_64.png) AmaCarpet

[日本語の説明はこちら](https://github.com/pugur523/ama-carpet/blob/main/README_ja.md)<br><br>
[![License](https://img.shields.io/github/license/pugur523/ama-carpet.svg)](https://opensource.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/pugur523/ama-carpet.svg)](https://github.com/pugur523/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)

**AmaCarpet** is an extension of the Carpet Mod specifically designed for the **Amateras SMP**.
Feel free to [report any issues](https://github.com/pugur523/ama-carpet/issues) or [contribute to this mod](https://github.com/pugur523/ama-carpet/pulls).


## Rules


### cheatRestriction

> Prohibits specific features in client-side mods such as [Tweakeroo](https://modrinth.com/mod/tweakeroo), [Tweakermore](https://modrinth.com/mod/tweakermore), [Litematica](https://modrinth.com/mod/litematica). The features to restrict can be configured using the `/restriction` command.

> [!NOTE]
> Only works in server side.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### commandRestriction

> Enables `/restriction` command to configure features restricted by [cheatRestriction](#cheatrestriction).

- Type: `String`
- Default value: `ops`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`

### disableAnimalSpawnOnChunkGen

> Disables animal spawning during chunk generation.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `OPTIMIZATION`, `SURVIVAL`

### disableSoundEngine

> Disables all server-side sound engine processes.
 
- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `CREATIVE`, `OPTIMIZATION`, `SURVIVAL`

### endGatewayChunkLoad (MC < 1.21)

> Allows entities traveling through end gateway portals to load a 3x3 chunk area, similar to nether portals.
> This is a backport of a feature implemented in Minecraft 1.21.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### endPortalChunkLoad (MC < 1.21)

> Allows entities traveling through end portals to load a 3x3 chunk area, similar to nether portals.
> This is a backport of a feature implemented in Minecraft 1.21.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### notifySchematicShare
> Sends notifications to player's chat when a schematic is shared or unshared using [Syncmatica](https://modrinth.com/mod/syncmatica) or [Kyoyu](https://modrinth.com/mod/kyoyu).

> [!NOTE] 
> Only works in server side.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### reloadPortalTicket

> Reloads all Nether portal chunk-loading tickets during server startup.
> This ensures that chunk loaders remain functional after server restarts.
> This feature is a backport of the implementation in snapshot 25w05a.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### requireAmaCarpetClient

> Prevents clients without AmaCarpet installed from logging in.
> The timeout duration can be configured with [requireAmaCarpetClientTimeoutSeconds](#requireamacarpetclienttimeoutseconds).

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`

### requireAmaCarpetClientTimeoutSeconds

> Determines timeout duration for [requireAmaCarpetClient](#requireamacarpetclient) to check if the client has ama-carpet on login phase.

- Type: `int`
- Default value: `5`
- Suggested options: `3`, `5`, `10`
- Categories: `AMA`
