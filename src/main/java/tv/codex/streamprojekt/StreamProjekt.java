package tv.codex.streamprojekt;

import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tv.codex.streamprojekt.commands.StartCommand;
import tv.codex.streamprojekt.listeners.*;
import tv.codex.streamprojekt.manager.GameManager;
import tv.codex.streamprojekt.utils.GameState;

import java.util.ArrayList;
import java.util.Objects;

public class StreamProjekt extends JavaPlugin {

    public final static String PREFIX = "§7[§bCODEX§7] ";

    private GameState gameState;

    private GameManager gameManager;
    private ArrayList<Player> playersAlive;

    @Override
    public void onEnable() {
        this.gameState = GameState.STARTING;
        this.gameManager = new GameManager(this);
        this.playersAlive = new ArrayList<>();

        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerSpawnLocationListener(this), this);

        Objects.requireNonNull(this.getServer().getPluginCommand("start")).setExecutor(new StartCommand(this));

        Objects.requireNonNull(this.getServer().getWorld("world")).getWorldBorder().setCenter(Objects.requireNonNull(this.getServer().getWorld("world")).getSpawnLocation());
        Objects.requireNonNull(this.getServer().getWorld("world")).getWorldBorder().setSize(15);

        Objects.requireNonNull(this.getServer().getWorld("world")).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        Objects.requireNonNull(this.getServer().getWorld("world_nether")).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

    }

    @Override
    public void onDisable() {

    }

    public GameState getGameState() {
        return gameState;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ArrayList<Player> getPlayersAlive() {
        return playersAlive;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
