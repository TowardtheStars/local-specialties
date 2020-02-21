package com.github.towardthestars.localspecialties.util.interpolation.periodic;

public class LinearPeriodicInterpolation extends AbstractPeriodicInterpolation
{
    private double[] ks;

    @Override
    protected void reload()
    {
        ks = new double[this.xs.length - 1];
        for (int i = 0; i < this.n; i++)
        {
            ks[i] = (ys[i + 1] - ys[i]) / hList[i];
        }
    }

    @Override
    public double apply(double x)
    {
        x = clamp(x);
        int k = getBox(x);
        return ks[k] * (x - xs[k]);
    }
}
