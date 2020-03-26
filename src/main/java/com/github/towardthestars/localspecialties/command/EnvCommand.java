package com.github.towardthestars.localspecialties.command;

import com.github.towardthestars.localspecialties.Registries;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

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
        command.then(CommandManager.literal("list").executes((context -> {
            for (Identifier identifier :
                    Registries.ENV_ATTRIBUTE.getIds())
            {
                context.getSource().sendFeedback(
                        new TranslatableText(Objects.requireNonNull(Registries.ENV_ATTRIBUTE.get(identifier)).getTranslateKey())
                                .append(String.format(": %s",
                                        (Objects.requireNonNull(Registries.ENV_ATTRIBUTE.get(identifier)).getAttribute
                                        (
                                                context.getSource().getWorld(),
                                                new BlockPos(context.getSource().getPosition())
                                        )
                                ))),
                        false
                );
            }

            return 0;
        })));
        for (Identifier identifier :
                Registries.ENV_ATTRIBUTE.getIds())
        {
            command = command.then(CommandManager.literal(identifier.toString()).executes((commandContext) -> {
                commandContext.getSource().sendFeedback(
                        new TranslatableText("localenv." + identifier.getPath() + ".name")
                                .append(String.format(": %f", (float)(Registries.ENV_ATTRIBUTE.get(identifier).getAttribute
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