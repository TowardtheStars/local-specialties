package com.github.towardthestars.localspecialties.environment;

import com.github.towardthestars.localspecialties.environment.attribute.EnvAttribute;
import com.github.towardthestars.localspecialties.environment.attribute.EnvAttributes;
import com.github.towardthestars.localspecialties.environment.soil.BlockFarmland;
import com.github.towardthestars.localspecialties.plant.PlantBlockBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemEnvironmentChecker extends Item
{
    public ItemEnvironmentChecker(Settings settings)
    {
        super(settings);
    }

    private static final List<EnvAttribute> USE_ON_AIR_ATTRIBUTES = new ArrayList<>();
    public static void registerAttributeUseOnAir(EnvAttribute... attributes)
    {
        USE_ON_AIR_ATTRIBUTES.addAll(Arrays.asList(attributes));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        BlockPos pos = user.getBlockPos();
        for (EnvAttribute attr : USE_ON_AIR_ATTRIBUTES)
        {
            user.sendMessage(EnvAttributes.getAttributeInfo(attr, world, pos));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Optional<PlayerEntity> player = Optional.ofNullable(context.getPlayer());
        if (state.getBlock() instanceof PlantBlockBase)
        {
            player.ifPresent(playerEntity -> playerEntity.sendMessage(
                    ((PlantBlockBase) state.getBlock()).getAffinityManager().toText()
            ));
            return ActionResult.SUCCESS;
        }
        else if (state.getBlock() instanceof BlockFarmland)
        {
            player.ifPresent(playerEntity -> {
                playerEntity.sendMessage(
                        EnvAttributes.getAttributeInfo(EnvAttributes.FERTILITY, world, pos)
                );
                playerEntity.sendMessage(
                        EnvAttributes.getAttributeInfo(EnvAttributes.MOISTURE, world, pos)
                );
            });
            return ActionResult.SUCCESS;
        }
        player.ifPresent(playerEntity -> {
            playerEntity.sendMessage(new LiteralText(
                    I18n.translate("item.env_checker.help_msg")
            ));
        });
        return ActionResult.PASS;
    }
}
