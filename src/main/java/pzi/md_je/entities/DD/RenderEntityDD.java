package pzi.md_je.entities.DD;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import pzi.md_je.entities.EntityMD;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.rendering.BloomQueue;

public class RenderEntityDD extends RenderLiving<EntityMD> 
{
    public RenderEntityDD(RenderManager renderManager)
    {
        super(renderManager, new ModelEntityDD(), 1f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMD entity) 
    {
        if (BloomQueue.cPass == BloomQueue.Pass.Normal)
            return ResourcesHelper.GetTextureLocation("entities/dd.png");
        else
            return ResourcesHelper.GetTextureLocation("entities/dd_bloom.png");
    }    

    @Override
    public void doRender(EntityMD entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        if (BloomQueue.cPass == BloomQueue.Pass.Normal)
        {
            BloomQueue.SubmitBloomTicket(this, entity, x, y, z, entityYaw, partialTicks);
        }
    }
    
}
