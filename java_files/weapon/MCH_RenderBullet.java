package mcheli.weapon;

import mcheli.wrapper.W_Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderBullet extends MCH_RenderBulletBase<MCH_EntityBaseBullet> {
  public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderBullet::new;
  
  protected MCH_RenderBullet(RenderManager renderManager) {
    super(renderManager);
  }
  
  public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float yaw, float tickTime) {
    MCH_EntityBaseBullet blt = entity;
    GL11.glPushMatrix();
    GL11.glTranslated(posX, posY, posZ);
    GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
    renderModel(blt);
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
    return TEX_DEFAULT;
  }
}
