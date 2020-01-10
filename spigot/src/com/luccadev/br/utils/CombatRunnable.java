package com.luccadev.br.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatRunnable extends BukkitRunnable
{
    public void run() {
    }
    
    public void stop() {
    }
    
    public static class UtilRunnable{
    	
    	  private static HashMap<String, ArrayList<CombatRunnable>> task = new HashMap<String, ArrayList<CombatRunnable>>();
    	
    	  @SuppressWarnings({ "rawtypes", "unchecked" })
		public static void addTimer(Player p, CombatRunnable run)
    	  {
    	    if (!task.containsKey(p.getName())) {
    	      task.put(p.getName(), new ArrayList());
    	    }
    	    (task.get(p.getName())).add(run);
    	  }
    	  
    	  public static void removeTimer(Player p, CombatRunnable run)
    	  {
    	    if ((task.containsKey(p.getName())) && 
    	      ((task.get(p.getName())).contains(run)))
    	    {
    	      run.stop();
    	      (task.get(p.getName())).remove(run);
    	    }
    	  }
    }
}

