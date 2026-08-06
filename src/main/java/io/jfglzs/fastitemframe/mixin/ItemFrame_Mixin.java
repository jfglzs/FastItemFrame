package io.jfglzs.fastitemframe.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemFrame.class)
public abstract class ItemFrame_Mixin extends Entity {
    public ItemFrame_Mixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }
}
