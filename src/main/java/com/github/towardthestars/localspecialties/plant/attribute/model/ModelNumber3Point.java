package com.github.towardthestars.localspecialties.plant.attribute.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;

@NoArgsConstructor
@AllArgsConstructor
public class ModelNumber3Point extends ModelNumber
{
    @Getter
    double leftPoint;
    @Getter
    double rightPoint;
    @Getter
    double middlePoint;
    @Getter
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
