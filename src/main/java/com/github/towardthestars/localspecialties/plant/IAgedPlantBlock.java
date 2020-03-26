package com.github.towardthestars.localspecialties.plant;

import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public interface IAgedPlantBlock
{
    BlockState getBlockStateWithAge(int age);
    default int maxAge()
    {
        return 7;
    }

    default IntegerProperty getAgeProperty()
    {
        return BlockStateProperties.AGE_0_7;
    }

    default int getAge(BlockState state){
        return state.get(getAgeProperty());
    }

    default boolean isRipe(BlockState state){
        return getAge(state) >= maxAge();
    }

}
