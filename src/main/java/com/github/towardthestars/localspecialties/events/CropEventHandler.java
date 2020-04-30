package com.github.towardthestars.localspecialties.events;

import com.github.towardthestars.localspecialties.plant.IWithAttributeAffinity;
import com.github.towardthestars.localspecialties.plant.attribute.PlantAttributes;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("unused")
public class CropEventHandler
{
    @SubscribeEvent
    public void harvestModifier(BlockEvent.HarvestDropsEvent event)
    {
        BlockState state = event.getState();
        if (state.getBlock() instanceof CropsBlock)
        {
            IWithAttributeAffinity block = (IWithAttributeAffinity) state.getBlock();
            float mul = block.getManager().getPlantAttributeValue
                    (
                            PlantAttributes.HARVEST, state,
                            event.getWorld(), event.getPos(),
                            event.getWorld().getRandom()
                    );
            event.setDropChance(event.getDropChance() * mul);
        }
    }
}
