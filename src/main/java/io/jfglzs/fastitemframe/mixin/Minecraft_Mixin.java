package io.jfglzs.fastitemframe.mixin;

import io.jfglzs.fastitemframe.acc.ClientPacketListenerAccessor1;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.maps.MapId;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class Minecraft_Mixin {
    @Shadow
    @Nullable
    public LocalPlayer player;
    @Unique
    private int FIF$age = 0;

    @Inject(
            method = "runTick",
            at = @At("HEAD")
    )
    private void runTick(CallbackInfo ci) {
        FIF$age++;
        if (this.player != null && this.FIF$age % 200 == 0) {
            for (ItemStack stack : this.player.getInventory()) {
                if (! stack.is(Items.FILLED_MAP)) return;
                MapId mapId = stack.get(DataComponents.MAP_ID);
                if (mapId != null) {
                    ((ClientPacketListenerAccessor1) player.connection).FIF$getMaps().remove(mapId.id());
                }
            }
        }

    }
}
