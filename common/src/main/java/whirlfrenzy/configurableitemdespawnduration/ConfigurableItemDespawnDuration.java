package whirlfrenzy.configurableitemdespawnduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whirlfrenzy.configurableitemdespawnduration.config.ConfigurableItemDespawnDurationConfig;

public final class ConfigurableItemDespawnDuration {
    public static final String MOD_ID = "configurable-item-despawn-duration";
    public static final String NEOFORGE_MOD_ID = "configurable_item_despawn_duration";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final int THIRTY_MINUTES_IN_SECONDS = 1800;

    public static void init() {
        ConfigurableItemDespawnDurationConfig.init(MOD_ID, ConfigurableItemDespawnDurationConfig.class);
    }
}
