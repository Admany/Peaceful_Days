package com.admany.peacefuldays.events;

import com.admany.peacefuldays.config.ConfigManager;
import com.admany.peacefuldays.utils.ModLogger;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "peacefuldays")
public class ServerTickHandler {
    private static int currentDay = 0;
    private static boolean isPeacefulDay = false;
    private static int nextPeacefulDay = -1;
    private static long lastDayCheckTime = 0;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        try {
            // Only process on server side and not too frequently
            if (ConfigManager.PEACEFUL_MODE_ENABLED.get()) {
                handlePeacefulDayLogic(event);
            }
        } catch (Exception e) {
            ModLogger.error("Error in server tick handler", e);
        }
    }

    private static void handlePeacefulDayLogic(TickEvent.ServerTickEvent event) {
        ServerLevel overworld = event.getServer().getLevel(ServerLevel.OVERWORLD);
        if (overworld == null) return;

        long worldTime = overworld.getDayTime();

        // Check for new day (every 24000 ticks)
        if (worldTime % 24000 == 0 && worldTime != lastDayCheckTime) {
            lastDayCheckTime = worldTime;
            currentDay++;

            // Determine peaceful day based on config
            determinePeacefulDay(overworld);
        }

        // Manage peaceful day mechanics
        if (isPeacefulDay) {
            managePeacefulDayEffects(overworld);
        }
    }

    private static void determinePeacefulDay(ServerLevel world) {
        switch (ConfigManager.PEACEFUL_MODE_TYPE.get()) {
            case FIXED:
                isPeacefulDay = (currentDay % ConfigManager.FIXED_PEACEFUL_DAYS.get() == 0);
                break;
            case RANDOM:
                if (nextPeacefulDay == -1) {
                    nextPeacefulDay = new Random().nextInt(100) + 1;
                }
                isPeacefulDay = (currentDay == nextPeacefulDay);
                break;
        }

        if (isPeacefulDay) {
            ModLogger.info("Peaceful Day activated on day " + currentDay);
            world.setDifficulty(Difficulty.PEACEFUL);
        }
    }

    private static void managePeacefulDayEffects(ServerLevel world) {
        // Check if day is ending
        if (world.getDayTime() % 24000 >= 13000) {
            // End of peaceful day
            world.setDifficulty(Difficulty.values()[ConfigManager.POST_PEACEFUL_DIFFICULTY.get()]);
            isPeacefulDay = false;
            nextPeacefulDay = -1;
            ModLogger.info("Peaceful Day ended. Difficulty reset.");
        }
    }
}
