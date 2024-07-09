package whirlfrenzy.configurableitemdespawnduration.forge;

import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformSpecificHelperImpl {
    public static Path getConfigDirectory(){
        return FMLPaths.CONFIGDIR.get();
    }

    public static boolean isClientSide(){
        return FMLEnvironment.dist.isClient();
    }
}
