package com.github.towardthestars.localspecialties.config;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.plant.attribute.AssembleHelper;
import net.java.games.input.Component;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class PlantAffinityConfig extends AbstractConfig
{
    PlantAffinityConfig(File file)
    {
        super(file);
        if (file.isDirectory())
        {
            for (File namespace :
                    Objects.requireNonNull(file.listFiles()))
            {
                if (namespace.isDirectory())
                {
                    for (File field : Objects.requireNonNull(namespace.listFiles()))
                    {

                        ResourceLocation identifier = new ResourceLocation(namespace.getName(), field.getName());
                        try
                        {
                            AssembleHelper.ATTRIBUTE_AFFINITY_MANAGER_MAP.put(identifier, AssembleHelper.fromFile(field));
                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void save()
    {
        generateDefault();
//        super.save();
    }

    private static void generateDefault(){
        try
        {
            FileUtils.copyDirectory(
                    new File(LocalSpecialties.getResource("assets/localspecialties/config/crop_affinity").getFile()),
                    new File("config/localspecialties/crop_affinity")
            );
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
