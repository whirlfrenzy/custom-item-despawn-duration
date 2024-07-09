package whirlfrenzy.configurableitemdespawnduration.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import whirlfrenzy.configurableitemdespawnduration.ConfigurableItemDespawnDuration;
import whirlfrenzy.configurableitemdespawnduration.config.ConfigurableItemDespawnDurationConfig;

@Mod(ConfigurableItemDespawnDuration.NEOFORGE_MOD_ID)
public final class ConfigurableItemDespawnDurationNeoForge {
    public ConfigurableItemDespawnDurationNeoForge(IEventBus eventBus) {
        ConfigurableItemDespawnDuration.init();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> ConfigurableItemDespawnDurationConfig.getScreen(parent, ConfigurableItemDespawnDuration.MOD_ID));
    }
}
