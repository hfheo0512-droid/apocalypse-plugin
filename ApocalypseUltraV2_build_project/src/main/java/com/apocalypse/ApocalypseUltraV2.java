
package com.apocalypse;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.*;

import java.util.*;

public class ApocalypseUltraV2 extends JavaPlugin implements Listener {

    Random r = new Random();

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this,this);
        startZombieSpawner();
        startHelicopterEvent();
        getLogger().info("Apocalypse Ultra V2 enabled");
    }

    /* ZOMBIE SPAWNER (simplified 20 types idea) */
    void startZombieSpawner(){
        Bukkit.getScheduler().runTaskTimer(this,()->{

            for(Player p : Bukkit.getOnlinePlayers()){

                for(int i=0;i<5;i++){

                    Location loc = p.getLocation().add(r.nextInt(30)-15,0,r.nextInt(30)-15);
                    Zombie z = (Zombie)p.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

                    double t = r.nextDouble();

                    if(t < 0.2){
                        z.setCustomName("Runner");
                        z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,999999,1));
                    }
                    else if(t < 0.4){
                        z.setCustomName("Tank");
                        z.setHealth(40);
                    }
                    else if(t < 0.6){
                        z.setCustomName("Toxic");
                        z.addPotionEffect(new PotionEffect(PotionEffectType.POISON,999999,1));
                    }
                    else if(t < 0.8){
                        z.setCustomName("Burning");
                        z.setFireTicks(999999);
                    }
                    else{
                        z.setCustomName("Boss Zombie");
                        z.setHealth(80);
                        z.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,999999,2));
                    }
                }

            }

        },200,200);
    }

    /* HELICOPTER SUPPLY EVENT */
    void startHelicopterEvent(){

        Bukkit.getScheduler().runTaskTimer(this,()->{

            Bukkit.broadcastMessage("§6Helicopter supply drop incoming!");

            for(Player p : Bukkit.getOnlinePlayers()){
                p.getWorld().dropItem(p.getLocation(), new org.bukkit.inventory.ItemStack(Material.GOLDEN_APPLE));
                p.getWorld().dropItem(p.getLocation(), new org.bukkit.inventory.ItemStack(Material.IRON_INGOT,5));
            }

        },24000,24000);
    }

    /* BOSS LOOT */

    @EventHandler
    public void onDeath(EntityDeathEvent e){

        if(e.getEntity() instanceof Zombie){

            if(r.nextDouble()<0.25)
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
                        new org.bukkit.inventory.ItemStack(Material.IRON_INGOT,2));

            if(r.nextDouble()<0.08)
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
                        new org.bukkit.inventory.ItemStack(Material.DIAMOND));
        }
    }
}
