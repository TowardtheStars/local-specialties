package com.github.towardthestars.localspecialties.plant;

import net.minecraft.state.IntegerProperty;

/**
 * This is for plants such as flowers and vines.
 *  - Replanting is unnecessary
 *  - Spread themselves to duplicate
 *  - Need a block to attach on
 */
public class SpreadPlantBlock extends PlantBlockBase
{
    public SpreadPlantBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public int maxAge()
    {
        return 0;
    }

}
