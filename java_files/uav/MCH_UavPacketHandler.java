package mcheli.uav;

import com.google.common.io.ByteArrayDataInput;
import mcheli.__helper.network.HandleSide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_UavPacketHandler {
  @HandleSide({Side.SERVER})
  public static void onPacketUavStatus(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.field_70170_p.field_72995_K) {
      MCH_UavPacketStatus status = new MCH_UavPacketStatus();
      status.readData(data);
      scheduler.func_152344_a(() -> {
            if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
              ((MCH_EntityUavStation)player.func_184187_bx()).setUavPosition(status.posUavX, status.posUavY, status.posUavZ);
              if (status.continueControl)
                ((MCH_EntityUavStation)player.func_184187_bx()).controlLastAircraft((Entity)player); 
            } 
          });
    } 
  }
}
