package se.jkrau.wat;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Plugin extends JavaPlugin implements Listener {

	public void onEnable() {
		System.out.println("If you ever do this.....");
		System.out.println("Then you'll be like WAT.");
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	// This is necessary to calculate higher bounciness.
	@EventHandler
	public void onFallDamage(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL && e.getEntity() instanceof Player) {
			Player pl = (Player) e.getEntity();
			if (pl.getLocation().getY() > 80) {
				if (pl.getLocation().getBlock().getRelative(BlockFace.DOWN).getTypeId() == 80) {
					if (!pl.getLocation().getBlock().getRelative(BlockFace.DOWN).hasMetadata("wat_playerPlaced")) {
						pl.setVelocity(new Vector(0, e.getDamage() * 2, 0));
						e.setDamage(0); // Incase plugins unset the cancel value (stupid developers check).
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	// Block Place
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getBlock().getTypeId() == 80) {
			e.getBlock().setMetadata("wat_playerPlaced", new FixedMetadataValue(this, true));
		}
	}
	
	// Move!
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.getTo().getX() < e.getFrom().getY() && e.getTo().getBlock().getRelative(BlockFace.DOWN).getTypeId() == 80) {
			if (!e.getTo().getBlock().getRelative(BlockFace.DOWN).hasMetadata("wat_playerPlaced")) {
				e.getPlayer().setVelocity(new Vector(0, 2, 0));
			}
		}
	}
}
