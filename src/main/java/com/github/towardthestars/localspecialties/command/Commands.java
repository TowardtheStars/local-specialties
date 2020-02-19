package com.github.towardthestars.localspecialties.command;

import net.fabricmc.fabric.api.registry.CommandRegistry;

public class Commands
{
    public static final EnvCommand ENV_COMMAND = new EnvCommand();

    private static void register(BasicCommand command, boolean dedicated)
    {
        CommandRegistry.INSTANCE.register(dedicated, command::register);
    }

    public static void registerAll()
    {
        register(ENV_COMMAND, false);
    }
}
