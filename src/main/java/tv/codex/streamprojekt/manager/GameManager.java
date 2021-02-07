package tv.codex.streamprojekt.manager;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import tv.codex.streamprojekt.StreamProjekt;
import tv.codex.streamprojekt.utils.GameState;
import tv.codex.streamprojekt.utils.PlayTimer;

import java.util.Objects;

public class GameManager {

    private final StreamProjekt streamProjekt;

    public GameManager(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    public void startGame() {
        this.streamProjekt.setGameState(GameState.PREPARATION);
        this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> {
            Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getWorldBorder().reset();
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
            this.streamProjekt.getPlayersAlive().add(player);

            ItemStack ironPickaxe = new ItemStack(Material.IRON_PICKAXE);
            ItemMeta ironPickaxeMeta = ironPickaxe.getItemMeta();
            assert ironPickaxeMeta != null;
            ironPickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
            ironPickaxe.setItemMeta(ironPickaxeMeta);

            ItemStack ironAxe = new ItemStack(Material.IRON_AXE);
            ItemMeta ironAxeMeta = ironAxe.getItemMeta();
            assert ironAxeMeta != null;
            ironAxeMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
            ironAxe.setItemMeta(ironAxeMeta);

            ItemStack ironShovel = new ItemStack(Material.IRON_SHOVEL);
            ItemMeta ironShovelMeta = ironShovel.getItemMeta();
            assert ironShovelMeta != null;
            ironShovelMeta.addEnchant(Enchantment.DIG_SPEED, 2, true);
            ironShovel.setItemMeta(ironShovelMeta);

            player.getInventory().addItem(ironPickaxe, ironAxe, ironShovel);
        });

        this.streamProjekt.getServer().broadcastMessage(StreamProjekt.PREFIX + "§7Farmt euch eure Sachen zusammen! Der Kampf beginnt in §915 Minuten");

        new BukkitRunnable() {
            int running = 0;
            @Override
            public void run() {
                running ++;
                if (running == 15)
                    endPrepartion();
                if (running == 5 || running == 10 || running == 14) {
                    GameManager.this.streamProjekt.getServer().broadcastMessage(StreamProjekt.PREFIX + "§7Die Vorbereitungsphase endet in §9§l" + (15 - running) + " §7Minute(n)");
                }
            }
        }.runTaskTimerAsynchronously(this.streamProjekt, 60 * 20,60 * 20);
    }

    private void endPrepartion() {
        new PlayTimer(15, this.streamProjekt, unused -> startBattle());
    }

    private void startBattle() {
        this.streamProjekt.setGameState(GameState.PROTECTION);
        this.streamProjekt.getServer().getOnlinePlayers().forEach(player ->
                player.teleport(Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getSpawnLocation()));

        Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getWorldBorder().setCenter(Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getSpawnLocation());
        Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getWorldBorder().setSize(150);
        Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).getWorldBorder().setSize(10, 600);

        new PlayTimer(30, this.streamProjekt, unused -> {
            this.streamProjekt.setGameState(GameState.BATTLE);
            Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).setDifficulty(Difficulty.NORMAL);
            Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).setTime(1000);
            Objects.requireNonNull(this.streamProjekt.getServer().getWorld("world")).setStorm(false);
            this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> {
                player.sendTitle("§6Der Kampf", "§7beginnt jetzt!", 10, 40, 20);
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1,3);
            });
        });
    }

    public void endGame() {
        if (this.streamProjekt.getPlayersAlive().size() != 1) return;

        Player winner = this.streamProjekt.getPlayersAlive().get(0);

        this.streamProjekt.setGameState(GameState.ENDING);

        this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> {
            player.teleport(winner);
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 3);
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 0);
            player.sendTitle("§9" + winner.getName(), "§7hat gewonnen!", 10, 60, 30);
            player.showPlayer(this.streamProjekt, player);
        });
    }
}
