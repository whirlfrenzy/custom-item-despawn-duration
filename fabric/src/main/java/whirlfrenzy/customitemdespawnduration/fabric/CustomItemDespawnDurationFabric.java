package whirlfrenzy.customitemdespawnduration.fabric;

import net.fabricmc.api.ModInitializer;

import whirlfrenzy.customitemdespawnduration.CustomItemDespawnDuration;

public final class CustomItemDespawnDurationFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        CustomItemDespawnDuration.init();
    }
}
