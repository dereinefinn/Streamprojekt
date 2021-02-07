package tv.codex.streamprojekt.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;
import tv.codex.streamprojekt.StreamProjekt;

import java.util.function.Consumer;

public class PlayTimer implements Runnable {

    private final StreamProjekt streamProjekt;

    private final int seconds;

    private final BukkitTask task;

    private final Consumer<Void> consumer;

    private int running;

    public PlayTimer(int seconds, StreamProjekt streamProjekt, Consumer<Void> consumer) {
        this.streamProjekt = streamProjekt;
        this.seconds = seconds;
        this.consumer = consumer;
        this.running = 0;

        this.task = this.streamProjekt.getServer().getScheduler().runTaskTimer(this.streamProjekt, this, 0, 20L);
    }

    @Override
    public void run() {

        if (this.seconds <= this.running) {
            this.task.cancel();
            this.consumer.accept(null);
            return;
        }

        switch (this.streamProjekt.getGameState()) {
            case STARTING:
                this.streamProjekt.getServer().broadcastMessage(StreamProjekt.PREFIX + "§7Das Spiel startet in §9§l" + (seconds - this.running) + " §7Sekunde(n)");
                this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 3));
                break;
            case PREPARATION:
                this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§7Die Vorbereitungsphase endet in §9§l" + (seconds - this.running) + " §7Sekunde(n)")));
                this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 3));
                break;
            case PROTECTION:
                this.streamProjekt.getServer().getOnlinePlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§7Die Schutzzeit endet in §9§l" + (seconds - this.running) + " §7Sekunde(n)")));
                break;
        }

        this.running++;

    }
}
