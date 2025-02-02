package mcheli.helicopter;

import javax.annotation.Nullable;
import mcheli.MCH_BaseInfo;
import mcheli.__helper.addon.AddonResourceLocation;
import mcheli.__helper.info.ContentRegistries;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_AircraftInfoManager;
import net.minecraft.item.Item;

public class MCH_HeliInfoManager extends MCH_AircraftInfoManager<MCH_HeliInfo> {
  private static final MCH_HeliInfoManager instance = new MCH_HeliInfoManager();
  
  public static MCH_HeliInfoManager getInstance() {
    return instance;
  }
  
  @Nullable
  public static MCH_HeliInfo get(String name) {
    return (MCH_HeliInfo)ContentRegistries.heli().get(name);
  }
  
  public MCH_HeliInfo newInfo(AddonResourceLocation name, String filepath) {
    return new MCH_HeliInfo(name, filepath);
  }
  
  @Nullable
  public static MCH_HeliInfo getFromItem(Item item) {
    return getInstance().getAcInfoFromItem(item);
  }
  
  @Nullable
  public MCH_HeliInfo getAcInfoFromItem(Item item) {
    return (MCH_HeliInfo)ContentRegistries.heli().findFirst(info -> (info.item == item));
  }
  
  protected boolean contains(String name) {
    return ContentRegistries.heli().contains(name);
  }
  
  protected int size() {
    return ContentRegistries.heli().size();
  }
}
