[English readme here](https://github.com/pugur523/ama-carpet/blob/main/README.md)<br><br>
[![License](https://img.shields.io/github/license/pugur523/ama-carpet.svg)](https://opensource.org/licenses/MIT)
[![Issues](https://img.shields.io/github/issues/pugur523/ama-carpet.svg)](https://github.com/pugur523/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)

AmaCarpetはAmateras SMPのために作られたCarpet mod extensionです

## Rules


### disableSoundeEngine

> サーバー側で処理されるすべての音エンジンを無効化して軽量化する。


### endGatewayChunkLoad (MC < 1.21)

> エンドゲートウェイポータルを通過したエンティティが3x3チャンクをロードするようにする。
> 1.21で実装された機能のbackport。


### endPortalChunkLoad (MC < 1.21)

> エンドポータルを通過したエンティティが3x3チャンクをロードするようにする。
> 1.21で実装された機能のbackport。


### notifyLitematicShared

> litematicが[syncmatica](https://modrinth.com/mod/syncmatica)や[kyoyu](https://modrinth.com/mod/kyoyu)を通じて共有されたり、削除されたときにプレイヤーのチャットへ通知を送信する。
> [!NOTE] kyoyuへの対応は準備中。


### reloadPortalTicket

> サーバーの起動時に、すべての記録されていたポータルチャンクロードチケットを再読み込みさせる。
> これによりチャンクローダーがサーバーの再起動で壊れなくなる。
> 25w05a(スナップショット)にて実装されたのでbackportになる可能性あり。