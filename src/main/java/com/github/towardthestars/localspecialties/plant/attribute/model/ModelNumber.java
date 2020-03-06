package com.github.towardthestars.localspecialties.plant.attribute.model;

public abstract class ModelNumber implements IAffinityModel<Number>
{
    @Override
    public Class getType()
    {
        return Number.class;
    }

}
