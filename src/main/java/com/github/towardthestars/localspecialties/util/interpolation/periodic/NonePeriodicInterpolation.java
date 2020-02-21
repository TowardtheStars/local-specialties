package com.github.towardthestars.localspecialties.util.interpolation.periodic;

public class NonePeriodicInterpolation extends AbstractPeriodicInterpolation
{
    @Override
    protected void reload()
    {
    }

    @Override
    public double apply(double x)
    {
        return ys[getBox(x)];
    }
}
