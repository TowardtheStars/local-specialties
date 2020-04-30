package com.github.towardthestars.localspecialties.events;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.environment.soil.FarmLandHelper;
import com.github.towardthestars.localspecialties.environment.soil.LSProperties;
import com.github.towardthestars.localspecialties.util.BlockStateFlag;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FarmlandEventHandler
{
    @SubscribeEvent
    public ActionResultType onHoeUse(UseHoeEvent event)
    {
        World world = event.getContext().getWorld();
        BlockPos pos = event.getContext().getPos();
        BlockState state = world.getBlockState(pos);
        ItemUseContext context = event.getContext();
        PlayerEntity playerEntity = event.getPlayer();
        if (state.getBlock() == Blocks.FARMLAND)
        {
            world.playSound(playerEntity, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote)
            {
                int sourceFertility = FarmLandHelper.getFertility(state) - 1;
                boolean shouldDistribute = false;
                for (int i = 0; i < 9; ++i)
                {
                    BlockState targetState;
                    BlockPos targetPos = pos.add(i % 3 - 1, 0, i / 3 - 1);
                    LocalSpecialties.LOGGER.debug("Tilling pos: " + targetPos.toString());
                    targetState = world.getBlockState(targetPos);
                    if (targetState.getBlock() == Blocks.FARMLAND)
                    {
                        int targetFertility = FarmLandHelper.getFertility(targetState);
                        LocalSpecialties.LOGGER.debug(String.format("Target fertility: %d", targetFertility));
                        if (targetFertility < sourceFertility)
                        {
                            // 不用 clamp, 因为0 <= 目标土壤肥力 < 源土壤肥力 <= 7 - 1 = 6
                            // 所以目标土壤肥力 + 1 <= 6
                            world.setBlockState(targetPos, targetState.with(LSProperties.FERTILITY, targetFertility + 1), BlockStateFlag.SEND_TO_CLIENT);
                            shouldDistribute = true;
                        }
                    }
                }

                if (shouldDistribute)
                {
                    world.setBlockState
                            (
                                    pos, state.with(LSProperties.FERTILITY, sourceFertility),
                                    BlockStateFlag.BUD | BlockStateFlag.SEND_TO_CLIENT | BlockStateFlag.FORCE_RERENDERING
                            );
                }
                if (playerEntity != null)
                {
                    event.getContext().getItem().damageItem(1, playerEntity, (p_220043_1_) ->
                    {
                        p_220043_1_.sendBreakAnimation(context.getHand());
                    });
                }
            }
            return ActionResultType.SUCCESS;
        }else{
            BlockState turnTo = FarmLandHelper.getFarmlandForDirt(state);
            if (turnTo != null)
            {

                world.playSound(playerEntity, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote)
                {
                    world.setBlockState(pos, turnTo, 11);
                    if (playerEntity != null) {
                        event.getContext().getItem().damageItem(1, playerEntity, (p_220043_1_) -> {
                            p_220043_1_.sendBreakAnimation(context.getHand());
                        });
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @SubscribeEvent
    public ActionResultType fertilizeFarmland(BonemealEvent event)
    {
        BlockState state = event.getBlock();
        if (state.getBlock() == Blocks.FARMLAND)
        {
            int fertility = state.get(LSProperties.FERTILITY);
            if (fertility < 7)
            {
                if (!event.getWorld().isRemote)
                    event.getWorld().setBlockState(event.getPos(), state.with(LSProperties.FERTILITY, fertility + 1), 2);
                return ActionResultType.CONSUME;
            }

        }
        return ActionResultType.PASS;
    }
}
