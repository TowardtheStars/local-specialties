package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@NoArgsConstructor
@AllArgsConstructor
public class ModelNumber3Point extends ModelNumber
{
    @Getter @With
    double leftPoint;
    @Getter @With
    double rightPoint;
    @Getter @With
    double middlePoint;
    @Getter @With
    double middlePointValue;

    @Override
    public double getMultiplier(Number envValue)
    {
        double x = envValue.doubleValue();
        if (x < leftPoint || x > rightPoint)
        {
            return 0;
        }
        else if (x > middlePoint)
        {
            return (x - rightPoint) / (middlePoint - rightPoint) * middlePointValue;
        }
        else
        {
            return (x - leftPoint) / (middlePoint - leftPoint) * middlePointValue;
        }
    }

    @Override
    public ModelNumber withArgs(Object... argv)
    {
        Double[] args = (Double[]) argv;
        this.leftPoint = args[0];
        this.middlePoint = args[1];
        this.middlePointValue = args[2];
        this.rightPoint = args[3];
        return this;
    }
}
