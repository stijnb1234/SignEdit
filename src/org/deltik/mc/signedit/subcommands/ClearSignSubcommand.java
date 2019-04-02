package org.deltik.mc.signedit.subcommands;

import org.bukkit.entity.Player;
import org.deltik.mc.signedit.ArgParser;
import org.deltik.mc.signedit.Configuration;
import org.deltik.mc.signedit.SignText;
import org.deltik.mc.signedit.listeners.SignEditListener;

import javax.inject.Inject;

public class ClearSignSubcommand extends SetSignSubcommand {
    @Inject
    public ClearSignSubcommand(Configuration c, SignEditListener l, ArgParser args, Player p, SignText t) {
        super(c, l, args, p, t);
    }
}