package com.github.towardthestars.localspecialties.config;

import configuration.Config;
import configuration.parser.ConfigParsers;

import java.io.File;

public abstract class AbstractConfig
{
    protected final Config config;
    private File file;
    public AbstractConfig(File file)
    {
        this.file = file;
        if (file.exists())
        {
            config = ConfigParsers.load(file);
        }else{
            config = new Config();
        }
    }
    public void save()
    {
        ConfigParsers.save(file, config);
    }


}
