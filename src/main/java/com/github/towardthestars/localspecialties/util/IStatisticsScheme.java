package com.github.towardthestars.localspecialties.util;

import java.util.Random;

public interface IStatisticsScheme<ResultType>
{
    IStatisticsScheme setExpectation(float expectation);
    IStatisticsScheme setVariance(float variance);
    float getExpectation();
    float getVariance();

    ResultType roll(Random random);

}
