package com.github.towardthestars.localspecialties.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import configuration.Config;
import configuration.parser.ConfigParsers;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class MainConfig extends AbstractConfig
{
    public final boolean ENABLE_SEASON;
    public MainConfig(File file)
    {
        super(file);
        ENABLE_SEASON = config.getBoolean("enable_season", true);
        config.set("enable_season", ENABLE_SEASON);
    }
}
