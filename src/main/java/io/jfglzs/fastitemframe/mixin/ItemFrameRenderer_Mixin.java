package io.jfglzs.fastitemframe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFrameRenderer.class)
public class ItemFrameRenderer_Mixin {
    @WrapOperation(
            method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/block/BlockModelResolver;updateForItemFrame(Lnet/minecraft/client/renderer/block/BlockModelRenderState;ZZ)V"
            )
    )
    public void updateForItemFrame(BlockModelResolver instance,
                                   BlockModelRenderState renderState,
                                   boolean isGlowing,
                                   boolean map,
                                   Operation<Void> original,
                                   @Local ItemStack stack
    ) {
        if (stack.isEmpty()) {
            original.call(instance, renderState, isGlowing, map);
        }
    }

    @WrapOperation(
            method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/item/ItemModelResolver;updateForNonLiving(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    public void updateForNonLiving(ItemModelResolver instance,
                                   ItemStackRenderState output,
                                   ItemStack item,
                                   ItemDisplayContext displayContext,
                                   Entity entity,
                                   Operation<Void> original
    ) {
        if (!item.is(Items.FILLED_MAP)) {
            original.call(instance, output, item, displayContext, entity);
        }
    }
}
