package com.github.towardthestars.localspecialties.util.interpolation;

import com.github.towardthestars.localspecialties.util.interpolation.periodic.*;


public enum EnumPeriodicSmoothMethod
{
    NO_SMOOTH(NonePeriodicInterpolation.class),
    LINEAR(LinearPeriodicInterpolation.class),
    CUBIC_SPLINE(CubicSplinePeriodicInterpolation.class);

    Class<? extends IPeriodicInterpolation> interpolationClass;
    EnumPeriodicSmoothMethod(Class<? extends IPeriodicInterpolation> clazz)
    {
        this.interpolationClass = clazz;
    }

    public IPeriodicInterpolation create()
    {
        try
        {
            return this.interpolationClass.getConstructor().newInstance();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
