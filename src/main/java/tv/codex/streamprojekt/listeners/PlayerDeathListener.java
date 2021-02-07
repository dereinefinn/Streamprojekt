package tv.codex.streamprojekt.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import tv.codex.streamprojekt.StreamProjekt;

import java.util.Objects;

public class PlayerDeathListener implements Listener {

    private final StreamProjekt streamProjekt;

    public PlayerDeathListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();

        event.setDeathMessage(null);

        switch (this.streamProjekt.getGameState()) {
            case ENDING:
            case STARTING:
                assert player != null;
                player.teleport(Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getSpawnLocation());
                break;
            case PROTECTION:
            case PREPARATION:
                assert player != null;
                player.teleport(Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getSpawnLocation());
                player.setGameMode(GameMode.SPECTATOR);

                this.streamProjekt.getServer().getOnlinePlayers().forEach(players -> {
                    if (!players.getGameMode().equals(GameMode.SPECTATOR)) {
                        players.hidePlayer(this.streamProjekt, player);
                    } else {
                        player.showPlayer(this.streamProjekt, players);
                    }
                });

                this.streamProjekt.getServer().broadcastMessage(StreamProjekt.PREFIX + "§7Der Spieler §9" + player.getName() + " §7ist gestorben!");
                this.streamProjekt.getPlayersAlive().remove(player);
                break;
            case BATTLE:
                assert player != null;
                player.teleport(Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getSpawnLocation());
                player.setGameMode(GameMode.SPECTATOR);

                this.streamProjekt.getServer().getOnlinePlayers().forEach(players -> {
                    if (!players.getGameMode().equals(GameMode.SPECTATOR)) {
                        players.hidePlayer(this.streamProjekt, player);
                    } else {
                        player.showPlayer(this.streamProjekt, players);
                    }
                });

                if (player.getKiller() != null) {
                    this.streamProjekt.getServer().broadcastMessage(StreamProjekt.PREFIX + "§7Der Spieler §9" + player.getName() + " §7wurde von §9" + player.getKiller().getName() + " §7getötet!");
                } else {
                    this.streamProjekt.getServer().broadcastMessage(StreamProjekt.PREFIX + "§7Der Spieler §9" + player.getName() + " §7ist gestorben!");
                }
                this.streamProjekt.getPlayersAlive().remove(player);
                this.streamProjekt.getGameManager().endGame();
                break;
        }
    }

}
