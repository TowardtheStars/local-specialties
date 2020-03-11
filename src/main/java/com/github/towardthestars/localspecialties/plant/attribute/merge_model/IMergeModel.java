package com.github.towardthestars.localspecialties.plant.attribute.merge_model;

import com.github.towardthestars.localspecialties.plant.attribute.Affinity;
import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.List;
import java.util.Random;

public interface IMergeModel extends List<Affinity>
{
    <ATTR_RET_TYPE> ATTR_RET_TYPE rollValue(IStatisticsScheme<ATTR_RET_TYPE> statisticsScheme, BlockState state, IWorld world, BlockPos pos, Random random);
}
