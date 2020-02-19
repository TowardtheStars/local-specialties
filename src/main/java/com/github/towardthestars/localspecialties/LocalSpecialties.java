package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.config.Configs;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.towardthestars.localspecialties.config.MainConfig;

import java.io.IOException;
import java.net.URL;

public class LocalSpecialties implements ModInitializer
{
    public static Logger LOGGER = LogManager.getFormatterLogger("LocalSpecialties");

    @Override
    public void onInitialize()
    {
        System.out.println(Configs.CONFIG_DIR);
        Configs.save();
    }

    public static URL getResource(String path)
    {
        return LocalSpecialties.class.getClassLoader().getResource(path);
    }
}
