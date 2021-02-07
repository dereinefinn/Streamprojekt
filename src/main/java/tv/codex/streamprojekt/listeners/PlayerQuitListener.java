package tv.codex.streamprojekt.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tv.codex.streamprojekt.StreamProjekt;

public class PlayerQuitListener implements Listener {

    private final StreamProjekt streamProjekt;

    public PlayerQuitListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.streamProjekt.getPlayersAlive().contains(player)) {
            this.streamProjekt.getPlayersAlive().remove(player);
            event.setQuitMessage("§8[§c-§8] §7" + player.getDisplayName());
            return;
        }
        event.setQuitMessage(null);
    }
}
