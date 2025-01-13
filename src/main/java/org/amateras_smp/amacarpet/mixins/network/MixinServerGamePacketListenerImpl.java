package org.amateras_smp.amacarpet.mixins.network;

import net.minecraft.network.protocol.game.ServerGamePacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        //#if 12002 <= MC && MC < 12005
        //$$ ServerCommonPacketListenerImpl.class
        //#else
        ServerGamePacketListener.class
        //#endif
)
public abstract class MixinServerGamePacketListenerImpl {
    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onCustomPayload$TISCM(CustomPayloadC2SPacket packet, CallbackInfo ci)
    {
        if (!CarpetTISAdditionSettings.tiscmNetworkProtocol)
        {
            return;
        }

        //#if MC >= 12002
        //$$ if (packet.payload() instanceof TISCMCustomPayload tiscmCustomPayload && (Object)this instanceof ServerPlayNetworkHandler self)
        //$$ {
        //$$ 	TISCMServerPacketHandler.getInstance().dispatch(self, tiscmCustomPayload);
        //$$ 	ci.cancel();
        //$$ }
        //#else
        Identifier channel = ((CustomPayloadC2SPacketAccessor)packet).getChannel();
        if (TISCMCustomPayload.ID.equals(channel))
        {
            TISCMCustomPayload tiscmCustomPayload = new TISCMCustomPayload(((CustomPayloadC2SPacketAccessor)packet).getData());
            TISCMServerPacketHandler.getInstance().dispatch((ServerPlayNetworkHandler)(Object)this, tiscmCustomPayload);
            ci.cancel();
        }
        //#endif
    }
}
