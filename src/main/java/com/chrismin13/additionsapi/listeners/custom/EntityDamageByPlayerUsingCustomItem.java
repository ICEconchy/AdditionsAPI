package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.events.item.EntityDamageByPlayerUsingCustomItemEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.permissions.ItemPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

public class EntityDamageByPlayerUsingCustomItem implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByCustomItem(EntityDamageByPlayerUsingCustomItemEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		CustomItem cItem = event.getCustomItem();
		ItemStack item = event.getCustomItemStack().getItemStack();
		PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item,
				cItem.getDurabilityMechanics().getEntityHit(), cItem);
		if (damageEvent.getDamage() != 0)
			Bukkit.getServer().getPluginManager().callEvent(damageEvent);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageByCustomItemLowest(EntityDamageByPlayerUsingCustomItemEvent customEvent) {
		if (customEvent.isCancelled())
			return;

		CustomItem cItem = customEvent.getCustomItem();
		ItemPermissions perm = cItem.getPermissions();

		if (!PermissionUtils.allowedAction(customEvent.getPlayer(), perm.getType(), perm.getHit()))
			customEvent.setCancelled(true);
	}

}
