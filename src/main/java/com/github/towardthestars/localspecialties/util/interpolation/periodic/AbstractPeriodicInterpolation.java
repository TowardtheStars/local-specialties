package com.github.towardthestars.localspecialties.util.interpolation.periodic;

import com.github.towardthestars.localspecialties.util.Point2D;
import com.github.towardthestars.localspecialties.util.interpolation.IInterpolation;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPeriodicInterpolation implements IPeriodicInterpolation
{
    double[] xs; // (0 ~ n, size = n + 1)
    double[] ys; // (0 ~ n, size = n + 1)

    int n;
    double[] hList; // hList[i] = xs[i + 1] - xs[i]

    @Override
    public IInterpolation load(Double[] xs, Double[] ys)
    {
        Preconditions.checkArgument(xs.length == ys.length);
        List<Point2D> points = new ArrayList<>();
        for (int i = 0; i < xs.length; i++)
        {
            points.add(new Point2D(xs[i], ys[i]));
        }
        return load(points);
    }

    @Override
    public IInterpolation load(List<Point2D> points)
    {
        points.sort((point1, point2) -> (int)(point1.x - point2.x));
        n = points.size() - 1;
        this.xs = new double[points.size()];
        this.ys = new double[points.size()];
        this.hList = new double[xs.length - 1];
        int i = 0;
        for (Point2D p : points)
        {
            this.xs[i] = p.x;
            this.ys[i] = p.y;
            i++;
        }
        for (i = 0; i < n; i++)
        {
            this.hList[i] = this.xs[i + 1] - this.xs[i];
        }
        reload();
        return this;
    }

    protected abstract void reload();

    public double getPeriod()
    {
        return xs[n] - xs[0];
    }

    /**
     * Clamp x into xs[0] ~ xs[n]
     * @param x input
     * @return value between xs[0] and xs[n]
     */
    double clamp(double x)
    {
        return x > xs[n] || x < xs[0]? x % getPeriod() + xs[0] : x;
    }

    int getBox(double x)
    {
        int min = 0;
        int max = n;
        x = clamp(x);
        while (max - min > 1)
        {
            int crit = (min + max) / 2;
            if (x >= this.xs[crit])
            {
                min = crit;
            } else
            {
                max = crit;
            }
        }
        return min;
    }

}
