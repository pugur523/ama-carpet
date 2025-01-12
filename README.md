[日本語の説明はこちら](https://github.com/pugur523/ama-carpet/blob/main/README_ja.md)<br><br>
[![License](https://img.shields.io/github/license/pugur523/ama-carpet.svg)](https://opensource.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/pugur523/ama-carpet.svg)](https://github.com/pugur523/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)

AmaCarpet is a Carpet mod extension made for Amateras SMP.
Be free to [report any issues](https://github.com/pugur523/ama-carpet/issues) or [contribute to this mod](https://github.com/pugur523/ama-carpet/pulls).

## Rules


### cheatRestriction

> Prohibits certain features on client mods such as [tweakeroo](https://modrinth.com/mod/tweakeroo), [tweakermore](https://modrinth.com/mod/tweakermore), [litematica](https://modrinth.com/mod/litematica). Features to prohibit can be configured with `/restriction` command.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### commandRestriction

> Enables `/restriction` command to configure features prohibited by [cheatRestriction](#cheatrestriction).

- Type: `String`
- Default value: `ops`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`

### disableAnimalSpawnOnChunkGen

> Disables animal spawning on chunk generation.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `OPTIMIZATION`, `SURVIVAL`

### disableSoundEngine

> Disables all process of server-side sound engine.
 
- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `CREATIVE`, `OPTIMIZATION`, `SURVIVAL`

### endGatewayChunkLoad (MC < 1.21)

> Entities go through end portal gateway will load 3x3 chunks like a nether portal.
> This is a backport feature implemented in vanilla minecraft 1.21.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### endPortalChunkLoad (MC < 1.21)

> Entities go through end portal will load 3x3 chunks like a nether portal.
> This is a backport feature implemented in vanilla minecraft 1.21.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### notifySchematicShare
> [!NOTE] 
> currently kyoyu support is under development.

> Sends notifications to player's chat when a schematic is shared or unshared using [syncmatica](https://modrinth.com/mod/syncmatica) or [kyoyu](https://modrinth.com/mod/kyoyu).

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### reloadPortalTicket

> Reloads all nether portal chunk loading tickets on server start.
> Chunk loaders may not be broken on server restarts.
> This was implemented in snapshot 25w05a so would be a backport.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### requireAmaCarpetClient

> Prohibits client login without ama-carpet. The timeout duration can be configure with [requireAmaCarpetClientTimeOutSeconds](#requireamacarpetclienttimeoutseconds)

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
