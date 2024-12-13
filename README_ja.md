[English readme here](https://github.com/pugur523/ama-carpet/blob/main/README.md)<br><br>
[![License](https://img.shields.io/github/license/pugur523/ama-carpet.svg)](https://opensource.org/licenses/MIT)
[![Issues](https://img.shields.io/github/issues/pugur523/ama-carpet.svg)](https://github.com/pugur523/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)

これはAmateras SMPのために作られたcarpet extensionです

## Rules

### disableNetherPortalCollisionCheck (MC >= 1.19)

> ネザーポータルを通過したエンティティのCollision Checkを無効化します。
> これはChronos Carpet Additionからポートされました。


### disableSoundeEngine

> サーバー側で処理されるすべての音エンジンを無効化します。


### endGatewayChunkLoad (MC < 1.21)

> エンドゲートウェイポータルをくぐったエンティティが3x3チャンクをロードするようになります。
> これと同様の仕様が1.21のバニラに実装されています。


### endPortalChunkLoad (MC < 1.21)

> エンドポータルをくぐったエンティティが3x3チャンクをロードするようになります。
> これと同様の仕様が1.21のバニラに実装されています。


### reloadPortalTicket

> サーバーの起動時に、すべての記録されていたポータルチャンクロードチケットを再読み込みします。
> これによりチャンクローダーがサーバーの再起動で壊れにくくなります。