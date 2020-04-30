package com.github.towardthestars.localspecialties.events;

import com.github.towardthestars.localspecialties.plant.ICrop;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class CropEvent extends BlockEvent
{
    private final ICrop block;
    CropEvent(IWorld world, BlockPos pos, BlockState state)
    {
        super(world, pos, state);
        this.block = (ICrop) state.getBlock();
    }

    @Cancelable
    public static class CropWitherEvent extends CropEvent
    {
        private final BlockState witheredState;
        public CropWitherEvent(IWorld world, BlockPos pos, BlockState state, BlockState newState)
        {
            super(world, pos, state);
            this.witheredState = newState;
        }

        public BlockState getWitheredState()
        {
            return witheredState;
        }
    }

    @Cancelable
    public static class CropRipeEvent extends CropEvent
    {
        public CropRipeEvent(IWorld world, BlockPos pos, BlockState state)
        {
            super(world, pos, state);
        }
    }

    public static class CropConsumeMoistureEvent extends CropEvent
    {
        public CropConsumeMoistureEvent(IWorld world, BlockPos pos, BlockState state)
        {
            super(world, pos, state);
        }

        @HasResult
        public static class Pre extends CropConsumeMoistureEvent
        {
            public Pre(IWorld world, BlockPos pos, BlockState state)
            {
                super(world, pos, state);
            }
        }

        public static class Post extends CropConsumeMoistureEvent
        {
            public Post(IWorld world, BlockPos pos, BlockState state)
            {
                super(world, pos, state);
            }
        }
    }

    public static class CropConsumeFertilityEvent extends CropEvent
    {
        public CropConsumeFertilityEvent(IWorld world, BlockPos pos, BlockState state)
        {
            super(world, pos, state);
        }

        @HasResult
        public static class Pre extends CropConsumeFertilityEvent
        {
            public Pre(IWorld world, BlockPos pos, BlockState state)
            {
                super(world, pos, state);
            }
        }

        public static class Post extends CropConsumeFertilityEvent
        {
            public Post(IWorld world, BlockPos pos, BlockState state)
            {
                super(world, pos, state);
            }
        }
    }

}
