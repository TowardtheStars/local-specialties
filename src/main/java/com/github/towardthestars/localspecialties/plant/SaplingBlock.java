package com.github.towardthestars.localspecialties.plant;

import net.minecraft.state.IntegerProperty;

/**
 * This is for plants such tree saplings.
 *  - Need a block to attach on
 *  - Transform into a structure when mature
 */
public class SaplingBlock extends PlantBlockBase
{
    public SaplingBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public int maxAge()
    {
        return 0;
    }


}
