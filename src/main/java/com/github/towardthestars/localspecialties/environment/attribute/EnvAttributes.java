package com.github.towardthestars.localspecialties.environment.attribute;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.Registries;
import com.github.towardthestars.localspecialties.config.Configs;
import com.github.towardthestars.localspecialties.environment.ItemEnvironmentChecker;
import com.github.towardthestars.localspecialties.environment.Seasons;
import com.github.towardthestars.localspecialties.environment.soil.BlockFarmland;
import com.github.towardthestars.localspecialties.environment.soil.FarmLandHelper;
import com.github.towardthestars.localspecialties.environment.soil.LSProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class EnvAttributes
{

    public static final FloatEnvAttribute TEMPERATURE = new FloatEnvAttribute("temperature")
    {
        @Override
        public Float getAttribute(IWorld world, BlockPos pos)
        {
            float biomeBase = world.getBiome(pos).getTemperature(pos);
//            if (Configs.MAIN.ENABLE_SEASON)
//            {
//                long time = world.getWorldInfo().getGameTime();
//                float seasonBase = Seasons.getTemperature(time);
//                return biomeBase + seasonBase * HUMIDITY.getAttribute(world, pos);
//
//            }
            return biomeBase;
        }
    };

    public static final FloatEnvAttribute HUMIDITY = new FloatEnvAttribute("humidity")
    {
        @Override
        public Float getAttribute(IWorld world, BlockPos pos)
        {
//            if (!Configs.MAIN.ENABLE_SEASON)
//            {
//                return world.getBiome(pos).getDownfall() + Seasons.getRainfall(world.getWorldInfo().getGameTime());
//            }
            return world.getBiome(pos).getDownfall();
        }
    };

    public static final IntEnvAttribute LIGHT_LEVEL = new IntEnvAttribute("light_level")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            return world.getLight(pos);
        }
    };

    public static final IntEnvAttribute SKYLIGHT = new IntEnvAttribute("sky_light")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            return world.getWorld().getSkylightSubtracted();
        }
    };

    public static final IntEnvAttribute MOISTURE = new IntEnvAttribute("moisture")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            BlockPos soilPos = pos.down();
            BlockState state = world.getBlockState(soilPos);
//            if (!(state.getBlock() instanceof BlockFarmland))
//            {
//                return 0;
//            }
            return state.get(FarmlandBlock.MOISTURE);
        }
    };

    public static final IntEnvAttribute FERTILITY = new IntEnvAttribute("fertility")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            BlockPos soilPos = pos.down();
            BlockState state = world.getBlockState(soilPos);
//            if (!(state.getBlock() instanceof BlockFarmland))
//            {
//                Optional<BlockState> farmlandState = Optional.ofNullable(FarmLandHelper.getFarmlandForDirt(state));
//                if (farmlandState.isPresent())
//                {
//                    return farmlandState.get().get(BlockFarmland.FERTILITY);
//                }
//                return 0;
//            }
            return state.get(LSProperties.FERTILITY);
        }
    };

    public static final BooleanEnvAttribute IS_DAY = new BooleanEnvAttribute("is_day")
    {
        @Override
        public Boolean getAttribute(IWorld world, BlockPos pos)
        {
            return world.getWorldInfo().getDayTime() < 12000;
        }
    };

    public static final BooleanEnvAttribute IS_SKY_VISIBLE = new BooleanEnvAttribute("is_sky_visible")
    {
        @Override
        public Boolean getAttribute(IWorld world, BlockPos pos)
        {
            return world.canBlockSeeSky(pos);
        }
    };

    public static final BooleanEnvAttribute IS_RAINING = new BooleanEnvAttribute("is_raining")
    {
        @Override
        public Boolean getAttribute(IWorld world, BlockPos pos)
        {
            return (
                    world.getWorldInfo().isRaining()
                            || world.getWorldInfo().isThundering()
                    )
                    && world.canBlockSeeSky(pos);
        }
    };

//    public static Text getAttributeInfo(EnvAttribute attribute, IWorld world, BlockPos pos)
//    {
//        return new LiteralText(
//                I18n.translate(
//                        attribute.getTranslateKey()
//                ) + ": "
//        ).setStyle(new Style().setColor(Formatting.AQUA)).append(
//                new LiteralText(String.valueOf(attribute.getAttribute(world, pos))).setStyle(new Style().setColor(Formatting.WHITE))
//        );
//    }


    private static void register(EnvAttribute... attributes)
    {
        for (EnvAttribute attribute: attributes)
        {
            Registry.register(Registries.ENV_ATTRIBUTE, LocalSpecialties.getIdentifier(attribute.getName()), attribute);
        }

    }

    public static void registerAll(){
        register(
                TEMPERATURE,
                MOISTURE,
                HUMIDITY,
                IS_SKY_VISIBLE,
                IS_DAY,
                IS_RAINING,
                SKYLIGHT,
                FERTILITY,
                LIGHT_LEVEL
        );
//        ItemEnvironmentChecker.registerAttributeUseOnAir(TEMPERATURE, HUMIDITY, IS_SKY_VISIBLE);
    }
}
