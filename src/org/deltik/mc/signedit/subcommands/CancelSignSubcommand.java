package org.deltik.mc.signedit.subcommands;

import org.bukkit.entity.Player;
import org.deltik.mc.signedit.committers.SignEditCommit;
import org.deltik.mc.signedit.listeners.SignEditListener;

import javax.inject.Inject;

import static org.deltik.mc.signedit.SignEditPlugin.CHAT_PREFIX;

public class CancelSignSubcommand implements SignSubcommand {
    private final SignEditListener listener;
    private final Player player;

    @Inject
    public CancelSignSubcommand(SignEditListener listener, Player player) {
        this.listener = listener;
        this.player = player;
    }

    @Override
    public boolean execute() {
        SignEditCommit commit = listener.popSignEditCommit(player);
        if (commit != null) {
            player.sendMessage(CHAT_PREFIX + "§6Cancelled pending right-click action");
        } else {
            player.sendMessage(CHAT_PREFIX + "§cNo right-click action to cancel!");
        }
        return true;
    }
}
