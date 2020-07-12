package net.rom.command;

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
import net.rom.Ref;
import net.rom.debugdata.Dump;


/**
 * The Class CommandData.
 */
public class CommandData extends CommandBase {
    
    /**
     * The Enum SubCommand.
     */
    enum SubCommand implements IStringSerializable {
        
        /** The block. */
        BLOCK(Dump::dumpBlocks),
        
        /** The enchantment. */
        ENCHANTMENT(Dump::dumpEnchantments),
        
        /** The entity. */
        ENTITY(Dump::dumpEntityList),
        
        /** The item. */
        ITEM(Dump::dumpItems),
        
        /** The potion. */
        POTION(Dump::dumpPotionEffects);

        /** The command. */
        private final Runnable command;

        /**
         * Instantiates a new sub command.
         *
         * @param command the command
         */
        SubCommand(Runnable command) {
            this.command = command;
        }

        /**
         * Execute.
         */
        void execute() {
            this.command.run();
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        @Override
        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Override
	public String getName() {
		return Ref.MODID + "_data";
	}
	
    /**
     * Gets the aliases.
     *
     * @return the aliases
     */
    @Override
    public List<String> getAliases() {
        return ImmutableList.of("rcore_dump");
    }

    /**
     * Gets the usage.
     *
     * @param sender the sender
     * @return the usage
     */
    @Override
    public String getUsage(ICommandSender sender) {
        StringBuilder subcommands = new StringBuilder();
        for (SubCommand sub : SubCommand.values()) {
            if (subcommands.length() > 0) subcommands.append("|");
            subcommands.append(sub.getName());
        }
        return String.format("%sUsage: /%s <%s>", TextFormatting.RED, getName(), subcommands.toString());
    }

    /**
     * Execute.
     *
     * @param server the server
     * @param sender the sender
     * @param args the args
     */
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

    /**
     * Gets the sub command.
     *
     * @param arg the arg
     * @return the sub command
     */
    private Optional<SubCommand> getSubCommand(String arg) {
        for (SubCommand subCommand : SubCommand.values())
            if (subCommand.name().equalsIgnoreCase(arg))
                return Optional.of(subCommand);
        return Optional.empty();
    }

}
