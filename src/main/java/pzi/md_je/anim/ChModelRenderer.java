package pzi.md_je.anim;

import java.util.Queue;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import pzi.md_je.rendering.BloomQueue;

public class ChModelRenderer extends ModelRenderer
{
    public float baseRotAngleX;
    public float baseRotAngleY;
    public float baseRotAngleZ;
    public float scale = 1f;
    public ChModelRenderer parent;
    public ChModelRenderer(AnimModelBase model, String name) 
    {  
        super(model, name);
        model.bones.add(this);
    }
    public ChModelRenderer(AnimModelBase model) 
    {  
        super(model, "-");
        model.bones.add(this);
    }
    @Override 
    public void addChild(ModelRenderer renderer)
    {
        super.addChild(renderer);
        if (renderer instanceof ChModelRenderer)
        {
            ((ChModelRenderer)renderer).parent = this;
        }
    }

    public void RotOffsetX(float angle)
    {
        this.rotateAngleX = baseRotAngleX + angle;
    }
    public void RotOffsetY(float angle)
    {
        this.rotateAngleX = baseRotAngleX + angle;
    }
    public void RotOffsetZ(float angle)
    {
        this.rotateAngleX = baseRotAngleX + angle;
    }
    @Override
    public void render(float sc)
    {
        super.render(sc * scale);
    }

    
}   
