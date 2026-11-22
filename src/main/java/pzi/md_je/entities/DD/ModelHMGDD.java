package pzi.md_je.entities.DD;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.anim.ChModelRenderer;
import pzi.md_je.loading.ResourcesHelper;

public class ModelHMGDD extends AnimModelBase  
{
	public static HMGModelRenderer HMG;
	private final ChModelRenderer cube_r1;
	private final ChModelRenderer cube_r2;
	private final ChModelRenderer cube_r3;
	private final ChModelRenderer cube_r4;
	private final ChModelRenderer cube_r5;
	private final ChModelRenderer cube_r6;
	private final ChModelRenderer cube_r7;
	private final ChModelRenderer cube_r8;
	private final ChModelRenderer cube_r9;
	private final ChModelRenderer cube_r10;
	private final ChModelRenderer cube_r11;
	private final ChModelRenderer cube_r12;
	private final ChModelRenderer cube_r13;
	private final ChModelRenderer cube_r14;
	private final ChModelRenderer cube_r15;
	private final ChModelRenderer cube_r16;
	private final ChModelRenderer cube_r17;
	private final ChModelRenderer cube_r18;
	private final ChModelRenderer cube_r19;
	private final ChModelRenderer cube_r20;
	private final ChModelRenderer cube_r21;
	private final ChModelRenderer cube_r22;
	private final ChModelRenderer cube_r23;
	private final ChModelRenderer cube_r24;
	private final ChModelRenderer cube_r25;
	private final ChModelRenderer cube_r26;
	private final ChModelRenderer cube_r27;
	private final ChModelRenderer cube_r28;
	private final ChModelRenderer cube_r29;
	private final ChModelRenderer cube_r30;
	private final ChModelRenderer cube_r31;
	private final ChModelRenderer cube_r32;
	private final ChModelRenderer cube_r33;
	private final ChModelRenderer cube_r34;
	private final ChModelRenderer cube_r35;
	private final ChModelRenderer cube_r36;
	private final ChModelRenderer cube_r37;
	private final ChModelRenderer cube_r38;
	public static MuzzleFlashRenderer muzzle_flash;
	private final ChModelRenderer cube_r39;

