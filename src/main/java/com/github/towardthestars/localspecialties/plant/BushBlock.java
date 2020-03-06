package com.github.towardthestars.localspecialties.plant;


import net.minecraft.state.property.IntProperty;

/**
 * This is for plants such berries, pumpkin stems and melon stems.
 *  - Replanting is unnecessary
 *  - Return to specific block state after harvesting
 *  - Need a block to attach on
 */
public class BushBlock extends PlantBlockBase
{
    public BushBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public int maxAge()
    {
        return 0;
    }

}
