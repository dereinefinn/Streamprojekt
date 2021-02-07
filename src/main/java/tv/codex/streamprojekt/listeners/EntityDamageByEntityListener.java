package tv.codex.streamprojekt.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tv.codex.streamprojekt.StreamProjekt;

public class EntityDamageByEntityListener implements Listener {

    private final StreamProjekt streamProjekt;

    public EntityDamageByEntityListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {

        switch (this.streamProjekt.getGameState()) {
            case STARTING:
            case ENDING:
                event.setCancelled(true);
                break;
            case PREPARATION:
                if (event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
                    event.setCancelled(true);
                }
                break;
            case PROTECTION:
                if (event.getEntityType().equals(EntityType.PLAYER)) {
                    event.setCancelled(true);
                }
                break;
        }

    }

}
