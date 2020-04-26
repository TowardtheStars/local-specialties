package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.command.Commands;
import com.github.towardthestars.localspecialties.config.Configs;
import com.github.towardthestars.localspecialties.environment.Seasons;
import com.github.towardthestars.localspecialties.plant.Plants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

@Mod("localspecialties")
public class LocalSpecialties
{
    public static Logger LOGGER = LogManager.getFormatterLogger("LocalSpecialties");
    public static String MOD_ID = "localspecialties";


    public LocalSpecialties()
    {
        LOGGER.info("Initializing Local Specialties");
        Configs.save();
//        if (Configs.MAIN.ENABLE_SEASON)
//        {
//            Seasons.load();
//        }

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


    }

    public static URL getResource(String path)
    {
        return LocalSpecialties.class.getClassLoader().getResource(path);
    }

    public static ResourceLocation getIdentifier(String name)
    {
        return new ResourceLocation(MOD_ID, name);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            Commands.registerAll();
            BlockLoader.registerAll();
//            EventHandlers.registerAll();
            Plants.registerBlocks();
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegisterEvent) {
            ItemLoader.registerAll();
            Plants.registerItems();
        }
    }
}
