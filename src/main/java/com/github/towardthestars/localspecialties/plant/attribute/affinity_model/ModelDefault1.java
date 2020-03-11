package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ModelDefault1 implements IAffinityModel<Object>
{
    @Override
    public double getMultiplier(Object envValue)
    {
        return 1;
    }

    @Override
    public Class getType()
    {
        return Object.class;
    }

    @Override
    public IAffinityModel<Object> withArgs(Object... args)
    {
        return this;
    }
}
