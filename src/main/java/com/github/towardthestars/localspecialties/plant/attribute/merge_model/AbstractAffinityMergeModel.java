package com.github.towardthestars.localspecialties.plant.attribute.merge_model;

import com.github.towardthestars.localspecialties.plant.attribute.Affinity;
import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public abstract class AbstractAffinityMergeModel implements IMergeModel
{
    @Delegate
    private final List<Affinity> affinityList = new ArrayList<>();

    abstract float apply(float origin, float modifier);

    public <ATTR_RET_TYPE> ATTR_RET_TYPE rollValue(IStatisticsScheme<ATTR_RET_TYPE> statisticsScheme, BlockState state, IWorld world, BlockPos pos, Random random)
    {
        float exp = statisticsScheme.getExpectation();
        float var = statisticsScheme.getVariance();
        for (Affinity affinity : affinityList)
        {
            switch (affinity.parameterType)
            {
                case VARIANCE:
                    var = this.apply(var, (float) affinity.getModifier(world, pos));
                    break;
                case EXPECTATION:
                    exp = this.apply(exp, (float) affinity.getModifier(world, pos));
                    break;
            }
        }
        var = statisticsScheme.clampVar(var);
        exp = statisticsScheme.clampExp(exp);
        return statisticsScheme.withExpectation(exp).withVariance(var).roll(random);
    }
}
