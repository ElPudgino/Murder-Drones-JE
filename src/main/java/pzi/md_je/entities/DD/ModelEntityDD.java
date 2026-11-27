package pzi.md_je.entities.DD;

import java.io.IOException;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pzi.md_je.MD;
import pzi.md_je.anim.AnimModelBase;
import pzi.md_je.anim.ChModelRenderer;
import pzi.md_je.anim.Transforms;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.generic.weapons.InnateWeapon;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.rendering.AnimLoader;
import pzi.md_je.rendering.BloomQueue;
import pzi.md_je.rendering.GraphicsUtils;
import pzi.md_je.rendering.AnimLoader.AnimationContainer;
import pzi.md_je.rendering.AnimLoader.AnimationData;
import pzi.md_je.util.math;

public class ModelEntityDD extends AnimModelBase 
{
	public static ModelHMGDD hmgmodel = null;

	public final ChModelRenderer root;
	public final ChModelRenderer leg_right;
	public final ChModelRenderer cube_r1;
	public final ChModelRenderer shin_right;
	public final ChModelRenderer leg_left;
	public final ChModelRenderer cube_r2;
	public final ChModelRenderer shin_left;
	public final ChModelRenderer hips;
	public final ChModelRenderer cube_r3;
	public final ChModelRenderer cube_r4;
	public final ChModelRenderer cube_r5;
	public final ChModelRenderer cube_r6;
	public final ChModelRenderer cube_r7;
	public final ChModelRenderer cube_r8;
	public final ChModelRenderer mid;
	public final ChModelRenderer cube_r9;
	public final ChModelRenderer wing_base_right;
	public final ChModelRenderer cube_r10;
	public final ChModelRenderer cube_r11;
	public final ChModelRenderer cube_r12;
	public final ChModelRenderer cube_r13;
	public final ChModelRenderer cube_r14;
	public final ChModelRenderer cube_r15;
	public final ChModelRenderer cube_r16;
	public final ChModelRenderer wing_end;
	public final ChModelRenderer cube_r17;
	public final ChModelRenderer cube_r18;
	public final ChModelRenderer cube_r19;
	public final ChModelRenderer cube_r20;
	public final ChModelRenderer wingb_1;
	public final ChModelRenderer cube_r21;
	public final ChModelRenderer wingb_2;
	public final ChModelRenderer cube_r22;
	public final ChModelRenderer wingb_3;
	public final ChModelRenderer wingb_4;
	public final ChModelRenderer cube_r23;
	public final ChModelRenderer wingb_5;
	public final ChModelRenderer cube_r24;
	public final ChModelRenderer wing_base_left;
	public final ChModelRenderer cube_r25;
	public final ChModelRenderer cube_r26;
	public final ChModelRenderer cube_r27;
	public final ChModelRenderer cube_r28;
	public final ChModelRenderer cube_r29;
	public final ChModelRenderer cube_r30;
	public final ChModelRenderer cube_r31;
	public final ChModelRenderer wing_end2;
	public final ChModelRenderer cube_r32;
	public final ChModelRenderer cube_r33;
	public final ChModelRenderer cube_r34;
	public final ChModelRenderer cube_r35;
	public final ChModelRenderer wingb_6;
	public final ChModelRenderer cube_r36;
	public final ChModelRenderer wingb_7;
	public final ChModelRenderer cube_r37;
	public final ChModelRenderer wingb_8;
	public final ChModelRenderer wingb_9;
	public final ChModelRenderer cube_r38;
	public final ChModelRenderer wingb_10;
	public final ChModelRenderer cube_r39;
	public final ChModelRenderer chest;
	public final ChModelRenderer cube_r40;
	public final ChModelRenderer cube_r41;
	public final ChModelRenderer cube_r42;
	public final ChModelRenderer cube_r43;
	public final ChModelRenderer cube_r44;
	public final ChModelRenderer cube_r45;
	public final ChModelRenderer arm_l;
	public final ChModelRenderer forearm_l;
	public final ChModelRenderer arm_r;
	public final ChModelRenderer forearm_r;
	public final ChModelRenderer hand_normal;
	public final ChModelRenderer hand_claw;
	public final ChModelRenderer h3;
	public final ChModelRenderer cube_r46;
	public final ChModelRenderer cube_r47;
	public final ChModelRenderer cube_r48;
	public final ChModelRenderer cl2;
	public final ChModelRenderer cube_r49;
	public final ChModelRenderer cube_r50;
	public final ChModelRenderer cube_r51;
	public final ChModelRenderer cube_r52;
	public final ChModelRenderer cube_r53;
	public final ChModelRenderer cl1;
	public final ChModelRenderer cube_r54;
	public final ChModelRenderer cube_r55;
	public final ChModelRenderer cube_r56;
	public final ChModelRenderer cube_r57;
	public final ChModelRenderer cube_r58;
	public final ChModelRenderer cl3;
	public final ChModelRenderer cube_r59;
	public final ChModelRenderer cube_r60;
	public final ChModelRenderer cube_r61;
	public final ChModelRenderer cube_r62;
	public final ChModelRenderer cube_r63;
	public final ChModelRenderer hand_hmg;
	public final ChModelRenderer cube_r64;
	public final ChModelRenderer cube_r65;
	public final ChModelRenderer hmg;
	public final ChModelRenderer head;
	public final ChModelRenderer cube_r66;
	public final ChModelRenderer cube_r67;
	public final ChModelRenderer cube_r68;
	public final ChModelRenderer faces;
	public final ChModelRenderer x;
	public final ChModelRenderer cube_r69;
	public final ChModelRenderer cube_r70;
	public final ChModelRenderer hollow;
	public final ChModelRenderer cube_r71;
	public final ChModelRenderer normal;
	public final ChModelRenderer cube_r72;
	public final ChModelRenderer error;
	public final ChModelRenderer cube_r73;
	public final ChModelRenderer mouth;
	public final ChModelRenderer open;
	public final ChModelRenderer cube_r74;
	public final ChModelRenderer cube_r75;
	public final ChModelRenderer cat;
	public final ChModelRenderer cube_r76;
	public final ChModelRenderer sad;
	public final ChModelRenderer cube_r77;
	public final ChModelRenderer bone;
	public final ChModelRenderer belly_full;
	public final ChModelRenderer cube_r78;
	public final ChModelRenderer cube_r79;

