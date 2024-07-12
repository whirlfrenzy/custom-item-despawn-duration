package whirlfrenzy.customitemdespawnduration.config;

import whirlfrenzy.customitemdespawnduration.CustomItemDespawnDuration;

import java.util.LinkedHashMap;

public class CustomItemDespawnDurationConfig extends MidnightConfig {
    @Comment(centered = true)
    public static String comment = "";

    @Entry
    public static boolean modifyDefaultItemDuration = false;

    @Entry(min = 0, max = CustomItemDespawnDuration.THIRTY_MINUTES_IN_SECONDS)
    public static int defaultItemDuration = 300;

    @Entry(isMap = true)
    public static LinkedHashMap<String, Integer> despawnDurations = new LinkedHashMap<>();
}
