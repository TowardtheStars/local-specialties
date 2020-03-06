package com.github.towardthestars.localspecialties;

import net.fabricmc.api.ClientModInitializer;

public class LocalSpecialtiesClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        BlockLoader.clientInit();
    }
}
