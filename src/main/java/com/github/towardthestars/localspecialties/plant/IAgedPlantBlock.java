package com.github.towardthestars.localspecialties.plant;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public interface IAgedPlantBlock
{
    BlockState getBlockStateWithAge(int age);
    default int maxAge()
    {
        return 7;
    }

    default IntProperty getAgeProperty()
    {
        return Properties.AGE_7;
    }

    default int getAge(BlockState state){
        return state.get(getAgeProperty());
    }

    default boolean isRipe(BlockState state){
        return getAge(state) >= maxAge();
    }

}
