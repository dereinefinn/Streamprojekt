package tv.codex.streamprojekt.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tv.codex.streamprojekt.StreamProjekt;
import tv.codex.streamprojekt.utils.GameState;

public class PlayerJoinListener implements Listener {

    private final StreamProjekt streamProjekt;

    public PlayerJoinListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendTitle("§fStreamprojekt", "§bpowered by Cooodex", 10, 40, 20);

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 5);

        if (!this.streamProjekt.getGameState().equals(GameState.STARTING)) {
            player.setGameMode(GameMode.SPECTATOR);
            this.streamProjekt.getServer().getOnlinePlayers().forEach(players -> {
                if (!players.getGameMode().equals(GameMode.SPECTATOR)) {
                    players.hidePlayer(this.streamProjekt, player);
                }
            });
            event.setJoinMessage(null);
            return;
        }
        event.setJoinMessage("§8[§a+§8] §7" + player.getDisplayName());
    }
}
