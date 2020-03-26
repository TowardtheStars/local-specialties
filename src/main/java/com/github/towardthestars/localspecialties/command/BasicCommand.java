package com.github.towardthestars.localspecialties.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.Commands;
import net.minecraft.command.CommandSource;

public abstract class BasicCommand
{
    public abstract String name();
    abstract LiteralArgumentBuilder<CommandSource> build(LiteralArgumentBuilder<CommandSource> command);


    void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(this.build(Commands.literal(this.name())));
    }
}
