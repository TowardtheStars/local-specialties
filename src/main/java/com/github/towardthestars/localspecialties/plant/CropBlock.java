package com.github.towardthestars.localspecialties.plant;

/**
 * This is for plants such wheat, carrots, and potatoes.
 *  - Harvesting needs to break block
 *  - Replanting needs to apply an item to a block
 *  - Growing update block state
 *  - Need a block to attach on
 */
public class CropBlock extends PlantBlockBase
{
    public CropBlock(Settings settings)
    {
        super(settings);
    }
}
