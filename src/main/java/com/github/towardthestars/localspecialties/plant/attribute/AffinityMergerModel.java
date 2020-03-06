package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import net.minecraft.block.BlockState;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class AffinityMergerModel
{
    @Delegate
    private final List<Affinity> affinityList = new ArrayList<>();

    @AllArgsConstructor
    public enum MergeType implements StringIdentifiable
    {
        MUL("MUL"){
            @Override
            public float apply(float origin, float modifier)
            {
                return origin * modifier;
            }
        },
        ADD("ADD"){
            @Override
            public float apply(float origin, float modifier)
            {
                return origin + modifier;
            }
        };

        public abstract float apply(float origin, float modifier);
        String name;

        @Override
        public String asString()
        {
            return name;
        }

    }

    private MergeType mergeType;

    public <ATTR_RET_TYPE> ATTR_RET_TYPE rollValue(PlantAttribute<ATTR_RET_TYPE> plantAttribute, BlockState state, IWorld world, BlockPos pos, Random random)
    {

        final IStatisticsScheme<ATTR_RET_TYPE> statisticsScheme = plantAttribute.getDefaultScheme();
        float exp = statisticsScheme.getExpectation();
        float var = statisticsScheme.getVariance();
        for (Affinity affinity : affinityList)
        {
            switch (affinity.parameterType)
            {
                case VARIANCE:
                    var = this.mergeType.apply(var, (float) affinity.getModifier(world, pos));
                    break;
                case EXPECTATION:
                    exp = this.mergeType.apply(exp, (float) affinity.getModifier(world, pos));
                    break;
            }
        }
        var = statisticsScheme.clampVar(var);
        exp = statisticsScheme.clampExp(exp);
        return statisticsScheme.withExpectation(exp).withVariance(var).roll(random);
    }
}
