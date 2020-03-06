package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class PlantAttribute<ATTR_RET_TYPE>
{
    @Getter @Setter
    @NonNull
    private IStatisticsScheme<ATTR_RET_TYPE> defaultScheme;


}
