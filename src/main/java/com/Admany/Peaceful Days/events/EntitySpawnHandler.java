package com.admany.peacefuldays.events;

import com.admany.peacefuldays.config.ConfigManager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "peacefuldays")
public class EntitySpawnHandler {
    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
        // Prevent hostile mob spawns during peaceful days
        if (ConfigManager.PEACEFUL_MODE_ENABLED.get() &&
                event.getEntity() instanceof Monster) {
            event.setResult(Event.Result.DENY);
        }
    }
}