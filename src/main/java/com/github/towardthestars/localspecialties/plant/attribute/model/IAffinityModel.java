package com.github.towardthestars.localspecialties.plant.attribute.model;

public interface IAffinityModel<ENV_ATTR_RET_TYPE>
{
    double getMultiplier(ENV_ATTR_RET_TYPE envValue);
    Class getType();
    IAffinityModel<ENV_ATTR_RET_TYPE> withArgs(Object... args);
}
