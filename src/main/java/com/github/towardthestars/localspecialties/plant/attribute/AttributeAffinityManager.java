package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.plant.PlantBlockBase;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.GrowthScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.HarvestScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.ViabilityScheme;
import com.github.towardthestars.localspecialties.util.IStatisticsScheme;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class AttributeAffinityManager
{
    PlantBlockBase plantBlock;

    private final Map<PlantAttribute, AffinityMergerModel> affinityMap = new HashMap<>();

    public AttributeAffinityManager()
    {
    }

    @Deprecated
    public int getGrowth(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
//        return getGrowthScheme(state, world, pos).roll(random);
        return affinityMap.get(PlantAttributes.GROWTH).rollValue(PlantAttributes.GROWTH, state, world, pos, random);
    }

    @Deprecated
    private IStatisticsScheme<Integer> getGrowthScheme(BlockState state, ServerWorld world, BlockPos pos)
    {
        return GrowthScheme.fromExpVar(1, 0);
    }

    @Deprecated
    public float getHarvest(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        return getHarvestScheme(state, world, pos).roll(random);
    }

    @Deprecated
    private IStatisticsScheme<Float> getHarvestScheme(BlockState state, ServerWorld world, BlockPos pos)
    {
        return HarvestScheme.fromExpVar(1, 0.1f);
    }

    @Deprecated
    private IStatisticsScheme<Boolean> getWitheringScheme(BlockState state, ServerWorld world, BlockPos pos)
    {
        return ViabilityScheme.fromP(0);
    }

    @Deprecated
    public boolean shouldWither(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        return getWitheringScheme(state, world, pos).roll(random);
    }


}
