package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.soil.BlockFarmland;
import com.github.towardthestars.localspecialties.soil.FarmLandHelper;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

class EventHandlers
{
    static void registerAll(){
        UseBlockCallback.EVENT.register(EventHandlers::useHoe);
    }


    private static ActionResult useHoe(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult)
    {
        if (playerEntity != null && playerEntity.getStackInHand(hand).getItem() instanceof HoeItem)
        {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            if (direction != Direction.DOWN && world.getBlockState(blockPos.up()).isAir())
            {
                BlockState blockState = FarmLandHelper.getFarmlandForDirt(world.getBlockState(blockPos));
                if (blockState != null)
                {
                    world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!world.isClient)
                    {
                        world.setBlockState(blockPos, blockState, 11);
                        playerEntity.getStackInHand(hand).damage(1, playerEntity, (p) ->
                                p.sendToolBreakStatus(hand)
                        );
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }


}
