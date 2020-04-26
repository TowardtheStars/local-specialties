package com.github.towardthestars.localspecialties.config;



import java.io.File;

abstract class AbstractConfig
{
//    final Config config;
    private File file;
    AbstractConfig(File file)
    {
        this.file = file;
        if (file.exists() && file.isFile())
        {
//            config = ConfigParsers.load(file);
        }else{
//            config = new Config();
        }
    }
    void save()
    {
//        ConfigParsers.save(file, config);
    }


}