	public ModelHMGDD() 
	{
		textureWidth = 64;
		textureHeight = 64;

		HMG = new HMGModelRenderer(this);
		HMG.scale = 1/1.4f;
		HMG.texture = ResourcesHelper.GetTextureLocation("entities/hmg.png");
		HMG.parentTexture = ResourcesHelper.GetTextureLocation("entities/dd.png");
		HMG.bloomTexture = ResourcesHelper.GetTextureLocation("entities/hmg_bloom.png");
		HMG.parentBloomTexture = ResourcesHelper.GetTextureLocation("entities/dd_bloom.png");
		HMG.heatGlowTexture = ResourcesHelper.GetTextureLocation("entities/hmg_heat_glow.png");
		HMG.heatGlow=1f;
		HMG.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(HMG, 0.0F, 0.0F, -(float)Math.PI/2f);
		HMG.cubeList.add(new ModelBox(HMG, 28, 40, -1.4F, -2.25F, 8.75F, 2, 1, 4, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 0, 33, -0.25F, -3.25F, 12.75F, 2, 1, 5, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 14, 35, -1.75F, -3.25F, 12.75F, 2, 1, 5, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 40, 40, -0.6F, -2.25F, 8.75F, 2, 1, 4, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 14, 41, -1.4F, -2.25F, 17.15F, 2, 1, 4, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 42, 12, -0.6F, -2.25F, 17.15F, 2, 1, 4, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 42, 36, -2.0F, 1.0F, -0.25F, 4, 2, 1, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 28, 29, -1.0F, -3.4F, 1.25F, 2, 1, 5, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 28, 35, -1.5F, -3.25F, 8.75F, 3, 1, 4, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 0, 39, -1.5F, -3.25F, 17.75F, 3, 1, 4, 0.0F, false));
		HMG.cubeList.add(new ModelBox(HMG, 39, 6, -0.5F, 0.9F, 22.8F, 1, 1, 1, 0.0F, false));

		cube_r1 = new ChModelRenderer(this);
		cube_r1.setRotationPoint(2.5F, 1.0F, 4.75F);
		HMG.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, 0.0873F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 7, 46, -2.0F, 0.0F, -4.0F, 2, 6, 5, 0.0F, true));

		cube_r2 = new ChModelRenderer(this);
		cube_r2.setRotationPoint(2.5F, 1.0F, 4.75F);
		HMG.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -0.4363F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 49, 1, -1.0F, -3.0F, -4.0F, 1, 3, 5, 0.0F, true));

		cube_r3 = new ChModelRenderer(this);
		cube_r3.setRotationPoint(-2.5F, 1.0F, 4.75F);
		HMG.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 0.4363F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 49, 1, 0.0F, -3.0F, -4.0F, 1, 3, 5, 0.0F, false));

		cube_r4 = new ChModelRenderer(this);
		cube_r4.setRotationPoint(1.05F, 2.5F, 12.0F);
		HMG.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.3927F, 0.0F, 0.1745F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, true));

		cube_r5 = new ChModelRenderer(this);
		cube_r5.setRotationPoint(1.05F, 2.5F, 10.75F);
		HMG.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.3927F, 0.0F, 0.1745F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, true));

		cube_r6 = new ChModelRenderer(this);
		cube_r6.setRotationPoint(1.05F, 2.5F, 9.5F);
		HMG.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.3927F, 0.0F, 0.1745F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, true));

		cube_r7 = new ChModelRenderer(this);
		cube_r7.setRotationPoint(1.05F, 2.5F, 8.25F);
		HMG.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.3927F, 0.0F, 0.1745F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, true));

		cube_r8 = new ChModelRenderer(this);
		cube_r8.setRotationPoint(-1.75F, 1.5F, 23.0F);
		HMG.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 1.5708F, 0.0F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 20, 26, 0.0F, -2.0F, 0.0F, 8, 2, 1, 0.0F, true));

		cube_r9 = new ChModelRenderer(this);
		cube_r9.setRotationPoint(-1.75F, -2.75F, -0.25F);
		HMG.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 1.5708F, 0.0F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 20, 16, -9.0F, -0.5F, 0.0F, 9, 1, 2, 0.0F, false));

		cube_r10 = new ChModelRenderer(this);
		cube_r10.setRotationPoint(-1.75F, -2.25F, 5.75F);
		HMG.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 1.5708F, -0.3054F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 20, 22, -3.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F, false));

		cube_r11 = new ChModelRenderer(this);
		cube_r11.setRotationPoint(1.5F, 1.0F, 12.75F);
		HMG.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.0F, 1.5708F, 0.1745F);
		cube_r11.cubeList.add(new ModelBox(cube_r11, 1, 7, -11.0F, 0.0F, -1.0F, 18, 3, 1, 0.0F, true));

		cube_r12 = new ChModelRenderer(this);
		cube_r12.setRotationPoint(-1.75F, -2.75F, 20.95F);
		HMG.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, -0.2618F, 0.0F);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 20, 48, 0.0F, -0.5F, -1.0F, 1, 1, 1, 0.0F, false));

		cube_r13 = new ChModelRenderer(this);
		cube_r13.setRotationPoint(-1.75F, -2.75F, 17.75F);
		HMG.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.0F, 0.2618F, 0.0F);
		cube_r13.cubeList.add(new ModelBox(cube_r13, 46, 47, 0.0F, -0.5F, 0.0F, 1, 1, 1, 0.0F, false));

		cube_r14 = new ChModelRenderer(this);
		cube_r14.setRotationPoint(1.75F, -2.75F, 17.75F);
		HMG.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, -0.2618F, 0.0F);
		cube_r14.cubeList.add(new ModelBox(cube_r14, 8, 47, -1.0F, -0.5F, 0.0F, 1, 1, 1, 0.0F, false));

		cube_r15 = new ChModelRenderer(this);
		cube_r15.setRotationPoint(1.75F, -2.75F, 20.95F);
		HMG.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.0F, 0.2618F, 0.0F);
		cube_r15.cubeList.add(new ModelBox(cube_r15, 4, 47, -1.0F, -0.5F, -1.0F, 1, 1, 1, 0.0F, false));

		cube_r16 = new ChModelRenderer(this);
		cube_r16.setRotationPoint(1.75F, -2.75F, 8.75F);
		HMG.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.0F, -0.2618F, 0.0F);
		cube_r16.cubeList.add(new ModelBox(cube_r16, 0, 47, -1.0F, -0.5F, 0.0F, 1, 1, 1, 0.0F, false));

		cube_r17 = new ChModelRenderer(this);
		cube_r17.setRotationPoint(1.75F, -2.75F, 12.75F);
		HMG.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.0F, 0.2618F, 0.0F);
		cube_r17.cubeList.add(new ModelBox(cube_r17, 46, 45, -1.0F, -0.5F, -1.0F, 1, 1, 1, 0.0F, false));

		cube_r18 = new ChModelRenderer(this);
		cube_r18.setRotationPoint(-1.75F, -2.75F, 8.75F);
		HMG.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.0F, 0.2618F, 0.0F);
		cube_r18.cubeList.add(new ModelBox(cube_r18, 20, 46, 0.0F, -0.5F, 0.0F, 1, 1, 1, 0.0F, false));

		cube_r19 = new ChModelRenderer(this);
		cube_r19.setRotationPoint(-1.75F, -2.75F, 12.75F);
		HMG.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.0F, -0.2618F, 0.0F);
		cube_r19.cubeList.add(new ModelBox(cube_r19, 14, 27, 0.0F, -0.5F, -1.0F, 1, 1, 1, 0.0F, false));

		cube_r20 = new ChModelRenderer(this);
		cube_r20.setRotationPoint(0.0F, -3.15F, 22.95F);
		HMG.addChild(cube_r20);
		setRotationAngle(cube_r20, 0.0F, 0.0F, -0.7854F);
		cube_r20.cubeList.add(new ModelBox(cube_r20, 6, 44, -0.5F, -0.5F, -1.0F, 1, 1, 2, 0.0F, false));

		cube_r21 = new ChModelRenderer(this);
		cube_r21.setRotationPoint(-1.05F, 2.5F, 12.0F);
		HMG.addChild(cube_r21);
		setRotationAngle(cube_r21, -0.3927F, 0.0F, -0.1745F);
		cube_r21.cubeList.add(new ModelBox(cube_r21, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, false));

		cube_r22 = new ChModelRenderer(this);
		cube_r22.setRotationPoint(-1.05F, 2.5F, 10.75F);
		HMG.addChild(cube_r22);
		setRotationAngle(cube_r22, -0.3927F, 0.0F, -0.1745F);
		cube_r22.cubeList.add(new ModelBox(cube_r22, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, false));

		cube_r23 = new ChModelRenderer(this);
		cube_r23.setRotationPoint(-1.05F, 2.5F, 9.5F);
		HMG.addChild(cube_r23);
		setRotationAngle(cube_r23, -0.3927F, 0.0F, -0.1745F);
		cube_r23.cubeList.add(new ModelBox(cube_r23, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, false));

		cube_r24 = new ChModelRenderer(this);
		cube_r24.setRotationPoint(-1.05F, 2.5F, 8.25F);
		HMG.addChild(cube_r24);
		setRotationAngle(cube_r24, -0.3927F, 0.0F, -0.1745F);
		cube_r24.cubeList.add(new ModelBox(cube_r24, 30, 45, -0.5F, -1.0F, -0.5F, 1, 2, 1, -0.2F, false));

		cube_r25 = new ChModelRenderer(this);
		cube_r25.setRotationPoint(-2.5F, 1.0F, 4.75F);
		HMG.addChild(cube_r25);
		setRotationAngle(cube_r25, 0.0F, 0.0F, -0.0873F);
		cube_r25.cubeList.add(new ModelBox(cube_r25, 0, 11, 0.0F, 0.0F, -4.0F, 3, 6, 5, 0.0F, false));

		cube_r26 = new ChModelRenderer(this);
		cube_r26.setRotationPoint(1.75F, -2.25F, 12.75F);
		HMG.addChild(cube_r26);
		setRotationAngle(cube_r26, 0.0F, 0.0F, 0.3054F);
		cube_r26.cubeList.add(new ModelBox(cube_r26, 14, 29, -2.0F, 0.0F, 0.0F, 2, 1, 5, 0.0F, false));
		cube_r26.cubeList.add(new ModelBox(cube_r26, 42, 28, -2.0F, 0.0F, 8.2F, 2, 1, 3, 0.0F, false));

		cube_r27 = new ChModelRenderer(this);
		cube_r27.setRotationPoint(1.75F, -2.25F, 5.75F);
		HMG.addChild(cube_r27);
		setRotationAngle(cube_r27, 0.0F, -1.5708F, 0.3054F);
		cube_r27.cubeList.add(new ModelBox(cube_r27, 20, 22, -6.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F, false));

		cube_r28 = new ChModelRenderer(this);
		cube_r28.setRotationPoint(-0.5F, 4.4F, 16.75F);
		HMG.addChild(cube_r28);
		setRotationAngle(cube_r28, 0.0F, -1.5708F, 0.0F);
		cube_r28.cubeList.add(new ModelBox(cube_r28, 38, 26, 0.0F, -1.0F, -1.0F, 7, 1, 1, 0.0F, false));

		cube_r29 = new ChModelRenderer(this);
		cube_r29.setRotationPoint(1.75F, 1.5F, 23.0F);
		HMG.addChild(cube_r29);
		setRotationAngle(cube_r29, 0.0F, -1.5708F, 0.0F);
		cube_r29.cubeList.add(new ModelBox(cube_r29, 20, 26, -8.0F, -2.0F, 0.0F, 8, 2, 1, 0.0F, false));

		cube_r30 = new ChModelRenderer(this);
		cube_r30.setRotationPoint(-0.5F, 4.15F, 22.85F);
		HMG.addChild(cube_r30);
		setRotationAngle(cube_r30, -0.1745F, 0.0F, 0.0F);
		cube_r30.cubeList.add(new ModelBox(cube_r30, 26, 45, 0.0F, -3.0F, 0.0F, 1, 3, 1, 0.1F, false));

		cube_r31 = new ChModelRenderer(this);
		cube_r31.setRotationPoint(1.75F, -2.75F, 20.95F);
		HMG.addChild(cube_r31);
		setRotationAngle(cube_r31, 0.0F, 0.0436F, 0.0F);
		cube_r31.cubeList.add(new ModelBox(cube_r31, 42, 32, -2.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F, false));

		cube_r32 = new ChModelRenderer(this);
		cube_r32.setRotationPoint(-1.5F, 1.0F, 12.75F);
		HMG.addChild(cube_r32);
		setRotationAngle(cube_r32, 0.0F, -1.5708F, -0.1745F);
		cube_r32.cubeList.add(new ModelBox(cube_r32, 1, 7, -7.0F, 0.0F, -1.0F, 18, 3, 1, 0.0F, false));

		cube_r33 = new ChModelRenderer(this);
		cube_r33.setRotationPoint(-1.75F, -2.75F, 20.95F);
		HMG.addChild(cube_r33);
		setRotationAngle(cube_r33, 0.0F, -0.0436F, 0.0F);
		cube_r33.cubeList.add(new ModelBox(cube_r33, 42, 17, 0.0F, -0.5F, 0.0F, 2, 1, 3, 0.0F, false));

		cube_r34 = new ChModelRenderer(this);
		cube_r34.setRotationPoint(-1.75F, -2.25F, 18.95F);
		HMG.addChild(cube_r34);
		setRotationAngle(cube_r34, 0.0F, 0.0F, -0.3054F);
		cube_r34.cubeList.add(new ModelBox(cube_r34, 40, 22, 0.0F, 0.0F, 2.0F, 2, 1, 3, 0.0F, false));
		cube_r34.cubeList.add(new ModelBox(cube_r34, 0, 27, 0.0F, 0.0F, -6.2F, 2, 1, 5, 0.0F, false));

		cube_r35 = new ChModelRenderer(this);
		cube_r35.setRotationPoint(1.75F, -2.75F, -0.25F);
		HMG.addChild(cube_r35);
		setRotationAngle(cube_r35, 0.0F, -1.5708F, 0.0F);
		cube_r35.cubeList.add(new ModelBox(cube_r35, 20, 16, 0.0F, -0.5F, 0.0F, 9, 1, 2, 0.0F, true));

		cube_r36 = new ChModelRenderer(this);
		cube_r36.setRotationPoint(0.7F, -0.65F, 23.4F);
		HMG.addChild(cube_r36);
		setRotationAngle(cube_r36, 0.0F, 0.0F, -0.7854F);
		cube_r36.cubeList.add(new ModelBox(cube_r36, 40, 6, -2.0F, -2.0F, -1.0F, 3, 3, 3, -0.6F, false));

		cube_r37 = new ChModelRenderer(this);
		cube_r37.setRotationPoint(0.0F, -0.65F, 23.15F);
		HMG.addChild(cube_r37);
		setRotationAngle(cube_r37, 0.0F, 0.0F, -0.7854F);
		cube_r37.cubeList.add(new ModelBox(cube_r37, 0, 44, -1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F, false));

		cube_r38 = new ChModelRenderer(this);
		cube_r38.setRotationPoint(-0.5F, 2.0F, 9.85F);
		HMG.addChild(cube_r38);
		setRotationAngle(cube_r38, 0.0F, 1.5708F, 0.0F);
		cube_r38.cubeList.add(new ModelBox(cube_r38, 0, 0, -14.0F, -4.0F, -1.0F, 24, 3, 3, 0.0F, false));

		muzzle_flash = new MuzzleFlashRenderer(this);
		muzzle_flash.setRotationPoint(0.0F, 0.25F, 25.75F);
		HMG.addChild(muzzle_flash);
		setRotationAngle(muzzle_flash, -3.1416F, 0.0F, 3.1416F);
		muzzle_flash.cubeList.add(new ModelBox(muzzle_flash, 46, 47, 0.0F, -5.0F, -8.0F, 0, 8, 9, 0.0F, true));
		muzzle_flash.cubeList.add(new ModelBox(muzzle_flash, 28, 55, -2.0F, -3.0F, -4.0F, 4, 4, 5, 0.0F, false));

		cube_r39 = new ChModelRenderer(this);
		cube_r39.setRotationPoint(0.0F, -1.0F, -3.5F);
		muzzle_flash.addChild(cube_r39);
		setRotationAngle(cube_r39, 0.0F, 0.0F, 1.5708F);
		cube_r39.cubeList.add(new ModelBox(cube_r39, 46, 47, 0.0F, -4.0F, -4.5F, 0, 8, 9, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		HMG.render(f5);
	}

}