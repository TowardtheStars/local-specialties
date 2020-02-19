package com.github.towardthestars.localspecialties.util.interpolation;

import com.github.towardthestars.localspecialties.util.Point2D;

import java.util.List;

public interface IInterpolation
{
    /**
     * Reload a new set of data
     * @param xs x coordinates
     * @param ys y coordinates
     * @return this
     */
    IInterpolation load(Double[] xs, Double[] ys);
    IInterpolation load(List<Point2D> points);

    /**
     * @param x feed x into this
     * @return interpolation value
     */
    double apply(double x);
}
