package mcheli.aircraft;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.__helper.MCH_CriteriaTriggers;
import mcheli.wrapper.W_EntityPlayer;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class MCH_ItemAircraft extends W_Item {
  public MCH_ItemAircraft(int i) {
    super(i);
  }
  
  private static boolean isRegistedDispenseBehavior = false;
  
  public static void registerDispenseBehavior(Item item) {
    if (isRegistedDispenseBehavior == true)
      return; 
    BlockDispenser.field_149943_a.func_82595_a(item, new MCH_ItemAircraftDispenseBehavior());
  }
  
  @Nullable
  public abstract MCH_AircraftInfo getAircraftInfo();
  
  @Nullable
  public abstract MCH_EntityAircraft createAircraft(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, ItemStack paramItemStack);
  
  public MCH_EntityAircraft onTileClick(ItemStack itemStack, World world, float rotationYaw, int x, int y, int z) {
    MCH_EntityAircraft ac = createAircraft(world, (x + 0.5F), (y + 1.0F), (z + 0.5F), itemStack);
    if (ac == null)
      return null; 
    ac.initRotationYaw((((MathHelper.func_76128_c((rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90));
    if (!world.func_184144_a((Entity)ac, ac.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty())
      return null; 
    return ac;
  }
  
  public String toString() {
    MCH_AircraftInfo info = getAircraftInfo();
    if (info != null)
      return super.toString() + "(" + info.getDirectoryName() + ":" + info.name + ")"; 
    return super.toString() + "(null)";
  }
  
  public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
    ItemStack itemstack = player.func_184586_b(handIn);
    float f = 1.0F;
    float f1 = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
    float f2 = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
    double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
    double d1 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + 1.62D;
    double d2 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
    Vec3d vec3 = W_WorldFunc.getWorldVec3(world, d0, d1, d2);
    float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
    float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
    RayTraceResult mop = W_WorldFunc.clip(world, vec3, vec31, true);
    if (mop == null)
      return ActionResult.newResult(EnumActionResult.PASS, itemstack); 
    Vec3d vec32 = player.func_70676_i(f);
    boolean flag = false;
    float f9 = 1.0F;
    List<Entity> list = world.func_72839_b((Entity)player, player
        .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity.func_70067_L()) {
        float f10 = entity.func_70111_Y();
        AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
        if (axisalignedbb.func_72318_a(vec3))
          flag = true; 
      } 
    } 
    if (flag)
      return ActionResult.newResult(EnumActionResult.PASS, itemstack); 
    if (W_MovingObjectPosition.isHitTypeTile(mop)) {
      if (MCH_Config.PlaceableOnSpongeOnly.prmBool) {
        MCH_AircraftInfo acInfo = getAircraftInfo();
        if (acInfo != null && acInfo.isFloat && !acInfo.canMoveOnGround) {
          if (world.func_180495_p(mop.func_178782_a().func_177977_b()).func_177230_c() != Blocks.field_150360_v)
            return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
          for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
              if (world.func_180495_p(mop.func_178782_a().func_177982_a(x, 0, z)).func_177230_c() != Blocks.field_150355_j)
                return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
            } 
          } 
        } else {
          Block block = world.func_180495_p(mop.func_178782_a()).func_177230_c();
          if (!(block instanceof net.minecraft.block.BlockSponge))
            return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
        } 
      } 
      spawnAircraft(itemstack, world, player, mop.func_178782_a());
    } 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
  
  public MCH_EntityAircraft spawnAircraft(ItemStack itemStack, World world, EntityPlayer player, BlockPos blockpos) {
    MCH_EntityAircraft ac = onTileClick(itemStack, world, player.field_70177_z, blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos
        .func_177952_p());
    if (ac != null) {
      if (ac.getAcInfo() != null && (ac.getAcInfo()).creativeOnly && !player.field_71075_bZ.field_75098_d)
        return null; 
      if (ac.isUAV()) {
        if (world.field_72995_K)
          if (ac.isSmallUAV()) {
            W_EntityPlayer.addChatMessage(player, "Please use the UAV station OR Portable Controller");
          } else {
            W_EntityPlayer.addChatMessage(player, "Please use the UAV station");
          }  
        ac = null;
      } else {
        if (!world.field_72995_K) {
          ac.getAcDataFromItem(itemStack);
          world.func_72838_d((Entity)ac);
          MCH_CriteriaTriggers.PUT_AIRCRAFT.trigger((EntityPlayerMP)player);
        } 
        if (!player.field_71075_bZ.field_75098_d)
          itemStack.func_190918_g(1); 
      } 
    } 
    return ac;
  }
  
  public void rideEntity(ItemStack item, Entity target, EntityPlayer player) {
    if (!MCH_Config.PlaceableOnSpongeOnly.prmBool)
      if (target instanceof net.minecraft.entity.item.EntityMinecartEmpty && target.func_184188_bt().isEmpty()) {
        BlockPos blockpos = new BlockPos((int)target.field_70165_t, (int)target.field_70163_u + 2, (int)target.field_70161_v);
        MCH_EntityAircraft ac = spawnAircraft(item, player.field_70170_p, player, blockpos);
        if (!player.field_70170_p.field_72995_K && ac != null)
          ac.func_184220_m(target); 
      }  
  }
}
