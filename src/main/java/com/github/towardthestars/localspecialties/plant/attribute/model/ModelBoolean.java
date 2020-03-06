package com.github.towardthestars.localspecialties.plant.attribute.model;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
public class ModelBoolean implements IAffinityModel<Boolean>
{
    @Getter @Setter
    double valueForFalse;
    @Getter @Setter
    double valueForTrue;

    @Override
    public double getMultiplier(Boolean envValue)
    {
        return envValue ? valueForTrue : valueForFalse;
    }

    @Override
    public Class getType()
    {
        return Boolean.class;
    }

    /**
     *
     * @param args arg[0] is value for false, arg[1] is value for true
     * @return this
     */
    @Override
    public IAffinityModel<Boolean> withArgs(Double... args)
    {
        Preconditions.checkArgument(args.length >= 2, "Model for Boolean type attribute needs at least 2 value as args");
        this.valueForFalse = args[0];
        this.valueForTrue = args[1];
        return this;
    }
}
