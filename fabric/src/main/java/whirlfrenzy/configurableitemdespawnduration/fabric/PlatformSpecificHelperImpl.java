package whirlfrenzy.configurableitemdespawnduration.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class PlatformSpecificHelperImpl {
    public static Path getConfigDirectory(){
        return FabricLoader.getInstance().getConfigDir();
    }

    public static boolean isClientSide(){
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
