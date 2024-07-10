package whirlfrenzy.customitemdespawnduration.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import whirlfrenzy.customitemdespawnduration.CustomItemDespawnDuration;
import whirlfrenzy.customitemdespawnduration.config.CustomItemDespawnDurationConfig;

public class ModMenuApiImpl implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return (parent) -> CustomItemDespawnDurationConfig.getScreen(parent, CustomItemDespawnDuration.MOD_ID);
    }
}
