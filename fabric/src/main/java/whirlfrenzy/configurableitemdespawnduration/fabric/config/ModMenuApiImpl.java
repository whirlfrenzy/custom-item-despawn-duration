package whirlfrenzy.configurableitemdespawnduration.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import whirlfrenzy.configurableitemdespawnduration.ConfigurableItemDespawnDuration;
import whirlfrenzy.configurableitemdespawnduration.config.ConfigurableItemDespawnDurationConfig;

public class ModMenuApiImpl implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return (parent) -> ConfigurableItemDespawnDurationConfig.getScreen(parent, ConfigurableItemDespawnDuration.MOD_ID);
    }
}
