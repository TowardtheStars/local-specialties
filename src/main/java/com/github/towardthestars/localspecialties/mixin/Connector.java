package com.github.towardthestars.localspecialties.mixin;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class Connector implements IMixinConnector
{
    /**
     * Connect to Mixin
     */
    @Override
    public void connect()
    {
        LocalSpecialties.LOGGER.info("Invoking Mixin connector");
        Mixins.addConfiguration("localspecialties.mixins.json");
    }
}
