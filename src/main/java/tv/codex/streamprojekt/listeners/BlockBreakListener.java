package tv.codex.streamprojekt.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import tv.codex.streamprojekt.StreamProjekt;
import tv.codex.streamprojekt.utils.GameState;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final StreamProjekt streamProjekt;

    private final HashMap<Material, Material> blockType;

    private final Material[] materials;

    private final Random random;

    public BlockBreakListener(StreamProjekt streamProjekt) {
        this.streamProjekt = streamProjekt;

        this.blockType = new HashMap<>();

        this.materials = Material.values().clone();

        this.random = new Random();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (this.streamProjekt.getGameState().equals(GameState.STARTING) || this.streamProjekt.getGameState().equals(GameState.ENDING)) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();

        if (this.blockType.containsKey(block.getType())) {
            event.setDropItems(false);
            Objects.requireNonNull(event.getBlock().getLocation().getWorld()).dropItemNaturally(event.getBlock().getLocation(), new ItemStack(this.blockType.get(block.getType())));
            return;
        }

        Material material;
        int number = random.nextInt(this.materials.length);

        material = this.materials[number];

        while (material == block.getType() || material == null || material == Material.AIR) {
            number = random.nextInt(this.materials.length);
            material = this.materials[number];
        }

        this.materials[number] = null;
        this.blockType.put(block.getType(), material);

        event.setDropItems(false);
        Objects.requireNonNull(event.getBlock().getLocation().getWorld()).dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));


    }

}
