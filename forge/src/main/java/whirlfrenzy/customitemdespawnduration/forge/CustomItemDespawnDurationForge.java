package whirlfrenzy.customitemdespawnduration.forge;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import whirlfrenzy.customitemdespawnduration.CustomItemDespawnDuration;
import whirlfrenzy.customitemdespawnduration.config.CustomItemDespawnDurationConfig;

@Mod(CustomItemDespawnDuration.FORGE_MOD_ID)
public final class CustomItemDespawnDurationForge {
    public CustomItemDespawnDurationForge() {
        CustomItemDespawnDuration.init();

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> CustomItemDespawnDurationConfig.getScreen(parent, CustomItemDespawnDuration.MOD_ID)));
    }
}
