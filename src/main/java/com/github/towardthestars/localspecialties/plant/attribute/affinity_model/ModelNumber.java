package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import lombok.Getter;
import lombok.Setter;

public abstract class ModelNumber implements IAffinityModel<Number>
{
    @Getter
    @Setter
    private double base = 0;

    @Override
    public Class getType()
    {
        return Number.class;
    }

}
