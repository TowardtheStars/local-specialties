package com.github.towardthestars.localspecialties.util;

import com.google.common.base.Preconditions;

import java.util.Arrays;

public class Maths
{
    public static double[] differenceQuotient(double[] xs, double[] ys, int order)
    {
        Preconditions.checkArgument(xs.length == ys.length, "Size of xs and ys must be same");
        Preconditions.checkArgument(order >= 0, "Order must be 0 or positive");
        Preconditions.checkArgument(order < ys.length, "Order should be less than point list size");
        double[] result = ys.clone();
        for (int o = 1; o <= order; o++)
        {
            for (int i = 0; i < xs.length - o; i++)
            {
                ys[i] = (ys[i + 1] - ys[i]) / (xs[i + o] - xs[i]);
            }
        }
        result = Arrays.copyOf(result, ys.length - order);
        return result;
    }
}
