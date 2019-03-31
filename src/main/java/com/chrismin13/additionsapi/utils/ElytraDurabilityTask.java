package com.chrismin13.additionsapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.chrismin13.additionsapi.durability.ElytraDurability;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.listeners.custom.CustomElytraPlayerToggleGlide;

public class ElytraDurabilityTask extends BukkitRunnable {

	private Player player;
	private ItemStack elytra;
	private CustomItem cItem;
	private boolean justStarted = true;

	public ElytraDurabilityTask(Player player, ItemStack elytra, CustomItem cItem) {
		this.player = player;
		this.cItem = cItem;
		this.elytra = elytra;
	}

	@Override
	public void run() {
		if (justStarted) {
			justStarted = false;
			return;
		}
		if (player.isGliding() && cItem.getDurabilityMechanics() instanceof ElytraDurability) {
			Bukkit.getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, elytra,
					((ElytraDurability) cItem.getDurabilityMechanics()).getFlightOneSecond(), cItem));
		} else {
			CustomElytraPlayerToggleGlide.cancelPlayerGlideDamage(player.getUniqueId());
		}
	}

}
