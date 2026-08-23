package io.jfglzs.fastitemframe.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.jfglzs.fastitemframe.acc.ClientPacketListenerAccessor1;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListener_Mixin implements ClientPacketListenerAccessor1 {
    @Unique
    private final Int2IntArrayMap FIF$MAPS = new Int2IntArrayMap();

    @Inject(
            method = "handleRespawn",
            at = @At("HEAD")
    )
    public void handleRespawn(ClientboundRespawnPacket packet, CallbackInfo ci) {
        this.FIF$MAPS.clear();
    }


    @Inject(
            method = "handleMapItemData",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/game/ClientboundMapItemDataPacket;applyToMap(Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;)V"
            ),
            cancellable = true
    )
    public void handleMapItemData(ClientboundMapItemDataPacket packet,
                                  CallbackInfo ci,
                                  @Local MapId id,
                                  @Local MapItemSavedData savedData
    ) {
        int saveDataHash = savedData.hashCode();
        int intID = id.id();
        if (intID != 0 && this.FIF$MAPS.get(intID) == saveDataHash) {
            ci.cancel();
        }
        else {
            this.FIF$MAPS.put(intID, saveDataHash);
        }
    }

    @Unique
    public Int2IntArrayMap FIF$getMaps() {
        return FIF$MAPS;
    }
}

