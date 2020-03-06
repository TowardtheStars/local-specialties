package com.github.towardthestars.localspecialties.plant;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface INutritionConsumer
{
    default boolean shouldConsumeFertility(World world, BlockPos pos, BlockState state, Random random)
    {
        return false;
    }

    default boolean shouldConsumeMoisture(World world, BlockPos pos, BlockState state, Random random)
    {
        return false;
    }
}
