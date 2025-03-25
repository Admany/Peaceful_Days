package com.admany.peacefuldays.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

public class ConfigManager {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue PEACEFUL_MODE_ENABLED;
    public static final ForgeConfigSpec.EnumValue<PeacefulModeType> PEACEFUL_MODE_TYPE;
    public static final ForgeConfigSpec.IntValue FIXED_PEACEFUL_DAYS;
    public static final ForgeConfigSpec.IntValue POST_PEACEFUL_DIFFICULTY;
    public static final ForgeConfigSpec.BooleanValue DEV_MODE;

    static {
        BUILDER.push("Peaceful Days Configuration");

        PEACEFUL_MODE_ENABLED = BUILDER
                .comment("Enable Peaceful Mode Mechanics")
                .define("peacefulModeEnabled", true);

        PEACEFUL_MODE_TYPE = BUILDER
                .comment("Peaceful Day Selection Method")
                .defineEnum("peacefulModeType", PeacefulModeType.FIXED);

        FIXED_PEACEFUL_DAYS = BUILDER
                .comment("Number of days between Peaceful Days (if FIXED mode)")
                .defineInRange("fixedPeacefulDays", 20, 1, 100);

        POST_PEACEFUL_DIFFICULTY = BUILDER
                .comment("Difficulty after Peaceful Day")
                .defineInRange("postPeacefulDifficulty", 2, 0, 3);

        DEV_MODE = BUILDER
                .comment("Enable Developer Diagnostic Mode")
                .define("devMode", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public enum PeacefulModeType {
        FIXED,   // Peaceful day every X days
        RANDOM   // Random peaceful day selection
    }
}