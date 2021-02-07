package tv.codex.streamprojekt.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import tv.codex.streamprojekt.StreamProjekt;

import java.util.Objects;

public class PlayerSpawnLocationListener implements Listener {

    private final StreamProjekt streamProjekt;

    public PlayerSpawnLocationListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onPlayerJoin(PlayerSpawnLocationEvent event) {

        Player player = event.getPlayer();

        event.setSpawnLocation(Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getSpawnLocation());

    }
}
