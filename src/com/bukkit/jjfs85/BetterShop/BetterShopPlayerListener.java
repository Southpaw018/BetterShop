package com.bukkit.jjfs85.BetterShop;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class BetterShopPlayerListener extends PlayerListener {
	public static BetterShop plugin;
	private static final String prefix = "b";

	public BetterShopPlayerListener(BetterShop instance) {
		plugin = instance;
	}

	@Override
	public void onPlayerCommand(PlayerChatEvent event) {
		int i = 0, a = 0, b = 0, s = 0;
		// Make the message a string.
		String[] split = event.getMessage().split(" ");
		// Get the player that talked.
		Player player = event.getPlayer();
		// Accept shop command, parse arguments, check values
		if (split[0].equalsIgnoreCase("/" + prefix + "shop")) {
			if (split.length > 1) {
				if (split[1].equalsIgnoreCase("list")) {
					if (split.length == 3) {
						try {
							// A 3rd param? Is it a page number?
							i = this.stringToInt(split[2]);
						} catch (Exception e) {
							// Pass page 1
							i = 1;
						}
						plugin.list(player, i);
					} else {
						plugin.list(player, 1);
					}
				} else {
					// Now we expect the 3rd param to be an item.
					if (split.length > 2) {
						try {
							// Try the items.db
							i = itemDb.get(split[2]);
						} catch (Exception doh2) {
							// Not an item. It's fucked.
							player
									.sendMessage("§c[§7SHOP§c] I don't know what §f"
											+ split[2]
											+ "§c is. Maybe try using the ID #.");
						}
					}
					if (split[1].equalsIgnoreCase("buy")) {
						if (split.length == 4) {
							try {
								// The 4th param is an amount
								a = this.stringToInt(split[3]);
							} catch (Exception e) {
								plugin.help(player);
							}
							plugin.buy(player, i, a);
						} else
							player.sendMessage("help1");
					}
					if (split[1].equalsIgnoreCase("sell")) {
						if (split.length == 4) {
							try {
								// The 4th param is an amount
								a = this.stringToInt(split[3]);
							} catch (Exception e) {
								player.sendMessage("help2");
							}
							plugin.sell(player, i, a);
						} else
							player.sendMessage("help3");
					}
					if (split[1].equalsIgnoreCase("add")) {
						if (split.length == 5) {
							try {
								// The 4th param is an amount
								b = this.stringToInt(split[3]);
							} catch (Exception e) {
								player.sendMessage("help4");
							}
							try {
								// The 5th param is an amount
								s = this.stringToInt(split[4]);
							} catch (Exception e) {
								player.sendMessage("help5");
							}
							plugin.add(player, i, b, s);
						} else
							player.sendMessage("help6");
					}
					if (split[1].equalsIgnoreCase("remove")) {
						if (split.length == 4)
							plugin.remove(player, i);
						else
							player.sendMessage("help7");
					}
					if (split[1].equalsIgnoreCase("update")) {
						if (split.length == 5)
							plugin.update(player, i, b, s);
						else
							player.sendMessage("help8");
					}
				}
			} else
				player.sendMessage("help10");
			event.setCancelled(true);
		}
	}

	private int stringToInt(String s) throws Exception {
		int i;
		try {
			i = Integer.parseInt(s);
			if ((i < 1) || (i > 2258))
				throw new Exception();
		} catch (NumberFormatException nfe) {

			throw new Exception();
		}
		return i;
	}
}
