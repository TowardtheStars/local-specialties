package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ModelNumberQuadratic extends ModelNumber
{
    @Getter @With
    double leftPoint;
    @Getter @With
    double rightPoint;
    @Getter @With
    double a;

    @Override
    public ModelNumber withArgs(Object... argv)
    {
        Double[] args = (Double[]) argv;
        ModelNumberQuadratic result = new ModelNumberQuadratic();
        result.a = args[0];
        result.leftPoint = args[1];
        result.rightPoint = args[2];
        return result;
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
