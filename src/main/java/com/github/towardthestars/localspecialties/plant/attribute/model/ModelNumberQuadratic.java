package com.github.towardthestars.localspecialties.plant.attribute.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ModelNumberQuadratic extends ModelNumber
{
    @Getter @Setter
    double leftPoint;
    @Getter @Setter
    double rightPoint;
    @Getter @Setter
    double a;

    @Override
    public ModelNumber withArgs(Object... argv)
    {
        Double[] args = (Double[]) argv;
        this.a = args[0];
        this.leftPoint = args[1];
        this.rightPoint = args[2];
        return this;
    }

    public void setPeak(double peakValue)
    {
        double x = (leftPoint + rightPoint) * .5;
        this.a = peakValue / Math.pow(x, 2);
    }

    @Override
    public double getMultiplier(Number envValue)
    {
        double x = envValue.doubleValue();
        if (x > rightPoint || x < leftPoint)
        {
            return 0;
        }
        return (x - leftPoint) * (x - rightPoint) * a;
    }
}
