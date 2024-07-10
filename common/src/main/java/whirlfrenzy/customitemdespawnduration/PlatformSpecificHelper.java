package whirlfrenzy.customitemdespawnduration;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class PlatformSpecificHelper {
    @ExpectPlatform
    public static Path getConfigDirectory(){
        return null;
    }

    @ExpectPlatform
    public static boolean isClientSide(){
        return false;
    }
}
