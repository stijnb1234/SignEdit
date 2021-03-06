/*
 * Copyright (C) 2017-2020 Deltik <https://www.deltik.org/>
 *
 * This file is part of SignEdit for Bukkit.
 *
 * SignEdit for Bukkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SignEdit for Bukkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SignEdit for Bukkit.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.deltik.mc.signedit.interactions;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.deltik.mc.signedit.*;

import javax.inject.Provider;
import java.util.Arrays;

import static org.deltik.mc.signedit.ArgParser.ALL_LINES_SELECTED;
import static org.deltik.mc.signedit.ArgParser.NO_LINES_SELECTED;

public class CutSignEditInteraction implements SignEditInteraction {
    private final ArgParser argParser;
    private final Provider<SignText> signTextProvider;
    private final SignTextClipboardManager clipboardManager;
    private final SignTextHistoryManager historyManager;
    private final ChatComms comms;

    public CutSignEditInteraction(
            ArgParser argParser,
            Provider<SignText> signTextProvider,
            SignTextClipboardManager clipboardManager,
            SignTextHistoryManager historyManager,
            ChatComms comms) {
        this.argParser = argParser;
        this.signTextProvider = signTextProvider;
        this.clipboardManager = clipboardManager;
        this.historyManager = historyManager;
        this.comms = comms;
    }

    @Override
    public void interact(Player player, Sign sign) {
        int[] selectedLines = argParser.getSelectedLines();
        if (Arrays.equals(selectedLines, NO_LINES_SELECTED)) {
            selectedLines = ALL_LINES_SELECTED;
        }

        SignText clipboard = signTextProvider.get();
        SignText sourceSign = signTextProvider.get();
        sourceSign.setTargetSign(sign);

        for (int selectedLine : selectedLines) {
            clipboard.setLineLiteral(selectedLine, sign.getLine(selectedLine));
            sourceSign.setLineLiteral(selectedLine, "");
        }

        sourceSign.applySign();
        if (sourceSign.signChanged()) {
            historyManager.getHistory(player).push(sourceSign);
        }
        clipboardManager.setClipboard(player, clipboard);

        comms.tellPlayer(comms.t("lines_cut_section"));
        comms.dumpLines(clipboard.getLines());
    }

    @Override
    public String getName() {
        return "cut_sign_text";
    }
}
