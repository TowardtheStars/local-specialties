package com.github.towardthestars.localspecialties.command;

import com.github.towardthestars.localspecialties.environment.attribute.EnvAttribute;
import com.github.towardthestars.localspecialties.environment.attribute.EnvAttributes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class EnvCommand extends BasicCommand
{
    @Override
    public String name()
    {
        return "localenv";
    }

    @Override
    LiteralArgumentBuilder<ServerCommandSource> build(LiteralArgumentBuilder<ServerCommandSource> command)
    {
        for (Map.Entry<String, EnvAttribute> attr :
                EnvAttributes.ENV_ATTRIBUTE_MAP.entrySet())
        {
            command = command.then(CommandManager.literal(attr.getKey()).executes((commandContext) -> {
                commandContext.getSource().sendFeedback(
                        new TranslatableText("localenv." + attr.getKey() + ".name")
                                .append(String.format(": %f", (float)(attr.getValue().getAttribute
                                                (
                                                        commandContext.getSource().getWorld(),
                                                        new BlockPos(commandContext.getSource().getPosition())
                                                )
                                ))),
                        false
                );
                return 0;
            }));
        }
        return command;
    }

}