	public ModelEntityDD() 
	{
		textureWidth = 256;
		textureHeight = 256;

		root = new ChModelRenderer(this, "root");
		root.setRotationPoint(0.0F, 4.0F, 0.0F);
		

		leg_right = new ChModelRenderer(this, "leg_right");
		leg_right.setRotationPoint(-3.0F, 1.5F, 0.5F);
		root.addChild(leg_right);
		leg_right.cubeList.add(new ModelBox(leg_right, 16, 65, -2.45F, 4.5F, -2.1F, 5, 4, 5, 0.0F, false));

		cube_r1 = new ChModelRenderer(this, "cube_r1");
		cube_r1.setRotationPoint(0.15F, 2.7114F, 0.5067F);
		leg_right.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.0436F, 0.0019F, 0.0436F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 56, 40, -2.5F, -3.5F, -2.5F, 5, 6, 5, -0.2F, false));

		shin_right = new ChModelRenderer(this, "shin_right");
		shin_right.setRotationPoint(0.0F, 8.5F, 0.0F);
		leg_right.addChild(shin_right);
		shin_right.cubeList.add(new ModelBox(shin_right, 0, 63, -1.95F, 0.0F, -1.85F, 4, 10, 4, 0.0F, false));

		leg_left = new ChModelRenderer(this, "leg_left");
		leg_left.setRotationPoint(3.0F, 1.5F, 0.5F);
		root.addChild(leg_left);
		leg_left.cubeList.add(new ModelBox(leg_left, 64, 31, -2.55F, 4.5F, -2.1F, 5, 4, 5, 0.0F, false));

		cube_r2 = new ChModelRenderer(this, "cube_r2");
		cube_r2.setRotationPoint(-0.15F, 2.7114F, 0.5067F);
		leg_left.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.0436F, -0.0019F, -0.0436F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 52, -2.5F, -3.5F, -2.5F, 5, 6, 5, -0.2F, false));

		shin_left = new ChModelRenderer(this, "shin_left");
		shin_left.setRotationPoint(0.0F, 8.5F, 0.0F);
		leg_left.addChild(shin_left);
		shin_left.cubeList.add(new ModelBox(shin_left, 56, 51, -2.05F, 0.0F, -1.85F, 4, 10, 4, 0.0F, false));

		hips = new ChModelRenderer(this, "hips");
		hips.setRotationPoint(0.0F, 0.8F, 0.75F);
		root.addChild(hips);
		setRotationAngle(hips, -0.0436F, 0.0F, 0.0F);
		

		cube_r3 = new ChModelRenderer(this, "cube_r3");
		cube_r3.setRotationPoint(-4.25F, 0.1F, 0.2F);
		hips.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.1461F, -0.022F, 0.3285F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 68, 65, -1.0F, -4.0F, -2.5F, 4, 4, 5, 0.0F, false));

		cube_r4 = new ChModelRenderer(this, "cube_r4");
		cube_r4.setRotationPoint(0.0F, 1.05F, 0.5F);
		hips.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.2182F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 68, 74, -2.5F, -4.0F, -0.5F, 5, 4, 3, 0.0F, false));

		cube_r5 = new ChModelRenderer(this, "cube_r5");
		cube_r5.setRotationPoint(0.0F, -1.3F, -2.6F);
		hips.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.2182F, 0.0F, 0.0F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 78, 81, -1.0F, -1.25F, -0.05F, 2, 2, 3, 0.0F, false));

		cube_r6 = new ChModelRenderer(this, "cube_r6");
		cube_r6.setRotationPoint(0.2F, 0.9F, -2.3F);
		hips.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.1673F, -0.056F, 0.2559F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 30, 77, -3.0F, -2.25F, 0.0F, 3, 3, 3, 0.0F, false));

		cube_r7 = new ChModelRenderer(this, "cube_r7");
		cube_r7.setRotationPoint(-0.2F, 0.9F, -2.3F);
		hips.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.1673F, 0.056F, -0.2559F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 42, 77, 0.0F, -2.25F, 0.0F, 3, 3, 3, 0.0F, false));

		cube_r8 = new ChModelRenderer(this, "cube_r8");
		cube_r8.setRotationPoint(4.25F, 0.1F, 0.2F);
		hips.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.1461F, 0.022F, -0.3285F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 66, 22, -3.0F, -4.0F, -2.5F, 4, 4, 5, 0.0F, false));

		mid = new ChModelRenderer(this, "mid");
		mid.setRotationPoint(0.5F, -2.3F, -0.05F);
		hips.addChild(mid);
		

		cube_r9 = new ChModelRenderer(this, "cube_r9");
		cube_r9.setRotationPoint(0.0F, 0.3F, -1.0F);
		mid.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0436F, 0.0F, 0.0F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 40, 0, -4.0F, -7.0F, -1.5F, 7, 7, 4, 0.0F, false));

		wing_base_right = new ChModelRenderer(this, "wing_base_right");
		wing_base_right.setRotationPoint(2.5F, -8.132F, 2.8476F);
		mid.addChild(wing_base_right);
		setRotationAngle(wing_base_right, 2.087F, -0.2025F, -0.8491F);
		wing_base_right.cubeList.add(new ModelBox(wing_base_right, 127, 29, 3.0F, -1.0F, -1.0F, 15, 3, 4, 0.0F, false));

		cube_r10 = new ChModelRenderer(this, "cube_r10");
		cube_r10.setRotationPoint(23.0F, 0.25F, -1.0F);
		wing_base_right.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 1.0908F, 0.0F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 124, 0, -4.0F, 0.0F, -27.0F, 7, 0, 28, 0.0F, false));

		cube_r11 = new ChModelRenderer(this, "cube_r11");
		cube_r11.setRotationPoint(30.0F, 0.5F, -1.0F);
		wing_base_right.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.0F, 0.829F, 0.0F);
		cube_r11.cubeList.add(new ModelBox(cube_r11, 124, 0, -4.0F, 0.0F, -27.0F, 7, 0, 28, 0.0F, false));

		cube_r12 = new ChModelRenderer(this, "cube_r12");
		cube_r12.setRotationPoint(25.0F, 2.0F, -1.15F);
		wing_base_right.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, 0.1309F, 0.0F);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 127, 11, -4.0F, -3.0F, -1.0F, 7, 3, 2, 0.0F, false));

		cube_r13 = new ChModelRenderer(this, "cube_r13");
		cube_r13.setRotationPoint(19.0F, 0.0F, -0.8F);
		wing_base_right.addChild(cube_r13);
		setRotationAngle(cube_r13, 0.0F, -0.1309F, 0.0F);
		cube_r13.cubeList.add(new ModelBox(cube_r13, 111, 21, -1.0F, -1.0F, -1.0F, 9, 3, 5, 0.1F, false));

		cube_r14 = new ChModelRenderer(this, "cube_r14");
		cube_r14.setRotationPoint(17.5F, 0.0F, -0.9F);
		wing_base_right.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, 0.1745F, 0.0F);
		cube_r14.cubeList.add(new ModelBox(cube_r14, 133, 94, -4.0F, -1.0F, -1.0F, 5, 3, 2, -0.1F, false));

		cube_r15 = new ChModelRenderer(this, "cube_r15");
		cube_r15.setRotationPoint(3.5F, 0.0F, -0.45F);
		wing_base_right.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.0F, -0.288F, 0.0F);
		cube_r15.cubeList.add(new ModelBox(cube_r15, 102, 31, -1.0171F, -1.0F, -1.2611F, 10, 3, 2, -0.1F, false));

		cube_r16 = new ChModelRenderer(this, "cube_r16");
		cube_r16.setRotationPoint(0.25F, -0.5F, 1.5F);
		wing_base_right.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.0F, 0.0873F, 0.0F);
		cube_r16.cubeList.add(new ModelBox(cube_r16, 110, 13, -3.0F, -0.5F, -3.0F, 6, 3, 5, 0.1F, false));

		wing_end = new ChModelRenderer(this, "wing_end");
		wing_end.setRotationPoint(28.75F, 0.0F, 1.65F);
		wing_base_right.addChild(wing_end);
		setRotationAngle(wing_end, 0.0F, 0.7854F, 0.0F);
		

		cube_r17 = new ChModelRenderer(this, "cube_r17");
		cube_r17.setRotationPoint(-0.1F, 0.0F, 0.6F);
		wing_end.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.0F, 0.6545F, 0.0F);
		cube_r17.cubeList.add(new ModelBox(cube_r17, 119, 0, -3.0F, -1.5F, -4.0F, 7, 4, 7, 0.0F, false));

		cube_r18 = new ChModelRenderer(this, "cube_r18");
		cube_r18.setRotationPoint(2.25F, 1.0F, -3.35F);
		wing_end.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.0F, -0.096F, 0.0F);
		cube_r18.cubeList.add(new ModelBox(cube_r18, 112, 41, -1.0F, -2.0F, 0.0F, 22, 3, 2, 0.0F, false));

		cube_r19 = new ChModelRenderer(this, "cube_r19");
		cube_r19.setRotationPoint(3.25F, 2.0F, -0.4F);
		wing_end.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.0F, -0.0175F, 0.0F);
		cube_r19.cubeList.add(new ModelBox(cube_r19, 112, 36, -1.0F, -3.0F, -1.0F, 14, 3, 2, -0.1F, false));

		cube_r20 = new ChModelRenderer(this, "cube_r20");
		cube_r20.setRotationPoint(4.25F, 0.5F, 2.15F);
		wing_end.addChild(cube_r20);
		setRotationAngle(cube_r20, 0.0F, 0.0785F, 0.0F);
		cube_r20.cubeList.add(new ModelBox(cube_r20, 120, 46, -3.0F, -1.5F, -1.5F, 23, 3, 2, 0.1F, false));

		wingb_1 = new ChModelRenderer(this, "wingb_1");
		wingb_1.setRotationPoint(22.5F, 1.25F, -2.15F);
		wing_end.addChild(wingb_1);
		

		cube_r21 = new ChModelRenderer(this, "cube_r21");
		cube_r21.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_1.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.0F, -0.6545F, 0.0F);
		cube_r21.cubeList.add(new ModelBox(cube_r21, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, false));

		wingb_2 = new ChModelRenderer(this, "wingb_2");
		wingb_2.setRotationPoint(19.25F, 1.0F, -3.35F);
		wing_end.addChild(wingb_2);
		

		cube_r22 = new ChModelRenderer(this, "cube_r22");
		cube_r22.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_2.addChild(cube_r22);
		setRotationAngle(cube_r22, 0.0F, -0.2618F, 0.0F);
		cube_r22.cubeList.add(new ModelBox(cube_r22, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, false));

		wingb_3 = new ChModelRenderer(this, "wingb_3");
		wingb_3.setRotationPoint(14.75F, 1.25F, -3.35F);
		wing_end.addChild(wingb_3);
		wingb_3.cubeList.add(new ModelBox(wingb_3, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, false));

		wingb_4 = new ChModelRenderer(this, "wingb_4");
		wingb_4.setRotationPoint(10.0F, 0.75F, -3.35F);
		wing_end.addChild(wingb_4);
		

		cube_r23 = new ChModelRenderer(this, "cube_r23");
		cube_r23.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_4.addChild(cube_r23);
		setRotationAngle(cube_r23, 0.0F, 0.2618F, 0.0F);
		cube_r23.cubeList.add(new ModelBox(cube_r23, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, false));

		wingb_5 = new ChModelRenderer(this, "wingb_5");
		wingb_5.setRotationPoint(5.0F, 1.0F, -3.35F);
		wing_end.addChild(wingb_5);
		

		cube_r24 = new ChModelRenderer(this, "cube_r24");
		cube_r24.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_5.addChild(cube_r24);
		setRotationAngle(cube_r24, 0.0F, 0.5236F, 0.0F);
		cube_r24.cubeList.add(new ModelBox(cube_r24, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, false));

		wing_base_left = new ChModelRenderer(this, "wing_base_left");
		wing_base_left.setRotationPoint(-3.5F, -8.132F, 2.8476F);
		mid.addChild(wing_base_left);
		setRotationAngle(wing_base_left, 2.087F, 0.2025F, 0.8491F);
		wing_base_left.cubeList.add(new ModelBox(wing_base_left, 127, 29, -18.0F, -1.0F, -1.0F, 15, 3, 4, 0.0F, true));

		cube_r25 = new ChModelRenderer(this, "cube_r25");
		cube_r25.setRotationPoint(-23.0F, 0.25F, -1.0F);
		wing_base_left.addChild(cube_r25);
		setRotationAngle(cube_r25, 0.0F, -1.0908F, 0.0F);
		cube_r25.cubeList.add(new ModelBox(cube_r25, 124, 0, -3.0F, 0.0F, -27.0F, 7, 0, 28, 0.0F, true));

		cube_r26 = new ChModelRenderer(this, "cube_r26");
		cube_r26.setRotationPoint(-30.0F, 0.5F, -1.0F);
		wing_base_left.addChild(cube_r26);
		setRotationAngle(cube_r26, 0.0F, -0.829F, 0.0F);
		cube_r26.cubeList.add(new ModelBox(cube_r26, 124, 0, -3.0F, 0.0F, -27.0F, 7, 0, 28, 0.0F, true));

		cube_r27 = new ChModelRenderer(this, "cube_r27");
		cube_r27.setRotationPoint(-25.0F, 2.0F, -1.15F);
		wing_base_left.addChild(cube_r27);
		setRotationAngle(cube_r27, 0.0F, -0.1309F, 0.0F);
		cube_r27.cubeList.add(new ModelBox(cube_r27, 127, 11, -3.0F, -3.0F, -1.0F, 7, 3, 2, 0.0F, true));

		cube_r28 = new ChModelRenderer(this, "cube_r28");
		cube_r28.setRotationPoint(-19.0F, 0.0F, -0.8F);
		wing_base_left.addChild(cube_r28);
		setRotationAngle(cube_r28, 0.0F, 0.1309F, 0.0F);
		cube_r28.cubeList.add(new ModelBox(cube_r28, 111, 21, -8.0F, -1.0F, -1.0F, 9, 3, 5, 0.1F, true));

		cube_r29 = new ChModelRenderer(this, "cube_r29");
		cube_r29.setRotationPoint(-17.5F, 0.0F, -0.9F);
		wing_base_left.addChild(cube_r29);
		setRotationAngle(cube_r29, 0.0F, -0.1745F, 0.0F);
		cube_r29.cubeList.add(new ModelBox(cube_r29, 133, 94, -1.0F, -1.0F, -1.0F, 5, 3, 2, -0.1F, true));

		cube_r30 = new ChModelRenderer(this, "cube_r30");
		cube_r30.setRotationPoint(-3.5F, 0.0F, -0.45F);
		wing_base_left.addChild(cube_r30);
		setRotationAngle(cube_r30, 0.0F, 0.288F, 0.0F);
		cube_r30.cubeList.add(new ModelBox(cube_r30, 102, 31, -8.9829F, -1.0F, -1.2611F, 10, 3, 2, -0.1F, true));

		cube_r31 = new ChModelRenderer(this, "cube_r31");
		cube_r31.setRotationPoint(-0.25F, -0.5F, 1.5F);
		wing_base_left.addChild(cube_r31);
		setRotationAngle(cube_r31, 0.0F, -0.0873F, 0.0F);
		cube_r31.cubeList.add(new ModelBox(cube_r31, 110, 13, -3.0F, -0.5F, -3.0F, 6, 3, 5, 0.1F, true));

		wing_end2 = new ChModelRenderer(this, "wing_end2");
		wing_end2.setRotationPoint(-28.75F, 0.0F, 1.65F);
		wing_base_left.addChild(wing_end2);
		setRotationAngle(wing_end2, 0.0F, -0.7854F, 0.0F);
		

		cube_r32 = new ChModelRenderer(this, "cube_r32");
		cube_r32.setRotationPoint(0.1F, 0.0F, 0.6F);
		wing_end2.addChild(cube_r32);
		setRotationAngle(cube_r32, 0.0F, -0.6545F, 0.0F);
		cube_r32.cubeList.add(new ModelBox(cube_r32, 119, 0, -4.0F, -1.5F, -4.0F, 7, 4, 7, 0.0F, true));

		cube_r33 = new ChModelRenderer(this, "cube_r33");
		cube_r33.setRotationPoint(-2.25F, 1.0F, -3.35F);
		wing_end2.addChild(cube_r33);
		setRotationAngle(cube_r33, 0.0F, 0.096F, 0.0F);
		cube_r33.cubeList.add(new ModelBox(cube_r33, 112, 41, -21.0F, -2.0F, 0.0F, 22, 3, 2, 0.0F, true));

		cube_r34 = new ChModelRenderer(this, "cube_r34");
		cube_r34.setRotationPoint(-3.25F, 2.0F, -0.4F);
		wing_end2.addChild(cube_r34);
		setRotationAngle(cube_r34, 0.0F, 0.0175F, 0.0F);
		cube_r34.cubeList.add(new ModelBox(cube_r34, 112, 36, -13.0F, -3.0F, -1.0F, 14, 3, 2, -0.1F, true));

		cube_r35 = new ChModelRenderer(this, "cube_r35");
		cube_r35.setRotationPoint(-4.25F, 0.5F, 2.15F);
		wing_end2.addChild(cube_r35);
		setRotationAngle(cube_r35, 0.0F, -0.0785F, 0.0F);
		cube_r35.cubeList.add(new ModelBox(cube_r35, 120, 46, -20.0F, -1.5F, -1.5F, 23, 3, 2, 0.1F, true));

		wingb_6 = new ChModelRenderer(this, "wingb_6");
		wingb_6.setRotationPoint(-22.5F, 1.25F, -2.15F);
		wing_end2.addChild(wingb_6);
		

		cube_r36 = new ChModelRenderer(this, "cube_r36");
		cube_r36.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_6.addChild(cube_r36);
		setRotationAngle(cube_r36, 0.0F, 0.6545F, 0.0F);
		cube_r36.cubeList.add(new ModelBox(cube_r36, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, true));

		wingb_7 = new ChModelRenderer(this, "wingb_7");
		wingb_7.setRotationPoint(-19.25F, 1.0F, -3.35F);
		wing_end2.addChild(wingb_7);
		

		cube_r37 = new ChModelRenderer(this, "cube_r37");
		cube_r37.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_7.addChild(cube_r37);
		setRotationAngle(cube_r37, 0.0F, 0.2618F, 0.0F);
		cube_r37.cubeList.add(new ModelBox(cube_r37, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, true));

		wingb_8 = new ChModelRenderer(this, "wingb_8");
		wingb_8.setRotationPoint(-14.75F, 1.25F, -3.35F);
		wing_end2.addChild(wingb_8);
		wingb_8.cubeList.add(new ModelBox(wingb_8, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, true));

		wingb_9 = new ChModelRenderer(this, "wingb_9");
		wingb_9.setRotationPoint(-10.0F, 0.75F, -3.35F);
		wing_end2.addChild(wingb_9);
		

		cube_r38 = new ChModelRenderer(this, "cube_r38");
		cube_r38.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_9.addChild(cube_r38);
		setRotationAngle(cube_r38, 0.0F, -0.2618F, 0.0F);
		cube_r38.cubeList.add(new ModelBox(cube_r38, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, true));

		wingb_10 = new ChModelRenderer(this, "wingb_10");
		wingb_10.setRotationPoint(-5.0F, 1.0F, -3.35F);
		wing_end2.addChild(wingb_10);
		

		cube_r39 = new ChModelRenderer(this, "cube_r39");
		cube_r39.setRotationPoint(0.0F, 0.0F, 0.0F);
		wingb_10.addChild(cube_r39);
		setRotationAngle(cube_r39, 0.0F, -0.5236F, 0.0F);
		cube_r39.cubeList.add(new ModelBox(cube_r39, 114, 53, -5.0F, -1.0F, -36.0F, 10, 1, 38, 0.0F, true));

		chest = new ChModelRenderer(this, "chest");
		chest.setRotationPoint(-0.5F, -5.75F, -0.7F);
		mid.addChild(chest);
		setRotationAngle(chest, 0.3054F, 0.0F, 0.0F);
		chest.cubeList.add(new ModelBox(chest, 62, 0, -4.0F, -1.75F, -2.3F, 8, 2, 4, 0.0F, false));

		cube_r40 = new ChModelRenderer(this, "cube_r40");
		cube_r40.setRotationPoint(0.0F, -4.1103F, -3.2135F);
		chest.addChild(cube_r40);
		setRotationAngle(cube_r40, 0.6283F, 0.0F, 0.0F);
		cube_r40.cubeList.add(new ModelBox(cube_r40, 89, 32, -2.0F, -0.7832F, -2.1294F, 4, 3, 4, -1.2F, false));

		cube_r41 = new ChModelRenderer(this, "cube_r41");
		cube_r41.setRotationPoint(0.0F, -2.6456F, -1.6696F);
		chest.addChild(cube_r41);
		setRotationAngle(cube_r41, 0.6545F, 0.0F, 0.0F);
		cube_r41.cubeList.add(new ModelBox(cube_r41, 87, 0, -4.0F, -1.5F, -1.5F, 8, 3, 4, 0.15F, false));

		cube_r42 = new ChModelRenderer(this, "cube_r42");
		cube_r42.setRotationPoint(-3.0F, -2.2129F, 0.3639F);
		chest.addChild(cube_r42);
		setRotationAngle(cube_r42, -0.3357F, -0.0905F, -0.2921F);
		cube_r42.cubeList.add(new ModelBox(cube_r42, 16, 74, -1.0F, -4.0F, -1.75F, 2, 4, 5, 0.0F, false));

		cube_r43 = new ChModelRenderer(this, "cube_r43");
		cube_r43.setRotationPoint(3.0F, -2.2129F, 0.3639F);
		chest.addChild(cube_r43);
		setRotationAngle(cube_r43, -0.3357F, 0.0905F, 0.2921F);
		cube_r43.cubeList.add(new ModelBox(cube_r43, 72, 51, -1.0F, -4.0F, -1.75F, 2, 4, 5, 0.0F, false));

		cube_r44 = new ChModelRenderer(this, "cube_r44");
		cube_r44.setRotationPoint(0.0F, -1.75F, -2.3F);
		chest.addChild(cube_r44);
		setRotationAngle(cube_r44, -0.4363F, 0.0F, 0.0F);
		cube_r44.cubeList.add(new ModelBox(cube_r44, 0, 33, -4.0F, -5.0F, 0.0F, 8, 5, 5, 0.05F, false));

		cube_r45 = new ChModelRenderer(this, "cube_r45");
		cube_r45.setRotationPoint(0.0F, 25.7383F, -7.9248F);
		chest.addChild(cube_r45);
		setRotationAngle(cube_r45, -0.3054F, 0.0F, 0.0F);
		cube_r45.cubeList.add(new ModelBox(cube_r45, 66, 81, -1.5F, -34.25F, -1.5F, 3, 2, 3, 0.0F, false));

		arm_l = new ChModelRenderer(this, "arm_l");
		arm_l.setRotationPoint(4.0F, -4.0654F, 1.4723F);
		chest.addChild(arm_l);
		setRotationAngle(arm_l, -0.2485F, -0.0905F, -0.2921F);
		arm_l.cubeList.add(new ModelBox(arm_l, 0, 77, -1.0F, 0.0F, -1.25F, 3, 8, 3, 0.0F, false));

		forearm_l = new ChModelRenderer(this, "forearm_l");
		forearm_l.setRotationPoint(0.5F, 8.0F, 0.0F);
		arm_l.addChild(forearm_l);
		forearm_l.cubeList.add(new ModelBox(forearm_l, 52, 65, -2.0F, 0.0F, -1.75F, 4, 8, 4, 0.0F, false));

		arm_r = new ChModelRenderer(this, "arm_r");
		arm_r.setRotationPoint(-4.0F, -4.0654F, 1.4723F);
		chest.addChild(arm_r);
		setRotationAngle(arm_r, -0.2485F, 0.0905F, 0.2921F);
		arm_r.cubeList.add(new ModelBox(arm_r, 76, 40, -2.0F, 0.0F, -1.25F, 3, 8, 3, 0.0F, false));

		forearm_r = new ChModelRenderer(this, "forearm_r");
		forearm_r.setRotationPoint(-0.5F, 8.0F, 0.0F);
		arm_r.addChild(forearm_r);
		

		hand_normal = new ChModelRenderer(this, "hand_normal");
		hand_normal.setRotationPoint(0.0F, 0.0F, 0.0F);
		forearm_r.addChild(hand_normal);
		hand_normal.cubeList.add(new ModelBox(hand_normal, 36, 65, -2.0F, 0.0F, -1.75F, 4, 8, 4, 0.0F, false));

		hand_claw = new ChModelRenderer(this, "hand_claw");
		hand_claw.setRotationPoint(0.0F, 0.0154F, 0.2277F);
		forearm_r.addChild(hand_claw);
		setRotationAngle(hand_claw, 0.0F, 0.0F, -1.5708F);
		hand_claw.cubeList.add(new ModelBox(hand_claw, 85, 74, -7.0F, -2.0F, -2.0F, 7, 4, 4, 0.0F, false));
		hand_claw.cubeList.add(new ModelBox(hand_claw, 107, 74, -8.25F, -2.5F, -2.5F, 3, 5, 5, -0.2F, false));

		h3 = new ChModelRenderer(this, "h3");
		h3.setRotationPoint(-7.0F, 0.0F, 0.0F);
		hand_claw.addChild(h3);
		setRotationAngle(h3, 0.0F, 1.5708F, 0.0F);
		h3.cubeList.add(new ModelBox(h3, 41, 102, -2.0F, -1.0F, -4.25F, 4, 2, 2, 0.0F, false));
		h3.cubeList.add(new ModelBox(h3, 52, 100, -1.5F, -1.0F, -2.25F, 3, 2, 2, 0.0F, false));

		cube_r46 = new ChModelRenderer(this, "cube_r46");
		cube_r46.setRotationPoint(1.75F, 0.0F, -5.1F);
		h3.addChild(cube_r46);
		setRotationAngle(cube_r46, 0.1872F, -0.1841F, -0.8027F);
		cube_r46.cubeList.add(new ModelBox(cube_r46, 35, 101, -0.5F, -0.5F, -1.0F, 1, 1, 2, 0.0F, true));

		cube_r47 = new ChModelRenderer(this, "cube_r47");
		cube_r47.setRotationPoint(0.0F, 0.0F, -5.1F);
		h3.addChild(cube_r47);
		setRotationAngle(cube_r47, 0.0F, 0.0F, 0.7854F);
		cube_r47.cubeList.add(new ModelBox(cube_r47, 53, 97, -0.5F, -0.5F, -1.0F, 1, 1, 2, 0.0F, false));

		cube_r48 = new ChModelRenderer(this, "cube_r48");
		cube_r48.setRotationPoint(-1.75F, 0.0F, -5.1F);
		h3.addChild(cube_r48);
		setRotationAngle(cube_r48, 0.1872F, 0.1841F, 0.8027F);
		cube_r48.cubeList.add(new ModelBox(cube_r48, 17, 99, -0.5F, -0.5F, -1.0F, 1, 1, 2, 0.0F, false));

		cl2 = new ChModelRenderer(this, "cl2");
		cl2.setRotationPoint(0.0F, 0.0F, -6.0F);
		h3.addChild(cl2);
		

		cube_r49 = new ChModelRenderer(this, "cube_r49");
		cube_r49.setRotationPoint(0.5F, 0.9F, -8.1F);
		cl2.addChild(cube_r49);
		setRotationAngle(cube_r49, 0.4363F, 0.0F, 0.0F);
		cube_r49.cubeList.add(new ModelBox(cube_r49, 36, 94, -1.0F, -1.0F, -3.0F, 1, 1, 3, -0.02F, false));

		cube_r50 = new ChModelRenderer(this, "cube_r50");
		cube_r50.setRotationPoint(0.5F, -0.1F, -8.35F);
		cl2.addChild(cube_r50);
		setRotationAngle(cube_r50, 0.7418F, 0.0F, 0.0F);
		cube_r50.cubeList.add(new ModelBox(cube_r50, 27, 96, -1.0F, -1.0F, -3.0F, 1, 1, 3, -0.04F, false));

		cube_r51 = new ChModelRenderer(this, "cube_r51");
		cube_r51.setRotationPoint(4.5F, 1.2F, -9.0F);
		cl2.addChild(cube_r51);
		setRotationAngle(cube_r51, -1.5708F, 1.5272F, -1.5708F);
		cube_r51.cubeList.add(new ModelBox(cube_r51, 12, 97, -8.0F, -1.0F, -5.0F, 8, 1, 1, -0.1F, false));

		cube_r52 = new ChModelRenderer(this, "cube_r52");
		cube_r52.setRotationPoint(0.0F, 0.0F, -5.1F);
		cl2.addChild(cube_r52);
		setRotationAngle(cube_r52, 0.0F, 1.5708F, 0.0F);
		cube_r52.cubeList.add(new ModelBox(cube_r52, 35, 98, -4.0F, -1.0F, -0.5F, 8, 2, 1, 0.0F, false));

		cube_r53 = new ChModelRenderer(this, "cube_r53");
		cube_r53.setRotationPoint(0.0F, 0.0F, -0.1F);
		cl2.addChild(cube_r53);
		setRotationAngle(cube_r53, 0.0F, 0.0F, 0.7854F);
		cube_r53.cubeList.add(new ModelBox(cube_r53, 32, 101, -0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F, false));

		cl1 = new ChModelRenderer(this, "cl1");
		cl1.setRotationPoint(-1.7F, 0.0F, -6.1F);
		h3.addChild(cl1);
		setRotationAngle(cl1, 0.0F, 0.2618F, 0.0F);
		

		cube_r54 = new ChModelRenderer(this, "cube_r54");
		cube_r54.setRotationPoint(0.2153F, 0.9F, -8.0375F);
		cl1.addChild(cube_r54);
		setRotationAngle(cube_r54, 0.4363F, 0.0F, 0.0F);
		cube_r54.cubeList.add(new ModelBox(cube_r54, 36, 94, -1.0F, -1.0F, -3.0F, 1, 1, 3, -0.02F, false));

		cube_r55 = new ChModelRenderer(this, "cube_r55");
		cube_r55.setRotationPoint(0.2153F, -0.1F, -8.2875F);
		cl1.addChild(cube_r55);
		setRotationAngle(cube_r55, 0.7418F, 0.0F, 0.0F);
		cube_r55.cubeList.add(new ModelBox(cube_r55, 27, 96, -1.0F, -1.0F, -3.0F, 1, 1, 3, -0.04F, false));

		cube_r56 = new ChModelRenderer(this, "cube_r56");
		cube_r56.setRotationPoint(4.2153F, 1.2F, -8.9375F);
		cl1.addChild(cube_r56);
		setRotationAngle(cube_r56, -1.5708F, 1.5272F, -1.5708F);
		cube_r56.cubeList.add(new ModelBox(cube_r56, 12, 97, -8.0F, -1.0F, -5.0F, 8, 1, 1, -0.1F, false));

		cube_r57 = new ChModelRenderer(this, "cube_r57");
		cube_r57.setRotationPoint(-0.2847F, 0.0F, -5.0375F);
		cl1.addChild(cube_r57);
		setRotationAngle(cube_r57, 0.0F, 1.5708F, 0.0F);
		cube_r57.cubeList.add(new ModelBox(cube_r57, 35, 98, -4.0F, -1.0F, -0.5F, 8, 2, 1, 0.0F, false));

		cube_r58 = new ChModelRenderer(this, "cube_r58");
		cube_r58.setRotationPoint(-0.2847F, 0.0F, -0.0375F);
		cl1.addChild(cube_r58);
		setRotationAngle(cube_r58, 0.0F, 0.0F, 0.7854F);
		cube_r58.cubeList.add(new ModelBox(cube_r58, 28, 102, -0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F, false));

		cl3 = new ChModelRenderer(this, "cl3");
		cl3.setRotationPoint(2.0F, 0.0F, -6.0F);
		h3.addChild(cl3);
		setRotationAngle(cl3, 0.0F, -0.2618F, 0.0F);
		

		cube_r59 = new ChModelRenderer(this, "cube_r59");
		cube_r59.setRotationPoint(-0.531F, 0.9F, -8.0564F);
		cl3.addChild(cube_r59);
		setRotationAngle(cube_r59, 0.4363F, 0.0F, 0.0F);
		cube_r59.cubeList.add(new ModelBox(cube_r59, 36, 94, 0.0F, -1.0F, -3.0F, 1, 1, 3, -0.02F, true));

		cube_r60 = new ChModelRenderer(this, "cube_r60");
		cube_r60.setRotationPoint(-0.531F, -0.1F, -8.3064F);
		cl3.addChild(cube_r60);
		setRotationAngle(cube_r60, 0.7418F, 0.0F, 0.0F);
		cube_r60.cubeList.add(new ModelBox(cube_r60, 27, 96, 0.0F, -1.0F, -3.0F, 1, 1, 3, -0.04F, true));

		cube_r61 = new ChModelRenderer(this, "cube_r61");
		cube_r61.setRotationPoint(-4.531F, 1.2F, -8.9564F);
		cl3.addChild(cube_r61);
		setRotationAngle(cube_r61, -1.5708F, -1.5272F, 1.5708F);
		cube_r61.cubeList.add(new ModelBox(cube_r61, 12, 97, 0.0F, -1.0F, -5.0F, 8, 1, 1, -0.1F, true));

		cube_r62 = new ChModelRenderer(this, "cube_r62");
		cube_r62.setRotationPoint(-0.031F, 0.0F, -0.0564F);
		cl3.addChild(cube_r62);
		setRotationAngle(cube_r62, 0.0F, 0.0F, -0.7854F);
		cube_r62.cubeList.add(new ModelBox(cube_r62, 23, 101, -0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F, true));

		cube_r63 = new ChModelRenderer(this, "cube_r63");
		cube_r63.setRotationPoint(-0.031F, 0.0F, -5.0564F);
		cl3.addChild(cube_r63);
		setRotationAngle(cube_r63, 0.0F, -1.5708F, 0.0F);
		cube_r63.cubeList.add(new ModelBox(cube_r63, 35, 98, -4.0F, -1.0F, -0.5F, 8, 2, 1, 0.0F, true));

		hand_hmg = new ChModelRenderer(this, "hand_hmg");
		hand_hmg.setRotationPoint(0.0F, 0.0F, 0.0F);
		forearm_r.addChild(hand_hmg);
		

		cube_r64 = new ChModelRenderer(this, "cube_r64");
		cube_r64.setRotationPoint(1.0F, 6.0154F, 0.2277F);
		hand_hmg.addChild(cube_r64);
		setRotationAngle(cube_r64, 0.0F, 0.0F, -1.5708F);
		cube_r64.cubeList.add(new ModelBox(cube_r64, 85, 74, -1.0F, -3.0F, -2.0F, 7, 4, 4, 0.0F, false));

		cube_r65 = new ChModelRenderer(this, "cube_r65");
		cube_r65.setRotationPoint(1.5F, 7.2654F, -0.2723F);
		hand_hmg.addChild(cube_r65);
		setRotationAngle(cube_r65, 0.0F, 0.0F, -1.5708F);
		cube_r65.cubeList.add(new ModelBox(cube_r65, 107, 74, -1.0F, -4.0F, -2.0F, 3, 5, 5, -0.2F, false));

		hmg = new ChModelRenderer(this, "hmg");
		hmg.setRotationPoint(0.0F, 8.0654F, 0.1777F);
		hand_hmg.addChild(hmg);
		setRotationAngle(hmg, 0.0F, -1.5708F, -1.5708F);
		

		head = new ChModelRenderer(this, "head");
		head.setRotationPoint(0.0F, -6.4678F, 2.1659F);
		chest.addChild(head);
		setRotationAngle(head, -0.2618F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 17, -4.0F, -8.0237F, -3.8512F, 8, 7, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 87, 19, -4.0F, -7.9301F, 0.0684F, 8, 7, 4, 0.2F, false));
		head.cubeList.add(new ModelBox(head, 88, 39, -4.0F, -8.25F, -3.75F, 8, 9, 8, 0.4F, false));
		head.cubeList.add(new ModelBox(head, 88, 56, -4.5F, -8.5F, -4.25F, 9, 9, 9, 0.1F, false));

		cube_r66 = new ChModelRenderer(this, "cube_r66");
		cube_r66.setRotationPoint(-3.0F, 0.4F, 0.1F);
		head.addChild(cube_r66);
		setRotationAngle(cube_r66, 0.0436F, 0.0F, 0.0F);
		cube_r66.cubeList.add(new ModelBox(cube_r66, 32, 31, -1.0002F, -1.2456F, -3.8712F, 8, 1, 8, 0.12F, false));

		cube_r67 = new ChModelRenderer(this, "cube_r67");
		cube_r67.setRotationPoint(-3.0F, 0.3F, 0.1F);
		head.addChild(cube_r67);
		setRotationAngle(cube_r67, 0.0436F, 0.0019F, -0.0436F);
		cube_r67.cubeList.add(new ModelBox(cube_r67, 0, 43, -1.0002F, -1.9956F, -3.9001F, 4, 1, 8, 0.1F, false));

		cube_r68 = new ChModelRenderer(this, "cube_r68");
		cube_r68.setRotationPoint(3.0F, 0.3F, 0.1F);
		head.addChild(cube_r68);
		setRotationAngle(cube_r68, 0.0436F, -0.0019F, 0.0436F);
		cube_r68.cubeList.add(new ModelBox(cube_r68, 26, 40, -2.9998F, -1.9956F, -3.9001F, 4, 1, 8, 0.1F, false));

		faces = new ChModelRenderer(this, "faces");
		faces.setRotationPoint(0.0F, -3.817F, -5.8699F);
		head.addChild(faces);
		

		x = new ChModelRenderer(this, "x");
		x.setRotationPoint(0.0F, 0.85F, 1.45F);
		faces.addChild(x);
		setRotationAngle(x, 1.5708F, 0.0F, 0.0F);
		

		cube_r69 = new ChModelRenderer(this, "cube_r69");
		cube_r69.setRotationPoint(0.0F, 0.0F, 0.0F);
		x.addChild(cube_r69);
		setRotationAngle(cube_r69, 0.0F, -0.3491F, 0.0F);
		cube_r69.cubeList.add(new ModelBox(cube_r69, 0, 90, -4.0F, 0.0F, -1.0F, 8, 1, 2, -0.5F, false));

		cube_r70 = new ChModelRenderer(this, "cube_r70");
		cube_r70.setRotationPoint(0.0F, 0.0F, 0.0F);
		x.addChild(cube_r70);
		setRotationAngle(cube_r70, 0.0F, 0.3491F, 0.0F);
		cube_r70.cubeList.add(new ModelBox(cube_r70, 0, 88, -4.0F, 0.0F, -1.0F, 8, 1, 2, -0.5F, false));

		hollow = new ChModelRenderer(this, "hollow");
		hollow.setRotationPoint(0.0F, 0.0F, 0.0F);
		faces.addChild(hollow);
		

		cube_r71 = new ChModelRenderer(this, "cube_r71");
		cube_r71.setRotationPoint(0.0F, 0.0F, 4.0F);
		hollow.addChild(cube_r71);
		setRotationAngle(cube_r71, -1.5708F, 0.0F, 3.1416F);
		cube_r71.cubeList.add(new ModelBox(cube_r71, 177, 32, -10.0F, -5.0F, -9.0F, 20, 13, 19, -6.0F, false));

		normal = new ChModelRenderer(this, "normal");
		normal.setRotationPoint(0.0F, 0.0F, 0.0F);
		faces.addChild(normal);
		

		cube_r72 = new ChModelRenderer(this, "cube_r72");
		cube_r72.setRotationPoint(0.0F, 0.0F, 4.0F);
		normal.addChild(cube_r72);
		setRotationAngle(cube_r72, -1.5708F, 0.0F, -3.1416F);
		cube_r72.cubeList.add(new ModelBox(cube_r72, 177, 0, -10.0F, -5.0F, -9.0F, 20, 13, 19, -6.0F, false));

		error = new ChModelRenderer(this, "error");
		error.setRotationPoint(0.0F, 0.0F, 0.0F);
		faces.addChild(error);
		

		cube_r73 = new ChModelRenderer(this, "cube_r73");
		cube_r73.setRotationPoint(0.0F, 0.0F, 4.0F);
		error.addChild(cube_r73);
		setRotationAngle(cube_r73, -1.5708F, 0.0F, -3.1416F);
		cube_r73.cubeList.add(new ModelBox(cube_r73, 177, 64, -10.0F, -5.0F, -9.0F, 20, 13, 19, -6.0F, false));

		mouth = new ChModelRenderer(this, "mouth");
		mouth.setRotationPoint(0.0F, -0.6801F, -3.8316F);
		head.addChild(mouth);
		

		open = new ChModelRenderer(this, "open");
		open.setRotationPoint(0.0F, -0.2869F, 0.7617F);
		mouth.addChild(open);
		

		cube_r74 = new ChModelRenderer(this, "cube_r74");
		cube_r74.setRotationPoint(0.0F, 0.0F, 0.2F);
		open.addChild(cube_r74);
		setRotationAngle(cube_r74, 1.6232F, 0.0F, 0.0F);
		cube_r74.cubeList.add(new ModelBox(cube_r74, 40, 128, -11.0F, -8.0F, -11.0F, 22, 14, 22, -6.9F, false));

		cube_r75 = new ChModelRenderer(this, "cube_r75");
		cube_r75.setRotationPoint(0.0F, -0.05F, 0.3F);
		open.addChild(cube_r75);
		setRotationAngle(cube_r75, 1.6232F, 0.0F, 0.0F);
		cube_r75.cubeList.add(new ModelBox(cube_r75, 40, 93, -11.0F, -8.0F, -11.0F, 22, 14, 22, -6.8F, false));

		cat = new ChModelRenderer(this, "cat");
		cat.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth.addChild(cat);
		

		cube_r76 = new ChModelRenderer(this, "cube_r76");
		cube_r76.setRotationPoint(0.0F, 0.0F, 0.0F);
		cat.addChild(cube_r76);
		setRotationAngle(cube_r76, 0.0436F, 0.0F, 0.0F);
		cube_r76.cubeList.add(new ModelBox(cube_r76, 86, 7, -3.5F, -3.0F, -2.0F, 7, 6, 4, -1.8F, false));

		sad = new ChModelRenderer(this, "sad");
		sad.setRotationPoint(0.0F, 0.0F, 0.0F);
		mouth.addChild(sad);
		

		cube_r77 = new ChModelRenderer(this, "cube_r77");
		cube_r77.setRotationPoint(0.0F, 0.0F, 0.0F);
		sad.addChild(cube_r77);
		setRotationAngle(cube_r77, 0.0436F, 0.0F, 0.0F);
		cube_r77.cubeList.add(new ModelBox(cube_r77, 106, 105, -3.5F, -3.0F, -2.0F, 7, 6, 4, -1.8F, false));

		bone = new ChModelRenderer(this, "bone");
		bone.setRotationPoint(0.0F, 26.2546F, -7.3992F);
		chest.addChild(bone);
		setRotationAngle(bone, -0.2618F, 0.0F, 0.0F);
		

		belly_full = new ChModelRenderer(this, "belly_full");
		belly_full.setRotationPoint(-1.0F, -3.5F, -1.2F);
		mid.addChild(belly_full);
		

		cube_r78 = new ChModelRenderer(this, "cube_r78");
		cube_r78.setRotationPoint(0.5F, 8.0F, -3.5F);
		belly_full.addChild(cube_r78);
		setRotationAngle(cube_r78, 0.1745F, 0.0F, 0.0F);
		cube_r78.cubeList.add(new ModelBox(cube_r78, 0, 0, -5.0F, -8.0F, -5.0F, 10, 7, 10, 0.0F, false));

		cube_r79 = new ChModelRenderer(this, "cube_r79");
		cube_r79.setRotationPoint(0.0F, 1.25F, -5.0F);
		belly_full.addChild(cube_r79);
		setRotationAngle(cube_r79, -0.1309F, 0.0F, 0.0F);
		cube_r79.cubeList.add(new ModelBox(cube_r79, 32, 17, -4.0F, -5.0F, -4.0F, 9, 6, 8, 0.0F, false));
		postConstruct();
	}

	public void postConstruct()
	{
		if (hmgmodel == null)
		{
			hmgmodel = new ModelHMGDD();
		} 
		hmg.addChild(ModelHMGDD.HMG);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		belly_full.isHidden = true;
		hollow.isHidden = true;
		root.render(f5);
		//add a conditional that hides/unhides correct weapon based on currently held item
	}
	static ResourceLocation handTexture = ResourcesHelper.GetEntityTexture("dd.png");
	static ResourceLocation bloomHandTexture = ResourcesHelper.GetEntityTexture("dd_bloom.png");
	public void renderRightArm(EntityDD dd)
	{
		GlStateManager.pushMatrix();
		TextureManager tex = Minecraft.getMinecraft().renderEngine;
		if (BloomQueue.cPass == BloomQueue.Pass.Normal) tex.bindTexture(handTexture); 	
		else tex.bindTexture(bloomHandTexture);
		GlStateManager.pushMatrix();
		
		arm_r.render(0.0625f);

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
	}

	public static AnimationContainer anims;
	public static AnimationData walk;
	public static AnimationData idle;
	public static AnimationData sneak;
	public static AnimationData sneak_walk;
	public static AnimationData fp_claws;
	public static AnimationData claw;
	public static AnimationData hit;
	public static AnimationData dash;
	public static AnimationData hover;
	public static AnimationData dash_claw;
	public static AnimationData error_sit;
	public static AnimationData unfold_wings;
	public static AnimationData fold_wings;
	public static AnimationData take_off;
	public static AnimationContainer handanims;
	public static AnimationData claws_idle;
	public static AnimationData claws_swing;
	public static AnimationData aim_idle;
	public static AnimationData hit_fp;
	public static AnimationData mine;

	public static void LoadAnims()
	{
		MD.logger.info("Loading anims...");
		try
		{
			anims = AnimLoader.parseAt("md");
			walk = anims.animations.get("walk");
			idle = anims.animations.get("idle");
			sneak = anims.animations.get("sneak");
			sneak_walk = anims.animations.get("sneak_walk");
			fp_claws = anims.animations.get("fp_claw");
			claw = anims.animations.get("claw");
			hit = anims.animations.get("hit");
			dash = anims.animations.get("dash");
			dash_claw = anims.animations.get("dash_claw");
			error_sit = anims.animations.get("error");
			hover = anims.animations.get("hover");
			unfold_wings = anims.animations.get("unfold_wings"); 
			fold_wings = anims.animations.get("fold_wings");
			take_off = anims.animations.get("take_off");

			handanims = AnimLoader.parseAt("md_arm");
			claws_idle = handanims.animations.get("idle");
			claws_swing = handanims.animations.get("swing");
			aim_idle = handanims.animations.get("aim");
			hit_fp = handanims.animations.get("hit");
			mine = handanims.animations.get("mine");
		}
		catch (IOException e)
		{
			MD.logger.error("Failed to load DD animations");
		}
		
	}

	protected void ToggleVisible(EntityDD dd)
	{
		hand_normal.isHidden = true;
		hand_claw.isHidden = true;
		hand_hmg.isHidden = false;

		wing_base_left.isHidden = !dd.GetFlightController().areWingsOut();
		wing_base_right.isHidden = !dd.GetFlightController().areWingsOut();
	}
	protected void ToggleVisiblePlayer(EntityDDMorph dd)
	{
		InnateWeapon<DDMorph, ModelEntityDD> wep = dd.morph.GetWeapon(EnumHand.MAIN_HAND);
		hand_normal.isHidden = true;
		hand_claw.isHidden = true;
		hand_hmg.isHidden = true;

		error.isHidden = !dd.morph.statsDD.criticalState;
		x.isHidden = !dd.morph.IsCombatMode() || !error.isHidden;
		normal.isHidden = !x.isHidden || !error.isHidden;
		sad.isHidden = !dd.morph.statsDD.criticalState;
		cat.isHidden = normal.isHidden || !sad.isHidden;
		open.isHidden = x.isHidden || !sad.isHidden;
		wep.OnRenderPre(this, dd.swingProgress * GraphicsUtils.GetPartialTick() + (1f - GraphicsUtils.GetPartialTick()) * dd.prevSwingProgress);
		boolean wings_hidden = !dd.GetFlightController().areWingsOut() && 
			!dd.animator.IsActive(ModelEntityDD.unfold_wings) && !dd.animator.IsActive(ModelEntityDD.fold_wings);
		wing_base_left.isHidden = wings_hidden;
		wing_base_right.isHidden = wings_hidden;
	}
	@Override 
	public void SelectAnims(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		EntityDD dd = (EntityDD)entityIn;
		float interp;
		if (dd.getStats().criticalState)
		{
			dd.animator.ContinueAnim(error_sit, 1f, 1f);
		}
		else if (dd.GetFlightController().isFlying())
		{
			dd.animator.ContinueAnim(hover, 0.5f, 1f);
		}
		else if (dd.isSneaking())
		{
			interp = MathHelper.clamp((limbSwingAmount-0.002f) / 0.15f,0f,1f);
			dd.animator.ContinueAnim(sneak_walk, limbSwingAmount*4f, interp);
			dd.animator.ContinueAnim(sneak, 1f, 1f-interp);
		}
		else
		{
			interp = MathHelper.clamp((limbSwingAmount-0.004f) / 0.4f,0f,1f);
			if (dd.isDashing()) dd.animator.ContinueAnim(dash, limbSwingAmount*2f, interp);
			else dd.animator.ContinueAnim(walk, limbSwingAmount*2f, interp);
			dd.animator.ContinueAnim(idle, 1f, 1f-interp);
		}
	}
	@Override
	public void animate(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		float eyaw = entityIn.prevRotationYaw - (entityIn.prevRotationYaw - entityIn.rotationYaw) * GraphicsUtils.GetPartialTick();
		EntityDD dd = (EntityDD)entityIn;
		EntityDDMorph ddm = null;
		if (dd instanceof EntityDDMorph) ddm = (EntityDDMorph)dd;

		if (ddm != null)
			ToggleVisiblePlayer(ddm);
		else
			ToggleVisible(dd);

		dd.GetFlightController().Animate(this);
		
		if (dd.isAiming(EnumHand.MAIN_HAND))
		{
			Quaternion q = Transforms.FromEulerXYZ(headPitch*math.DegToRad - (float)Math.PI/2f, netHeadYaw*math.DegToRad, 0);
			Transforms.SetGlobalRotation(arm_r, q, 0);
			forearm_r.rotateAngleX = 0;
			forearm_r.rotateAngleY = 0;
			forearm_r.rotateAngleZ = 0;
		}
		netHeadYaw = Transforms.SimplifyAngle(netHeadYaw);
		Transforms.SetGlobalRotation(head, Transforms.FromEulerXYZ((headPitch + Math.abs(netHeadYaw) * 0.3f) * math.DegToRad, netHeadYaw * math.DegToRad, netHeadYaw * 0.4f * math.DegToRad), 0);
    	if (ddm != null)
		{
			InnateWeapon<DDMorph, ModelEntityDD> wep = ddm.morph.GetWeapon(EnumHand.MAIN_HAND);
			wep.OnRenderPost();
		}
	}

	
}