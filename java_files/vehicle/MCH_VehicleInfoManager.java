package mcheli.vehicle;

import javax.annotation.Nullable;
import mcheli.MCH_BaseInfo;
import mcheli.__helper.addon.AddonResourceLocation;
import mcheli.__helper.info.ContentRegistries;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_AircraftInfoManager;
import net.minecraft.item.Item;

public class MCH_VehicleInfoManager extends MCH_AircraftInfoManager<MCH_VehicleInfo> {
  private static MCH_VehicleInfoManager instance = new MCH_VehicleInfoManager();
  
  @Nullable
  public static MCH_VehicleInfo get(String name) {
    return (MCH_VehicleInfo)ContentRegistries.vehicle().get(name);
  }
  
  public static MCH_VehicleInfoManager getInstance() {
    return instance;
  }
  
  public MCH_VehicleInfo newInfo(AddonResourceLocation name, String filepath) {
    return new MCH_VehicleInfo(name, filepath);
  }
  
  @Nullable
  public static MCH_VehicleInfo getFromItem(Item item) {
    return getInstance().getAcInfoFromItem(item);
  }
  
  @Nullable
  public MCH_VehicleInfo getAcInfoFromItem(Item item) {
    return (MCH_VehicleInfo)ContentRegistries.vehicle().findFirst(info -> (info.item == item));
  }
  
  protected boolean contains(String name) {
    return ContentRegistries.vehicle().contains(name);
  }
  
  protected int size() {
    return ContentRegistries.vehicle().size();
  }
}
