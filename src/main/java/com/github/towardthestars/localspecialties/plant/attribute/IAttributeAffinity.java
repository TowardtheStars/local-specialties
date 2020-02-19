package com.github.towardthestars.localspecialties.plant.attribute;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import com.github.towardthestars.localspecialties.environment.attribute.EnvAttribute;

public interface IAttributeAffinity
{
    EnvAttribute getAttachedAttribute();
    float growSpeedExpectation(IWorld world, BlockPos pos);
    float growSpeedVariance(IWorld world, BlockPos pos);
    float productivityExpectation(IWorld world, BlockPos pos);
    float productivityVariance(IWorld world, BlockPos pos);
    float wither(IWorld world, BlockPos pos);
}
