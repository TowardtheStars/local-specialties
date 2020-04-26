package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.plant.attribute.merge_model.IMergeModel;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.HarvestScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.ViabilityScheme;
import com.github.towardthestars.localspecialties.util.IStatisticsScheme;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.*;


public class AttributeAffinityManager
{
    private final Map<PlantAttribute, IMergeModel> affinityMap = new HashMap<>();

    public AttributeAffinityManager()
    {
    }

    @Deprecated
    public int getGrowth(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        return this.getPlantAttributeValue(PlantAttributes.GROWTH, state, world, pos, random);
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


    public <ATTR_RET_TYPE> ATTR_RET_TYPE getPlantAttributeValue(PlantAttribute<ATTR_RET_TYPE> attribute, BlockState state, IWorld world, BlockPos pos, Random random)
    {
        if (this.affinityMap.containsKey(attribute))
            return this.affinityMap.get(attribute).rollValue(attribute.getDefaultScheme(), state, world, pos, random);
        return attribute.getDefaultScheme().roll(random);
    }

    public AttributeAffinityManager setPlantAttributeAffinityModel(PlantAttribute attribute, IMergeModel model)
    {
        this.affinityMap.put(attribute, model);
        return this;
    }

//    public Text toText()
//    {
//        return new LiteralText("");
//    }
}
