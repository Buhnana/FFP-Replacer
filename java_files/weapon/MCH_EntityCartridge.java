package mcheli.weapon;

import mcheli.MCH_Config;
import mcheli.__helper.client._IModelCustom;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityCartridge extends W_Entity {
  public final String texture_name;
  
  public final _IModelCustom model;
  
  private final float bound;
  
  private final float gravity;
  
  private final float scale;
  
  private int countOnUpdate;
  
  public float targetYaw;
  
  public float targetPitch;
  
  @SideOnly(Side.CLIENT)
  public static void spawnCartridge(World world, MCH_Cartridge cartridge, double x, double y, double z, double mx, double my, double mz, float yaw, float pitch) {
    if (cartridge != null) {
      MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.field_73012_v.nextFloat() - 0.5D) * 0.07D, my, mz + (world.field_73012_v.nextFloat() - 0.5D) * 0.07D);
      entityFX.field_70126_B = yaw;
      entityFX.field_70177_z = yaw;
      entityFX.targetYaw = yaw;
      entityFX.field_70127_C = pitch;
      entityFX.field_70125_A = pitch;
      entityFX.targetPitch = pitch;
      float cy = yaw + cartridge.yaw;
      float cp = pitch + cartridge.pitch;
      double tX = (-MathHelper.func_76126_a(cy / 180.0F * 3.1415927F) * MathHelper.func_76134_b(cp / 180.0F * 3.1415927F));
      double tZ = (MathHelper.func_76134_b(cy / 180.0F * 3.1415927F) * MathHelper.func_76134_b(cp / 180.0F * 3.1415927F));
      double tY = -MathHelper.func_76126_a(cp / 180.0F * 3.1415927F);
      double d = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
      if (Math.abs(d) > 0.001D) {
        entityFX.field_70159_w += tX * cartridge.acceleration / d;
        entityFX.field_70181_x += tY * cartridge.acceleration / d;
        entityFX.field_70179_y += tZ * cartridge.acceleration / d;
      } 
      world.func_72838_d((Entity)entityFX);
    } 
  }
  
  public MCH_EntityCartridge(World par1World, MCH_Cartridge c, double x, double y, double z, double mx, double my, double mz) {
    super(par1World);
    func_70080_a(x, y, z, 0.0F, 0.0F);
    this.field_70159_w = mx;
    this.field_70181_x = my;
    this.field_70179_y = mz;
    this.texture_name = c.name;
    this.model = c.model;
    this.bound = c.bound;
    this.gravity = c.gravity;
    this.scale = c.scale;
    this.countOnUpdate = 0;
  }
  
  public float getScale() {
    return this.scale;
  }
  
  public void func_70071_h_() {
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    this.field_70126_B = this.field_70177_z;
    this.field_70127_C = this.field_70125_A;
    if (this.countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
      this.countOnUpdate++;
    } else {
      func_70106_y();
    } 
    this.field_70159_w *= 0.98D;
    this.field_70179_y *= 0.98D;
    this.field_70181_x += this.gravity;
    move();
  }
  
  public void rotation() {
    if (this.field_70177_z < this.targetYaw - 3.0F) {
      this.field_70177_z += 10.0F;
      if (this.field_70177_z > this.targetYaw)
        this.field_70177_z = this.targetYaw; 
    } else if (this.field_70177_z > this.targetYaw + 3.0F) {
      this.field_70177_z -= 10.0F;
      if (this.field_70177_z < this.targetYaw)
        this.field_70177_z = this.targetYaw; 
    } 
    if (this.field_70125_A < this.targetPitch) {
      this.field_70125_A += 10.0F;
      if (this.field_70125_A > this.targetPitch)
        this.field_70125_A = this.targetPitch; 
    } else if (this.field_70125_A > this.targetPitch) {
      this.field_70125_A -= 10.0F;
      if (this.field_70125_A < this.targetPitch)
        this.field_70125_A = this.targetPitch; 
    } 
  }
  
  public void move() {
    Vec3d vec1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    Vec3d vec2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
    RayTraceResult m = W_WorldFunc.clip(this.field_70170_p, vec1, vec2);
    double d = Math.max(Math.abs(this.field_70159_w), Math.abs(this.field_70181_x));
    d = Math.max(d, Math.abs(this.field_70179_y));
    if (W_MovingObjectPosition.isHitTypeTile(m)) {
      func_70107_b(m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
      this.field_70159_w += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
      this.field_70181_x += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
      this.field_70179_y += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
      if (d > 0.10000000149011612D) {
        this.targetYaw += (float)(d * (this.field_70146_Z.nextFloat() - 0.5F) * 720.0D);
        this.targetPitch = (float)(d * (this.field_70146_Z.nextFloat() - 0.5F) * 720.0D);
      } else {
        this.targetPitch = 0.0F;
      } 
      switch (m.field_178784_b) {
        case DOWN:
          if (this.field_70181_x > 0.0D)
            this.field_70181_x = -this.field_70181_x * this.bound; 
          break;
        case UP:
          if (this.field_70181_x < 0.0D)
            this.field_70181_x = -this.field_70181_x * this.bound; 
          this.targetPitch *= 0.3F;
          break;
        case NORTH:
          if (this.field_70179_y > 0.0D) {
            this.field_70179_y = -this.field_70179_y * this.bound;
            break;
          } 
          this.field_70161_v += this.field_70179_y;
          break;
        case SOUTH:
          if (this.field_70179_y < 0.0D) {
            this.field_70179_y = -this.field_70179_y * this.bound;
            break;
          } 
          this.field_70161_v += this.field_70179_y;
          break;
        case WEST:
          if (this.field_70159_w > 0.0D) {
            this.field_70159_w = -this.field_70159_w * this.bound;
            break;
          } 
          this.field_70165_t += this.field_70159_w;
          break;
        case EAST:
          if (this.field_70159_w < 0.0D) {
            this.field_70159_w = -this.field_70159_w * this.bound;
            break;
          } 
          this.field_70165_t += this.field_70159_w;
          break;
      } 
    } else {
      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
      if (d > 0.05000000074505806D)
        rotation(); 
    } 
  }
  
  protected void func_70037_a(NBTTagCompound var1) {}
  
  protected void func_70014_b(NBTTagCompound var1) {}
}
