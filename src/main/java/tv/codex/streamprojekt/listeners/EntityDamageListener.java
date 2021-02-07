package tv.codex.streamprojekt.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tv.codex.streamprojekt.StreamProjekt;

public class EntityDamageListener implements Listener {

    private final StreamProjekt streamProjekt;

    public EntityDamageListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        switch (this.streamProjekt.getGameState()) {
            case PROTECTION:
            case STARTING:
            case ENDING:
                event.setCancelled(true);
                break;
        }
    }

}
