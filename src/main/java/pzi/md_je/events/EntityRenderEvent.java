package pzi.md_je.events;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pzi.md_je.MD;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.entities.EntityMD;
import pzi.md_je.rendering.BloomQueue;

@EventBusSubscriber (modid = MD.MODID, value = Side.CLIENT)
public class EntityRenderEvent 
{
    @SideOnly (Side.CLIENT)
    @SubscribeEvent 
    public static void preRender(RenderLivingEvent.Pre<EntityMD> event)
    {
        if (BloomQueue.cPass == BloomQueue.Pass.Normal && event.getEntity() instanceof EntityMD)
        {
            ((EntityMD)event.getEntity()).animator.ProgressAnims(event.getEntity().ticksExisted + event.getPartialRenderTick());
        }
    }
}
