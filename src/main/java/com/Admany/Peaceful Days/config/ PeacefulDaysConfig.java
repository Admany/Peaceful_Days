package com.Admany.PeacefulDays.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

public class PeacefulDaysConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Configuration Categories
    public static final ForgeConfigSpec.BooleanValue PEACEFUL_MODE_ENABLED;
    public static final ForgeConfigSpec.EnumValue<PeacefulModeType> PEACEFUL_MODE_TYPE;
    public static final ForgeConfigSpec.IntValue FIXED_PEACEFUL_DAYS;
    public static final ForgeConfigSpec.IntValue RANDOM_PEACEFUL_DAY_MIN;
    public static final ForgeConfigSpec.IntValue RANDOM_PEACEFUL_DAY_MAX;
    public static final ForgeConfigSpec.IntValue POST_PEACEFUL_DIFFICULTY;
    public static final ForgeConfigSpec.BooleanValue DEV_MODE_ENABLED;
    public static final ForgeConfigSpec.ConfigValue<String> CUSTOM_PEACEFUL_DAYS_LIST;

    // Enum for Peaceful Mode Types
    public enum PeacefulModeType {
        FIXED,       // Peaceful day every X days
        RANDOM,      // Random peaceful day selection
        CUSTOM_LIST  // Custom list of specific days
    }

    static {
        BUILDER.push("peaceful_days_configuration");

        PEACEFUL_MODE_ENABLED = BUILDER
                .comment("Enable Peaceful Mode Mechanics")
                .define("peacefulModeEnabled", true);

        PEACEFUL_MODE_TYPE = BUILDER
                .comment("Peaceful Day Selection Method")
                .defineEnum("peacefulModeType", PeacefulModeType.FIXED);

        FIXED_PEACEFUL_DAYS = BUILDER
                .comment("Number of days between Peaceful Days (if FIXED mode)")
                .defineInRange("fixedPeacefulDays", 20, 1, 100);

        RANDOM_PEACEFUL_DAY_MIN = BUILDER
                .comment("Minimum day for random peaceful day selection")
                .defineInRange("randomPeacefulDayMin", 1, 1, 1000);

        RANDOM_PEACEFUL_DAY_MAX = BUILDER
                .comment("Maximum day for random peaceful day selection")
                .defineInRange("randomPeacefulDayMax", 100, 1, 1000);

        CUSTOM_PEACEFUL_DAYS_LIST = BUILDER
                .comment("Comma-separated list of specific peaceful days (e.g. 10,25,50)")
                .define("customPeacefulDaysList", "");

        POST_PEACEFUL_DIFFICULTY = BUILDER
                .comment("Difficulty after Peaceful Day (0=Peaceful, 1=Easy, 2=Normal, 3=Hard)")
                .defineInRange("postPeacefulDifficulty", 2, 0, 3);

        DEV_MODE_ENABLED = BUILDER
                .comment("Enable Developer Diagnostic Mode")
                .define("devMode", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    // Utility method to get custom peaceful days
    public static int[] getCustomPeacefulDays() {
        String customDaysList = CUSTOM_PEACEFUL_DAYS_LIST.get();
        if (customDaysList.isEmpty()) {
            return new int[0];
        }

        return java.util.Arrays.stream(customDaysList.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    // Validate configuration
    public static boolean validateConfiguration() {
        if (PEACEFUL_MODE_TYPE.get() == PeacefulModeType.RANDOM) {
            return RANDOM_PEACEFUL_DAY_MIN.get() < RANDOM_PEACEFUL_DAY_MAX.get();
        }
        return true;
    }

    // Debug method to print current configuration
    public static void printCurrentConfiguration() {
        System.out.println("Peaceful Days Mod Configuration:");
        System.out.println("Enabled: " + PEACEFUL_MODE_ENABLED.get());
        System.out.println("Mode Type: " + PEACEFUL_MODE_TYPE.get());
        System.out.println("Fixed Peaceful Days: " + FIXED_PEACEFUL_DAYS.get());
        System.out.println("Dev Mode: " + DEV_MODE_ENABLED.get());
    }
}