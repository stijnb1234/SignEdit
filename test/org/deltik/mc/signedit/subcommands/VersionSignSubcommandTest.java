package org.deltik.mc.signedit.subcommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.deltik.mc.signedit.SignEditPlugin;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

public class VersionSignSubcommandTest {
    @Test
    public void signVersionShowsVersion() {
        String expected = "1.3.1";

        Player player = mock(Player.class);
        PluginDescriptionFile pluginDescriptionFile = mock(PluginDescriptionFile.class);
        SignEditPlugin plugin = mock(SignEditPlugin.class);
        when(plugin.getDescription()).thenReturn(pluginDescriptionFile);
        when(pluginDescriptionFile.getVersion()).thenReturn(expected);
        SignSubcommand subcommand = new VersionSignSubcommand(player, plugin);
        subcommand.execute();

        verify(player).sendMessage(contains(expected));
    }
}