package tv.codex.streamprojekt.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tv.codex.streamprojekt.StreamProjekt;

import java.util.Objects;

public class AsyncPlayerChatListener implements Listener {

    private final StreamProjekt streamProjekt;

    public AsyncPlayerChatListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            event.setCancelled(true);
            return;
        }

        event.setFormat(Objects.requireNonNull(player.getScoreboard().getEntryTeam(player.getName())).getColor() + player.getName() + "§7: §f" + event.getMessage().replace("%", "%%"));
    }
}
