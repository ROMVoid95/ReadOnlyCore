package net.rom.libs.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.util.ResourceLocation;
import net.rom.libs.tablet.ITabletScreen;

public class TabletHelper {
	  public static Map<String, ResourceLocation> TABLET_CATAGORIES = new HashMap<>();
	  
	  public static Set<ITabletScreen> TABLET_PAGES = new TreeSet<>(new Comparator<ITabletScreen>() {
	        public int compare(ITabletScreen arg0, ITabletScreen arg1) {
	          return arg0.startScreen().compareTo(arg1.startScreen());
	        }
	      });
	  
	  public enum Tablet_Catagories {
	    GENERAL, SPACE, BLOCKS, ITEMS, MECHANICS;
	    
	    public String getName() {
	      return name().toLowerCase();
	    }
	  }
	  
	  public static void addTabletCategory(String name, ResourceLocation icon) {
		  TABLET_CATAGORIES.put(name, icon);
	  }
	  
	  public static void addTabletPage(ITabletScreen page) {
		  TABLET_PAGES.add(page);
	  }
}
