package com.github.towardthestars.localspecialties.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

public class MainConfig
{
    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue disableBoneMealGrowing;
        public final ForgeConfigSpec.BooleanValue enableAffinity;

        public Common(ForgeConfigSpec.Builder builder)
        {
            disableBoneMealGrowing = builder
                    .comment(
                            "If true, you cannot use bone meal to force a crop to grow, ",
                            "and applying bone meal to crops will only fertilize the farmland block underneath."
                    )
                    .translation("config.disableBoneMealGrowing")
                    .define("disableBoneMealGrowing", true);

            enableAffinity = builder
                    .comment(
                            "If true, crops will have affinity for environment parameters.",
                            "If false, crops won't be picky. They only consume fertility and moisture to grow up."
                    )
                    .translation("config.enableAffinity")
                    .define("enableAffinity", true);
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
