package com.github.towardthestars.localspecialties.util;

import java.util.Random;

public interface IStatisticsScheme<ResultType>
{
    IStatisticsScheme<ResultType> setExpectation(float expectation);
    IStatisticsScheme<ResultType> setVariance(float variance);
    float getExpectation();
    float getVariance();

    ResultType roll(Random random);

}
