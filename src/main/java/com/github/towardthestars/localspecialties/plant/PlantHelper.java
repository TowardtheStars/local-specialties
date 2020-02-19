package com.github.towardthestars.localspecialties.plant;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

public class PlantHelper
{
    public static class BiomeInfo{
        public final float temperature;
        public final float rainfall;
        BiomeInfo(float temperature, float rainfall){
            this.rainfall = rainfall;
            this.temperature = temperature;
        }
    }

    public static BiomeInfo getBiomeInfo(IWorld world, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        float temperature = biome.getTemperature(pos);
        float rainfall = biome.getRainfall();
        return new BiomeInfo(temperature, rainfall);
    }

    public static float getNearByCropModifier(IWorld world, BlockPos pos)
    {
        return 0.0f;
    }

}
