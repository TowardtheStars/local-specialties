package com.github.towardthestars.localspecialties.config;

import java.nio.file.Paths;

public class Configs
{
    public static final String CONFIG_DIR = Paths.get("config", "localspecialties").toString();
    public static final MainConfig MAIN = new MainConfig(Paths.get(CONFIG_DIR, "main.toml").toFile());
    public static final SeasonConfig SEASON = new SeasonConfig(Paths.get(CONFIG_DIR, "season.toml").toFile());
    public static final PlantAffinityConfig PLANT_AFFINITY = new PlantAffinityConfig(Paths.get(CONFIG_DIR, "plant_affinity").toFile());

    public static void save(){
        MAIN.save();
        SEASON.save();
    }
}
