package mcheli.weapon;

import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.wrapper.W_Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderTvMissile extends MCH_RenderBulletBase<MCH_EntityBaseBullet> {
  public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderTvMissile::new;
  
  public MCH_RenderTvMissile(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 0.5F;
  }
  
  public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float par8, float par9) {
    MCH_EntityAircraft ac = null;
    Entity ridingEntity = (Minecraft.func_71410_x()).field_71439_g.func_184187_bx();
    if (ridingEntity instanceof MCH_EntityAircraft) {
      ac = (MCH_EntityAircraft)ridingEntity;
    } else if (ridingEntity instanceof MCH_EntitySeat) {
      ac = ((MCH_EntitySeat)ridingEntity).getParent();
    } else if (ridingEntity instanceof MCH_EntityUavStation) {
      ac = ((MCH_EntityUavStation)ridingEntity).getControlAircract();
    } 
    if (ac != null && !ac.isRenderBullet((Entity)entity, (Entity)(Minecraft.func_71410_x()).field_71439_g))
      return; 
    if (entity instanceof MCH_EntityBaseBullet) {
      MCH_EntityBaseBullet bullet = entity;
      GL11.glPushMatrix();
      GL11.glTranslated(posX, posY, posZ);
      GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
      renderModel(bullet);
      GL11.glPopMatrix();
    } 
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
    return TEX_DEFAULT;
  }
}
