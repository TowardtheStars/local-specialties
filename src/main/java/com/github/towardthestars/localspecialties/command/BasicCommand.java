package com.github.towardthestars.localspecialties.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class BasicCommand
{
    public abstract String name();
    abstract LiteralArgumentBuilder<ServerCommandSource> build(LiteralArgumentBuilder<ServerCommandSource> command);


    void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(this.build(CommandManager.literal(this.name())));
    }
}
