package com.github.towardthestars.localspecialties.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;


public class EnvCommand extends BasicCommand
{
    @Override
    public String name()
    {
        return "localenv";
    }

    @Override
    LiteralArgumentBuilder<CommandSource> build(LiteralArgumentBuilder<CommandSource> command)
    {
//        for (Map.Entry<String, EnvAttribute> attr :
//                EnvAttributes.ENV_ATTRIBUTE_MAP.entrySet())
//        {
//            command = command.then(Commands.literal(attr.getKey()).executes((commandContext) -> {
//                commandContext.getSource().sendFeedback(
//                        new TranslatableText("localenv." + attr.getKey() + ".name")
//                                .append(String.format(": %f", (float)(attr.getValue().getAttribute
//                                                (
//                                                        commandContext.getSource().getWorld(),
//                                                        new BlockPos(commandContext.getSource().getPos())
//                                                )
//                                ))),
//                        false
//                );
//                return 0;
//            }));
//        }
        return command;
    }

}