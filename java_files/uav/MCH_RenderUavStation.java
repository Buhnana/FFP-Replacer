package mcheli.uav;

import mcheli.MCH_Lib;
import mcheli.MCH_ModelManager;
import mcheli.wrapper.W_Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderUavStation extends W_Render<MCH_EntityUavStation> {
  public static final IRenderFactory<MCH_EntityUavStation> FACTORY = MCH_RenderUavStation::new;
  
  public static final String[] MODEL_NAME = new String[] { "uav_station", "uav_portable_controller" };
  
  public static final String[] TEX_NAME_ON = new String[] { "uav_station_on", "uav_portable_controller_on" };
  
  public static final String[] TEX_NAME_OFF = new String[] { "uav_station", "uav_portable_controller" };
  
  public MCH_RenderUavStation(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 1.0F;
  }
  
  public void doRender(MCH_EntityUavStation entity, double posX, double posY, double posZ, float par8, float tickTime) {
    if (!(entity instanceof MCH_EntityUavStation))
      return; 
    MCH_EntityUavStation uavSt = entity;
    if (uavSt.getKind() <= 0)
      return; 
    int kind = uavSt.getKind() - 1;
    GL11.glPushMatrix();
    GL11.glTranslated(posX, posY + 0.3499999940395355D, posZ);
    GL11.glEnable(2884);
    GL11.glRotatef(entity.field_70177_z, 0.0F, -1.0F, 0.0F);
    GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    GL11.glEnable(3042);
    int srcBlend = GL11.glGetInteger(3041);
    int dstBlend = GL11.glGetInteger(3040);
    GL11.glBlendFunc(770, 771);
    if (kind == 0) {
      if (uavSt.getControlAircract() != null && uavSt.getRiddenByEntity() != null) {
        bindTexture("textures/" + TEX_NAME_ON[kind] + ".png");
      } else {
        bindTexture("textures/" + TEX_NAME_OFF[kind] + ".png");
      } 
      MCH_ModelManager.render(MODEL_NAME[kind]);
    } else {
      if (uavSt.rotCover > 0.95F) {
        bindTexture("textures/" + TEX_NAME_ON[kind] + ".png");
      } else {
        bindTexture("textures/" + TEX_NAME_OFF[kind] + ".png");
      } 
      renderPortableController(uavSt, MODEL_NAME[kind], tickTime);
    } 
    GL11.glBlendFunc(srcBlend, dstBlend);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public void renderPortableController(MCH_EntityUavStation uavSt, String name, float tickTime) {
    MCH_ModelManager.renderPart(name, "$body");
    float rot = MCH_Lib.smooth(uavSt.rotCover, uavSt.prevRotCover, tickTime);
    renderRotPart(name, "$cover", rot * 60.0F, 0.0D, -0.1812D, -0.3186D);
    renderRotPart(name, "$laptop_cover", rot * 95.0F, 0.0D, -0.1808D, -0.0422D);
    renderRotPart(name, "$display", rot * -85.0F, 0.0D, -0.1807D, 0.2294D);
  }
  
  private void renderRotPart(String modelName, String partName, float rot, double x, double y, double z) {
    GL11.glPushMatrix();
    GL11.glTranslated(x, y, z);
    GL11.glRotatef(rot, -1.0F, 0.0F, 0.0F);
    GL11.glTranslated(-x, -y, -z);
    MCH_ModelManager.renderPart(modelName, partName);
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityUavStation entity) {
    return TEX_DEFAULT;
  }
}
