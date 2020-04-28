package com.github.towardthestars.localspecialties.environment.soil;

import com.github.towardthestars.localspecialties.BlockLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class FarmLandHelper
{
    private static final BlockState[] FERTILITY_TO_STATE = new BlockState[8];
    private static final Map<BlockState, BlockState> STATE_FERTILITY_MAP = new HashMap<>();

    private static BlockState getDefaultStateWithFertility(int fertility)
    {
//        return BlockLoader.FARMLAND.getDefaultState().with(BlockFarmland.FERTILITY, fertility);
        return Blocks.FARMLAND.getDefaultState().with(LSProperties.FERTILITY, fertility);
    }

    public static void addDirtStateFertilityEntry(BlockState state, int fertility)
    {
        STATE_FERTILITY_MAP.put(state, getDefaultStateWithFertility(fertility));

    }



    public static void setDefaultDirtStateForFertility(int fertility, BlockState state, boolean canConvertToFarmland)
    {
        if (canConvertToFarmland)
        {
            STATE_FERTILITY_MAP.put(state, getDefaultStateWithFertility(fertility));
        }
        FERTILITY_TO_STATE[fertility] = state;
    }

    static {
        setDefaultDirtStateForFertility(4, Blocks.DIRT.getDefaultState(), true);
        addDirtStateFertilityEntry(Blocks.GRASS_BLOCK.getDefaultState(), 4);
        addDirtStateFertilityEntry(Blocks.GRASS_PATH.getDefaultState(), 5);
        setDefaultDirtStateForFertility(2, Blocks.COARSE_DIRT.getDefaultState(), true);
        setDefaultDirtStateForFertility(6, Blocks.PODZOL.getDefaultState(), true);
        setDefaultDirtStateForFertility(0, Blocks.GRAVEL.getDefaultState(), false);
    }

    public static BlockState getDirtStateForFertility(int fertility)
    {
        while (fertility >= 0 && FERTILITY_TO_STATE[fertility] == null)
        {
            fertility--;
        }
        return fertility >= 0? FERTILITY_TO_STATE[fertility] : Blocks.GRAVEL.getDefaultState();
    }

    /**
     *
     * @param state block state
     * @return Farmland BlockState with fertility
     */
    @Nullable
    public static BlockState getFarmlandForDirt(BlockState state)
    {
        return STATE_FERTILITY_MAP.getOrDefault(state, null);
    }

    public static int getFertility(BlockState state)
    {
        return state.get(LSProperties.FERTILITY);
    }

}
