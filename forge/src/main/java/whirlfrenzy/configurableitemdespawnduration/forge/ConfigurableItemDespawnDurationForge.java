package whirlfrenzy.configurableitemdespawnduration.forge;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import whirlfrenzy.configurableitemdespawnduration.ConfigurableItemDespawnDuration;
import whirlfrenzy.configurableitemdespawnduration.config.ConfigurableItemDespawnDurationConfig;

@Mod(ConfigurableItemDespawnDuration.FORGE_MOD_ID)
public final class ConfigurableItemDespawnDurationForge {
    public ConfigurableItemDespawnDurationForge() {
        ConfigurableItemDespawnDuration.init();

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ConfigurableItemDespawnDurationConfig.getScreen(parent, ConfigurableItemDespawnDuration.MOD_ID)));
    }
}
