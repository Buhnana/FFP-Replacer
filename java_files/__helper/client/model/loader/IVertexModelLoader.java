package mcheli.__helper.client.model.loader;

import java.io.IOException;
import javax.annotation.Nullable;
import mcheli.__helper.client._IModelCustom;
import mcheli.__helper.client._ModelFormatException;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IVertexModelLoader {
  String getExtension();
  
  @Nullable
  _IModelCustom load(IResourceManager paramIResourceManager, ResourceLocation paramResourceLocation) throws IOException, _ModelFormatException;
  
  default ResourceLocation withExtension(ResourceLocation location) {
    return new ResourceLocation(location.func_110624_b(), location.func_110623_a() + "." + getExtension());
  }
}
