package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ModelDefault0 implements IAffinityModel<Object>
{
    @Override
    public double getMultiplier(Object envValue)
    {
        return 0;
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
