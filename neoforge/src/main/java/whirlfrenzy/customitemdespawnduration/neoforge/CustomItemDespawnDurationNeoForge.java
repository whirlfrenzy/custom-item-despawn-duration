package whirlfrenzy.customitemdespawnduration.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import whirlfrenzy.customitemdespawnduration.CustomItemDespawnDuration;
import whirlfrenzy.customitemdespawnduration.config.CustomItemDespawnDurationConfig;

@Mod(CustomItemDespawnDuration.NEOFORGE_MOD_ID)
public final class CustomItemDespawnDurationNeoForge {
    public CustomItemDespawnDurationNeoForge(IEventBus eventBus) {
        CustomItemDespawnDuration.init();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> CustomItemDespawnDurationConfig.getScreen(parent, CustomItemDespawnDuration.MOD_ID));
    }
}
