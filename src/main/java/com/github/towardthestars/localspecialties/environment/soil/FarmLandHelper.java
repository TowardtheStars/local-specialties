package com.github.towardthestars.localspecialties.environment.soil;

import com.github.towardthestars.localspecialties.BlockLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class FarmLandHelper
{
    private static final BlockState[] FERTILITY_TO_STATE = new BlockState[8];
    private static final Map<BlockState, BlockState> STATE_FERTILITY_MAP = new HashMap<>();

    private static BlockState getDefaultStateWithFertility(int fertility)
    {
        return BlockLoader.FARMLAND.getDefaultState().with(BlockFarmland.FERTILITY, fertility);
    }

    public static void addDirtStateFertilityEntry(BlockState state, int fertility)
    {
        STATE_FERTILITY_MAP.put(state, getDefaultStateWithFertility(fertility));
        if (FERTILITY_TO_STATE[fertility] == null)
        {
            FERTILITY_TO_STATE[fertility] = state;
        }
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
        addDirtStateFertilityEntry(Blocks.DIRT.getDefaultState(), 4);
        addDirtStateFertilityEntry(Blocks.GRASS_BLOCK.getDefaultState(), 4);
        addDirtStateFertilityEntry(Blocks.GRASS_PATH.getDefaultState(), 5);
        addDirtStateFertilityEntry(Blocks.COARSE_DIRT.getDefaultState(), 2);
        addDirtStateFertilityEntry(Blocks.PODZOL.getDefaultState(), 6);
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
    public static BlockState getFarmlandForDirt(BlockState state)
    {
        return STATE_FERTILITY_MAP.get(state);
    }

}
