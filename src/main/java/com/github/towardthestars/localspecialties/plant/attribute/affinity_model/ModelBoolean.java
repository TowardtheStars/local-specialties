package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import com.google.common.base.Preconditions;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
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
     * @param argv arg[0] is value for false, arg[1] is value for true
     * @return this
     */
    @Override
    public IAffinityModel<Boolean> withArgs(Object... argv)
    {
        Double[] args = (Double[]) argv;
        Preconditions.checkArgument(args.length >= 2, "Model for Boolean type attribute needs at least 2 value as args");
        this.valueForFalse = args[0];
        this.valueForTrue = args[1];
        return this;
    }
}
