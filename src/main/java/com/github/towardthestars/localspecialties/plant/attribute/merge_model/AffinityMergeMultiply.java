package com.github.towardthestars.localspecialties.plant.attribute.merge_model;

public class AffinityMergeMultiply extends AbstractAffinityMergeModel
{
    @Override
    float apply(float origin, float modifier)
    {
        return origin * modifier;
    }
}
