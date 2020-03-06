package com.github.towardthestars.localspecialties.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.EntityContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

/**
 * This is for plants such wheat, carrots, and potatoes.
 *  - Harvesting needs to break block
 *  - Replanting needs to apply an item to a block
 *  - Growing update block state
 *  - Need a block to attach on
 */
public class CropBlock extends PlantBlockBase implements Fertilizable
{
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    public CropBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public int maxAge()
    {
        return 7;
    }

    @Override
    public IntProperty getAgeProperty()
    {
        return Properties.AGE_7;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
    {
        return true;
    }


    @Override
    public boolean isSimpleFullBlock(BlockState state, BlockView view, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
    {
        return this.isRipe(state);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        int age = MathHelper.clamp(MathHelper.nextInt(random, 2, 5), 0, this.maxAge());
        world.setBlockState(pos, state.with(this.getAgeProperty(), age));
    }
}
