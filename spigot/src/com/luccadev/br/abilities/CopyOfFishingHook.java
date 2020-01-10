package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import net.minecraft.server.v1_8_R3.EntityFishingHook;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

public class CopyOfFishingHook extends EntityFishingHook {
	private Snowball sb;
	private EntitySnowball controller;
	public int a;
	public EntityHuman owner;
	public Entity hooked;
	public boolean lastControllerDead;
	public boolean isHooked;

	public CopyOfFishingHook(final World world, final EntityHuman owner) {
		super((net.minecraft.server.v1_8_R3.World) ((CraftWorld) world).getHandle(), owner);
		this.owner = owner;
	}

	protected void c() {
	}

	public void h() {
		if (!this.lastControllerDead && this.controller.dead) {
			/*
			 * ((Player)this.owner.getBukkitEntity()).sendMessage(ChatColor.
			 * GREEN + "Seu gancho prendeu em algo!");
			 */
		}
		this.lastControllerDead = this.controller.dead;
		for (final Entity hooked : this.controller.world.getWorld().getEntities()) {
			if (!(hooked instanceof Firework) && hooked.getType() != EntityType.DROPPED_ITEM
					&& hooked.getType() != EntityType.FISHING_HOOK
					&& hooked.getEntityId() != this.getBukkitEntity().getEntityId()
					&& hooked.getEntityId() != this.owner.getBukkitEntity().getEntityId()
					&& hooked.getEntityId() != this.controller.getBukkitEntity().getEntityId()) {
				if (hooked.getLocation().distance(this.controller.getBukkitEntity().getLocation()) >= 2.0) {
					if (!(hooked instanceof Player)) {
						continue;
					}
					((Player) hooked).getEyeLocation().distance(this.controller.getBukkitEntity().getLocation());
				} else {
					this.controller.die();
					this.hooked = hooked;
					this.isHooked = true;
					this.locX = hooked.getLocation().getX();
					this.locY = hooked.getLocation().getY();
					this.locZ = hooked.getLocation().getZ();
					this.motX = 0.0;
					this.motY = 0.04;
					this.motZ = 0.0;
				}
			}
		}
		try {
			this.locX = this.hooked.getLocation().getX();
			this.locY = this.hooked.getLocation().getY();
			this.locZ = this.hooked.getLocation().getZ();
			this.motX = 0.0;
			this.motY = 0.04;
			this.motZ = 0.0;
			this.isHooked = true;
		} catch (Exception ex) {
			if (this.controller.dead) {
				this.isHooked = true;
			}
			this.locX = this.controller.locX;
			this.locY = this.controller.locY;
			this.locZ = this.controller.locZ;
		}
	}

	public void die() {
	}

	public void remove() {
		super.die();
	}

	@SuppressWarnings({ "rawtypes" })
	public void spawn(final Location location) {
		this.sb = (Snowball) this.owner.getBukkitEntity().launchProjectile(Snowball.class);
		this.controller = ((CraftSnowball) this.sb).getHandle();
		final PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(
				new int[] { this.controller.getId() });
		for (Player op : Bukkit.getOnlinePlayers()){
			((CraftPlayer) op).getHandle().playerConnection
			.sendPacket((Packet) packetPlayOutEntityDestroy);
		}
		((CraftWorld) location.getWorld()).getHandle().addEntity((net.minecraft.server.v1_8_R3.Entity) this);
	}

	public boolean isHooked() {
		return this.isHooked;
	}

	public void setHookedEntity(final Entity hooked) {
		this.hooked = hooked;
	}
}
