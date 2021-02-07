package tv.codex.streamprojekt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tv.codex.streamprojekt.StreamProjekt;
import tv.codex.streamprojekt.utils.PlayTimer;

public class StartCommand implements CommandExecutor {

    private final StreamProjekt streamProjekt;

    public StartCommand(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!sender.isOp()) return true;

        if (args.length != 0) return true;

        sender.sendMessage(StreamProjekt.PREFIX + "§7Du hast das Spiel gestartet");

        new PlayTimer(10, this.streamProjekt, unused -> {
            this.streamProjekt.getGameManager().startGame();
        });

        return false;
    }
}
