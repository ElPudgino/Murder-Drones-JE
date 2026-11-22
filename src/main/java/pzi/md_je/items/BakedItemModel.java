package pzi.md_je.items;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.TRSRTransformation;
import pzi.md_je.MD;

import org.apache.commons.lang3.tuple.Pair;
import javax.vecmath.Matrix4f;
import java.util.List;

public class BakedItemModel implements IBakedModel {

    private final IBakedModel baseModel; 

    public BakedItemModel(IBakedModel baseModel) 
    {
        this.baseModel = baseModel;
    }

    @Override
    public List<BakedQuad> getQuads(
            net.minecraft.block.state.IBlockState state,
            EnumFacing side,
            long rand) {
        return java.util.Collections.emptyList();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() 
    {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return baseModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() 
    {
        return ItemOverrideList.NONE;
    }
}