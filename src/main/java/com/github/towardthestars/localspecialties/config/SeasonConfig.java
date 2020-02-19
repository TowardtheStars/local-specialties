package com.github.towardthestars.localspecialties.config;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.environment.EnumSmoothWay;
import com.github.towardthestars.localspecialties.environment.Season;
import com.github.towardthestars.localspecialties.environment.Seasons;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class SeasonConfig extends AbstractConfig
{

    public final EnumSmoothWay TEMPERATURE_SMOOTH;
    public final EnumSmoothWay RAINFALL_SMOOTH;

    SeasonConfig(File file)
    {
        super(file);
        String seasonsProfile = config.getString("seasons_profile", "default.json");
        loadFromJson(Paths.get(Configs.CONFIG_DIR, "seasons", seasonsProfile).toFile());
        TEMPERATURE_SMOOTH = EnumSmoothWay.valueOf(config.getString("temperature_smooth", "CUBIC_SPLINE"));
        RAINFALL_SMOOTH = EnumSmoothWay.valueOf(config.getString("rainfall_smooth", "CUBIC_SPLINE"));

        config.set("seasons_profile", seasonsProfile);
        config.set("temperature_smooth", TEMPERATURE_SMOOTH.toString());
        config.set("rainfall_smooth", RAINFALL_SMOOTH.toString());
    }

    private static void loadFromJson(File file)
    {
        if (!file.exists())
        {
            generateDefault();
        }

        try{
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            for (JsonElement jsonElement : array)
            {
                Seasons.SEASONS.add(gson.fromJson(jsonElement, Season.class));
            }
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static void generateDefault(){
        try
        {
            FileUtils.copyDirectory(
                    new File(LocalSpecialties.getResource("assets/localspecialties/config/seasons").getFile()),
                    new File("config/localspecialties/seasons")
            );
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
