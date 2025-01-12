[English readme here](https://github.com/pugur523/ama-carpet/blob/main/README.md)<br><br>
[![License](https://img.shields.io/github/license/pugur523/ama-carpet.svg)](https://opensource.org/licenses/MIT)
[![Issues](https://img.shields.io/github/issues/pugur523/ama-carpet.svg)](https://github.com/pugur523/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)

AmaCarpetはAmateras SMPのために作られたCarpet mod extensionです

## Rules


### cheatRestriction

> [tweakeroo](https://modrinth.com/mod/tweakeroo)や[tweakermore](https://modrinth.com/mod/tweakermore)、[litematica](https://modrinth.com/mod/litematica)の中の指定された機能を禁止する。禁止する機能は`/restriction`コマンドで指定できる。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### commandRestriction

> `/restriction`コマンドを有効化し、[cheatRestriction](#cheatrestriction)により禁止するクライアントmodの機能をこのコマンドで設定できるようにする。

- Type: `String`
- Default value: `ops`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`

### disableAnimalSpawnOnChunkGen

> 地形生成時の動物のスポーンを無効化する。ワールドサイズの縮小が期待される。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `OPTIMIZATION`, `SURVIVAL`

### disableSoundeEngine

> サーバー側で処理されるすべての音エンジンを無効化して軽量化する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `CREATIVE`, `OPTIMIZATION`, `SURVIVAL`

### endGatewayChunkLoad (MC < 1.21)

> エンドゲートウェイポータルを通過したエンティティが3x3チャンクをロードするようにする。
> 1.21で実装された機能のbackport。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### endPortalChunkLoad (MC < 1.21)

> エンドポータルを通過したエンティティが3x3チャンクをロードするようにする。
> 1.21で実装された機能のbackport。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### notifySchematicShare

> litematicが[syncmatica](https://modrinth.com/mod/syncmatica)や[kyoyu](https://modrinth.com/mod/kyoyu)を通じて共有されたり、削除されたときにプレイヤーのチャットへ通知を送信する。
> [!NOTE] kyoyuへの対応は準備中。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### reloadPortalTicket

> サーバーの起動時に、すべての記録されていたポータルチャンクロードチケットを再読み込みさせる。
> これによりチャンクローダーがサーバーの再起動で壊れなくなる。
> 25w05a(スナップショット)にて実装されたのでbackportになる可能性あり。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`

### requireAmaCarpetClient

> ama-carpetを導入していないクライアントによるサーバーへのログインを拒否する。タイムアウトまでの時間は[requireAmaCarpetClientTimeOutSeconds](#requireamacarpetclienttimeoutseconds)にて設定できる。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`

### requireAmaCarpetClientTimeoutSeconds

> [requireAmaCarpetClient](#requireamacarpetclient)によるクライアントがama-carpetを持っているかどうかのチェックがタイムアウトするまでの時間を設定する。

- Type: `int`
- Default value: `5`
- Suggested options: `3`, `5`, `10`
- Categories: `AMA`
