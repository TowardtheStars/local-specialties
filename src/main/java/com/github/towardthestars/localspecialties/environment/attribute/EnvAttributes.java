package com.github.towardthestars.localspecialties.environment.attribute;

import com.github.towardthestars.localspecialties.environment.Seasons;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import com.github.towardthestars.localspecialties.environment.Season;
import com.github.towardthestars.localspecialties.soil.BlockFarmland;

import java.util.Hashtable;
import java.util.Map;

public class EnvAttributes
{
    public static final Map<String, EnvAttribute> ENV_ATTRIBUTE_MAP = new Hashtable<>();
    public static void register(EnvAttribute ...envAttributes)
    {
        for (EnvAttribute envAttribute: envAttributes)
        {
            ENV_ATTRIBUTE_MAP.put(envAttribute.getName(), envAttribute);
        }
    }

    public static final FloatEnvAttribute TEMPERATURE = new FloatEnvAttribute("temperature")
    {
        @Override
        public Float getAttribute(IWorld world, BlockPos pos)
        {
            float biomeBase = world.getBiome(pos).getTemperature(pos);
            long time = world.getLevelProperties().getTime();
            float seasonBase = Seasons.getTemperature(time);
            return biomeBase + seasonBase;
        }
    };

    public static final FloatEnvAttribute HUMIDITY = new FloatEnvAttribute("humidity")
    {
        @Override
        public Float getAttribute(IWorld world, BlockPos pos)
        {
            return world.getBiome(pos).getRainfall();
        }
    };

    public static final IntEnvAttribute LIGHT_LEVEL = new IntEnvAttribute("light_level")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            return world.getLightLevel(pos);
        }
    };

    public static final IntEnvAttribute SKYLIGHT = new IntEnvAttribute("sky_light")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            return world.getLightLevel(LightType.SKY, pos);
        }
    };

    public static final IntEnvAttribute MOISTURE = new IntEnvAttribute("moisture")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            BlockPos soilPos = pos.down();
            BlockState state = world.getBlockState(soilPos);
            if (!(state.getBlock() instanceof BlockFarmland))
            {
                return 0;
            }
            return state.get(BlockFarmland.MOISTURE);
        }
    };

    public static final IntEnvAttribute FERTILITY = new IntEnvAttribute("fertility")
    {
        @Override
        public Integer getAttribute(IWorld world, BlockPos pos)
        {
            BlockPos soilPos = pos.down();
            BlockState state = world.getBlockState(soilPos);
            if (!(state.getBlock() instanceof BlockFarmland))
            {
                return 0;
            }
            return state.get(BlockFarmland.FERTILITY);
        }
    };

    public static final BooleanEnvAttribute IS_DAY = new BooleanEnvAttribute("is_day")
    {
        @Override
        public Boolean getAttribute(IWorld world, BlockPos pos)
        {
            return world.getLevelProperties().getTimeOfDay() < 12000;
        }
    };

    public static final BooleanEnvAttribute IS_SKY_VISIBLE = new BooleanEnvAttribute("is_sky_visible")
    {
        @Override
        public Boolean getAttribute(IWorld world, BlockPos pos)
        {
            return world.isSkyVisible(pos);
        }
    };

    public static final BooleanEnvAttribute IS_RAINING = new BooleanEnvAttribute("is_raining")
    {
        @Override
        public Boolean getAttribute(IWorld world, BlockPos pos)
        {
            return (
                    world.getLevelProperties().isRaining()
                            || world.getLevelProperties().isThundering()
                    )
                    && world.isSkyVisible(pos);
        }
    };

    public static void registerAll(){
        register(
                TEMPERATURE,
                HUMIDITY,
                LIGHT_LEVEL,
                FERTILITY,
                MOISTURE,
                SKYLIGHT,
                IS_DAY,
                IS_RAINING,
                IS_SKY_VISIBLE
        );
    }
}
