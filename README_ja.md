# ![Logo](src/main/resources/assets/ama-carpet/icon_32.png) AmaCarpet

[English readme here](README.md)<br><br>
[![License](https://img.shields.io/github/license/pugur523/ama-carpet.svg)](https://opensource.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/pugur523/ama-carpet.svg)](https://github.com/pugur523/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)

**AmaCarpet**は**Amateras SMP**のために作られたCarpet Mod Extensionです。<br>
バグや問題等発見された方は[issue](https://github.com/pugur523/ama-carpet/issues)へお願いします。また、機能改善や新機能等あれば[pull request](https://github.com/pugur523/ama-carpet/pulls)まで。


## Rules


### cheatRestriction

> [tweakeroo](https://modrinth.com/mod/tweakeroo)や[tweakermore](https://modrinth.com/mod/tweakermore)、[litematica](https://modrinth.com/mod/litematica)の中の指定された機能を禁止する。禁止する機能は`/restriction`コマンドで指定できる。

> [!NOTE]
> サーバー側でのみ機能する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### commandListRestriction

> `/listrestriction`コマンドを有効化し、[cheatRestriction](#cheatRestriction)により禁止されているクライアントmodの機能の一覧をこのコマンドで閲覧できるようにする。
> `/restriction`コマンドを引数なしで実行したときと同じ結果が返ってくるが、このコマンドはデフォルトではop権限を持っていないプレイヤーも実行することができる。

- Type: `String`
- Default value: `true`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`
<br><br>

### commandRestriction

> `/restriction`コマンドを有効化し、[cheatRestriction](#cheatRestriction)により禁止するクライアントmodの機能をこのコマンドで設定できるようにする。

- Type: `String`
- Default value: `ops`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`
<br><br>

### debugModeAmaCarpet

> AmaCarpet開発者用のdebug printを有効化する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`
<br><br>

### disableAnimalSpawnOnChunkGen

> 地形生成時の動物のスポーンを無効化する。軽量化やワールドサイズの縮小が期待される。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `OPTIMIZATION`, `SURVIVAL`
<br><br>

### disableSoundEngine

> サーバー側で処理されるすべての音エンジンを無効化して軽量化する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `CREATIVE`, `OPTIMIZATION`, `SURVIVAL`
<br><br>

### endGatewayChunkLoad (MC < 1.21)

> エンドゲートウェイポータルを通過したエンティティが3x3チャンクをロードするようにする。
> 1.21で実装された機能のbackport。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### endPortalChunkLoad (MC < 1.21)

> エンドポータルを通過したエンティティが3x3チャンクをロードするようにする。
> 1.21で実装された機能のbackport。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### notifySchematicShare

> schematic(.litematicファイル)が[syncmatica](https://modrinth.com/mod/syncmatica)や[kyoyu](https://modrinth.com/mod/kyoyu)を通じて共有されたり、削除されたときにプレイヤーのチャットへ通知を送信する。

> [!NOTE]
> サーバー側でのみ機能する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### reloadPortalTicket

> サーバーの起動時に、すべての記録されていたポータルチャンクロードチケットを再読み込みさせる。
> これによりチャンクローダーがサーバーの再起動で壊れなくなる。
> 25w05aで実装された機能のbackport。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### requireAmaCarpetClient

> ama-carpetを導入していないクライアントによるサーバーへのログインを拒否する。タイムアウトまでの時間は[requireAmaCarpetClientTimeoutSeconds](#requireAmaCarpetClientTimeoutSeconds)にて設定できる。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`
<br><br>

### requireAmaCarpetClientTimeoutSeconds

> [requireAmaCarpetClient](#requireAmaCarpetClient)によるクライアントがama-carpetを導入しているかどうかのチェックがタイムアウトするまでの時間を設定する。

- Type: `int`
- Default value: `5`
- Suggested options: `3`, `5`, `10`
- Categories: `AMA`
<br><br>
