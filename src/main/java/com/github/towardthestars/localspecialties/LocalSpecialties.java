package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.command.Commands;
import com.github.towardthestars.localspecialties.config.Configs;
import com.github.towardthestars.localspecialties.environment.Seasons;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class LocalSpecialties implements ModInitializer
{
    public static Logger LOGGER = LogManager.getFormatterLogger("LocalSpecialties");
    public static String MOD_ID = "localspecialties";

    @Override
    public void onInitialize()
    {
        System.out.println(Configs.CONFIG_DIR);
        Configs.save();
        if (Configs.MAIN.ENABLE_SEASON)
        {
            Seasons.load();
        }

        Commands.registerAll();
        BlockLoader.registerAll();
        ItemLoader.registerAll();
        EventHandlers.registerAll();
    }

    public static URL getResource(String path)
    {
        return LocalSpecialties.class.getClassLoader().getResource(path);
    }

    public static Identifier getIdentifier(String name)
    {
        return new Identifier(MOD_ID, name);
    }
}
