package net.rom.readonlycore.command;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.rom.readonlycore.Ref;
import net.rom.readonlycore.debugdata.Dump;

public class CommandData extends CommandBase {
    enum SubCommand implements IStringSerializable {
        BLOCK(Dump::dumpBlocks),
        ENCHANTMENT(Dump::dumpEnchantments),
        ENTITY(Dump::dumpEntityList),
        ITEM(Dump::dumpItems),
        POTION(Dump::dumpPotionEffects);

        private final Runnable command;

        SubCommand(Runnable command) {
            this.command = command;
        }

        void execute() {
            this.command.run();
        }

        @Override
        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
	@Override
	public String getName() {
		return Ref.MODID + "_data";
	}
	
    @Override
    public List<String> getAliases() {
        return ImmutableList.of("rcore_dump");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        StringBuilder subcommands = new StringBuilder();
        for (SubCommand sub : SubCommand.values()) {
            if (subcommands.length() > 0) subcommands.append("|");
            subcommands.append(sub.getName());
        }
        return String.format("%sUsage: /%s <%s>", TextFormatting.RED, getName(), subcommands.toString());
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        getSubCommand(args[0]).ifPresent(subCommand -> {
            subCommand.execute();
            String line = String.format("Printed %s list to log", subCommand.getName());
            sender.sendMessage(new TextComponentString(line));
        });
    }

    private Optional<SubCommand> getSubCommand(String arg) {
        for (SubCommand subCommand : SubCommand.values())
            if (subCommand.name().equalsIgnoreCase(arg))
                return Optional.of(subCommand);
        return Optional.empty();
    }

}
