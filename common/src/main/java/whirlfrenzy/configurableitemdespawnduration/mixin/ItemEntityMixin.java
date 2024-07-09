package whirlfrenzy.configurableitemdespawnduration.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whirlfrenzy.configurableitemdespawnduration.config.ConfigurableItemDespawnDurationConfig;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Shadow private int itemAge;

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;DDD)V", at = @At("TAIL"))
    public void setItemAge(World world, double x, double y, double z, ItemStack stack, double velocityX, double velocityY, double velocityZ, CallbackInfo ci){
        if(world.isClient()){
            return;
        }

        Identifier itemIdentifier = Registries.ITEM.getId(((ItemEntity)(Object)this).getStack().getItem());
        if(itemIdentifier == null){
            return;
        }

        if(ConfigurableItemDespawnDurationConfig.despawnDurations.containsKey(itemIdentifier.toString()) || ConfigurableItemDespawnDurationConfig.modifyDefaultItemDuration){
            ((ItemEntityAccessor)(Object)this).setItemAge(6000 - ConfigurableItemDespawnDurationConfig.despawnDurations.getOrDefault(itemIdentifier.toString(), ConfigurableItemDespawnDurationConfig.defaultItemDuration) * 20);
        }

    }
}
