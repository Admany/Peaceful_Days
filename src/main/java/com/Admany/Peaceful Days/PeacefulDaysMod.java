package com.Admany.PeacefulDays;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import com.Admany.PeacefulDays.config.PeacefulDaysConfig;

@Mod("peacefuldays")
public class PeacefulDaysMod {
    public static final String MOD_ID = "peacefuldays";

    public PeacefulDaysMod() {
        // Register configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, PeacefulDaysConfig.SPEC);
    }
}