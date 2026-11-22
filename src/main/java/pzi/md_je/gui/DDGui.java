package pzi.md_je.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.OverheatLevel;
import pzi.md_je.entities.DD.ModelEntityDD;
import pzi.md_je.entities.DD.StatsDD;
import pzi.md_je.generic.HeatManager;
import pzi.md_je.generic.weapons.InnateWeapon;
import pzi.md_je.loading.ResourcesHelper;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;
import pzi.md_je.rendering.GraphicsUtils;
import pzi.md_je.util.rand;

public class DDGui extends PrettyGUI
{
    public static DDGui instance = new DDGui();
    static ResourceLocation left_bar = ResourcesHelper.GetGuiTexture("dd_gui_l.png");
    static ResourceLocation right_bar = ResourcesHelper.GetGuiTexture("dd_gui_r.png");
    static ResourceLocation top_bar = ResourcesHelper.GetGuiTexture("dd_top_gui.png");
    static ResourceLocation droplet = ResourcesHelper.GetGuiTexture("droplet.png");
    static ResourceLocation temperature = ResourcesHelper.GetGuiTexture("temperature.png");
    static ResourceLocation warning = ResourcesHelper.GetGuiTexture("warning.png");
    static ResourceLocation warning_oil = ResourcesHelper.GetGuiTexture("warning_oil.png");
    static ResourceLocation crshr = ResourcesHelper.GetGuiTexture("crosshair_small.png");
    static ResourceLocation f_crshr = ResourcesHelper.GetGuiTexture("feed_crhr.png");
    static ResourceLocation hp_bar = ResourcesHelper.GetGuiTexture("dd_hp_bar.png");
    static ResourceLocation hp_bar_empty = ResourcesHelper.GetGuiTexture("dd_hp_empty.png");
    static ResourceLocation hp_icon1 = ResourcesHelper.GetGuiTexture("dd_core_1.png");
    static ResourceLocation hp_icon2 = ResourcesHelper.GetGuiTexture("dd_core_2.png");
    static ResourceLocation hp_icon3 = ResourcesHelper.GetGuiTexture("dd_core_3.png");
    static ResourceLocation hp_icon4 = ResourcesHelper.GetGuiTexture("dd_core_4.png");
    static ResourceLocation hp_icon1_d = ResourcesHelper.GetGuiTexture("dd_core_1_damaged.png");
    static ResourceLocation hp_icon2_d = ResourcesHelper.GetGuiTexture("dd_core_2_damaged.png");
    static ResourceLocation hp_icon3_d = ResourcesHelper.GetGuiTexture("dd_core_3_damaged.png");
    static ResourceLocation hp_icon4_d = ResourcesHelper.GetGuiTexture("dd_core_4_damaged.png");
    static List<ResourceLocation> hp_icons;
    static ResourceLocation solv_err = ResourcesHelper.GetGuiTexture("solv_err.png");
    static ResourceLocation crit_err = ResourcesHelper.GetGuiTexture("dmg_err.png");
    static ResourceLocation motor_err = ResourcesHelper.GetGuiTexture("motor_err.png");
    static GuiRect solv_rect;
    static List<GuiRect> error_rects;
    static 
    {
        hp_icons = new ArrayList<>();
        hp_icons.add(hp_icon1);
        hp_icons.add(hp_icon2);
        hp_icons.add(hp_icon3);
        hp_icons.add(hp_icon4);
        hp_icons.add(hp_icon1_d);
        hp_icons.add(hp_icon2_d);
        hp_icons.add(hp_icon3_d);
        hp_icons.add(hp_icon4_d);

        error_rects = new ArrayList<>();
        solv_rect = instance.new GuiRect(40f, solv_err, 80, 50, true);
        error_rects.add(instance.new GuiRect(65f, crit_err, 100, 40, false));
        error_rects.add(instance.new GuiRect(60f, motor_err, 70, 27, false));
    }

    public float deltaX = 0;
    public float deltaY = 0;
    float gainCoef = 0.001f;
    float maxOffset = 0.015f;
    float prevCamPitch = 0f;
    float prevCamYaw = 0f;
    public boolean doDrawCrosshair;
    public static int baseColor = 0xffdd1b;
    public static int baseWhiteOverHeatColor = baseColor;
    public static int baseYellowOverHeatColor = 0xff3000;
    public static int baseRedOverHeatColor = 0xff0400;
    public static int baseBloodColor = 0xe00804;
    public static float baseRed = 1f;
    public static float baseGreen = 0.87f;
    public static float baseBlue = 0.1f;
    public float prevTime;
    public float dt;
    public float pulsetimer;
    public int curPulse;
    public float err_cd;
    protected List<GuiRect> rects = new ArrayList<>();

    protected static DecimalFormat format2 = new DecimalFormat("0.00");
    protected static DecimalFormat format1 = new DecimalFormat("0.0");
    protected static DecimalFormat format0 = new DecimalFormat("0");

    @Override 
    public void Draw()
    {
        Entity cameraEntity = Minecraft.getMinecraft().getRenderViewEntity();
        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        Minecraft mc = Minecraft.getMinecraft();
        
        deltaX = mc.player.prevRenderArmYaw + (mc.player.renderArmYaw - mc.player.prevRenderArmYaw) * partialTicks;
	    deltaY = mc.player.prevRenderArmPitch + (mc.player.renderArmPitch - mc.player.prevRenderArmPitch) * partialTicks;
        deltaX -= mc.player.rotationYaw;
        deltaY -= mc.player.rotationPitch;
        deltaX *= gainCoef;
        deltaY *= gainCoef;
        deltaX = MathHelper.clamp(deltaX, -maxOffset, maxOffset);
        deltaY = MathHelper.clamp(deltaY, -maxOffset, maxOffset);
        
        TextureManager tex = Minecraft.getMinecraft().renderEngine;

        EntityPlayer pl = Minecraft.getMinecraft().player;
        dt = pl.ticksExisted + partialTicks - prevTime;
        if (dt < 0) dt = 0;
        IMorph m = PlayerMorphs.GetMorph(pl);
        assert(m != null && m.GetMorph() == Morphs.DD);
        DDMorph dm = (DDMorph)m;

        if (dm.IsCombatMode()) DrawSideBars(tex, dm);

        DrawTopBar(tex, dm);
        DrawHP(tex, dm);
        DrawStats(tex, dm);
        DrawWarnings(tex, dm);
        DrawPersistentRects(tex, dm);
        if (dm.statsDD.criticalState) DrawErrors(tex, dm);
        if (doDrawCrosshair) DrawCrossHair(dm);
        if (doDrawCrosshair) doDrawCrosshair = false;
        if (dm.GetWeapon(EnumHand.MAIN_HAND).GetId() != "Empty")
        {
            dm.GetWeapon(EnumHand.MAIN_HAND).DrawGUI(this);
        }
        prevTime = pl.ticksExisted + partialTicks;
    }  
    protected void DrawStats(TextureManager tex, DDMorph dm)
    {
        GraphicsUtils.GlSetColor(GetColor(dm), 1f);
        
        tex.bindTexture(temperature);
        drawTexRect(0.1f, 0.1f, 30, 0, 1, 1, 11, 11);
        drawString(format1.format(dm.GetHeatManager().temp), 0.1f, 0.1f, 61, 1, 4, GetColor(dm), false);
        
        tex.bindTexture(droplet);
        drawTexRect(0.1f, 0.1f, 30, 60, 1, 1, 11, 11);
        drawString(format0.format(dm.GetCurrentOil()*100f/StatsDD.maxOil)+"%", 0.1f, 0.1f, 60, 61, 4, GetColor(dm), false);

        if (dm.GetStoredBlood() > 0.01f)
        {
            GraphicsUtils.GlSetColor(baseBloodColor, 1f);
            tex.bindTexture(droplet);
            drawTexRect(0.1f, 0.1f, 30, 120, 1, 1, 11, 11);
            drawString(format0.format(dm.GetStoredBlood()*100f/StatsDD.maxBlood)+"%", 0.1f, 0.1f, 60, 121, 4, baseBloodColor, false);
        }
    }
    protected void DrawHP(TextureManager tex, DDMorph dm)
    {   
        int clr = GetColor(dm);
        float hp = dm.owner.getHealth() / dm.owner.getMaxHealth();
        if (hp <= 0.3f) clr = 0xff1000;
        float brightness = dm.statsDD.criticalState ? 0.7f : 1f;
        GraphicsUtils.GlSetColor(clr, brightness);
        int bar_len = 2 + (int)(78 * hp);
        
        float speed = 1/15f;
        speed *= (2f - hp);
        if (hp <= 0.3f) speed *= 1.3f;
        if (dm.statsDD.criticalState) speed *= rand.Next() * 2f;
        if (pulsetimer < 0f) pulsetimer = 0f;
        pulsetimer += dt * speed;
        if (pulsetimer >= 1f) 
        {
            pulsetimer = 0f;
            curPulse = (curPulse + 1) % 4;
        }
        int ticks = curPulse;
        if (dm.statsDD.criticalState) ticks += 4;

        tex.bindTexture(hp_icons.get(ticks));
        drawTexRect(0, 1, 70, -60, 1, 1, 20, 20);
        tex.bindTexture(hp_bar_empty);
        drawTexRectUncentered(0f, 1f, 130, -84, 80, 12, 80, 12);
        tex.bindTexture(hp_bar);
        drawTexRectUncentered(0f, 1f, 130, -84, bar_len, 12,80, 12);
    }
    protected void DrawSideBars(TextureManager tex, DDMorph dm)
    {
        GraphicsUtils.GlSetColor(GetColor(dm), 1f);
        tex.bindTexture(left_bar);
        drawTexRect(0f, 0.5f, 60, 0, 1, 1, 24, 120);
        tex.bindTexture(right_bar);
        drawTexRect(1f, 0.5f, -60, 0, 1, 1, 24, 120);
    }
    protected void DrawTopBar(TextureManager tex, DDMorph dm)
    {
        GraphicsUtils.GlSetColor(GetColor(dm), 1f);
        tex.bindTexture(top_bar);
        drawTexRect(0.5f, 0f, 0, -15, 1, 1, 240, 30);
    }
    protected void DrawWarnings(TextureManager tex, DDMorph dm)
    {
        HeatManager h = dm.GetHeatManager();
        if (h.overheat != OverheatLevel.None)
        {
            int clr = h.overheat == OverheatLevel.White ? baseWhiteOverHeatColor : 
            (h.overheat == OverheatLevel.Yellow ? baseYellowOverHeatColor : baseRedOverHeatColor);
            GraphicsUtils.GlSetColor(clr, 1f);
            tex.bindTexture(warning);
            drawTexRect(0.9f, 0.9f, 0, 0, 1, 1, 32, 32);
        }
        float curoil = dm.GetCurrentOil();
        if (curoil < StatsDD.whiteOilLevel)
        {
            int clr = curoil < StatsDD.redOilLevel ? baseRedOverHeatColor : 
            (curoil < StatsDD.yellowOilLevel ? baseYellowOverHeatColor : baseWhiteOverHeatColor);
            GraphicsUtils.GlSetColor(clr, 1f);
            tex.bindTexture(warning_oil);
            drawTexRect(0.9f, 0.9f, -134, 0, 1, 1, 32, 32);
        }
    }
    protected void DrawErrors(TextureManager tex, DDMorph dm)
    {
        err_cd -= dt;
        if (err_cd <= 0)
        {
            float hp = 1f - (dm.owner.getHealth() / dm.owner.getMaxHealth());
            float tmp = MathHelper.clamp((dm.statsDD.heatManager.coreTemp - StatsDD.targetCoreTemp) / StatsDD.OHRedStart,0,1);
            err_cd = 18f - hp * 12f - tmp * 12f - hp * tmp * 6f;
            if (err_cd < 2f) err_cd = 2f;
            float solv_ch = tmp * tmp * 0.4f;
            float d = (1f-hp) * 0.8f;
            float x = (rand.Next() * 2f - 1f);
            x = x + d * Math.signum(x);
            x = x / (1f + d);
            x = (x + 1f) / 2f;
            float y = (rand.Next() * 2f - 1f);
            y = y + d * Math.signum(y);
            y = y / (1f + d);
            y = (y + 1f) / 2f;
            if (rand.Next() < solv_ch) rects.add(new GuiRect(solv_rect, x, y));
            else if (rand.Next() < 0.5f) rects.add(new GuiRect(error_rects.get(0), x, y));
            else rects.add(new GuiRect(error_rects.get(1), x, y));
        }   
    }

    public void DrawCrossHair(DDMorph m)
    {
        TextureManager tex = Minecraft.getMinecraft().renderEngine;
        int texsize = 7;
        InnateWeapon<DDMorph, ModelEntityDD> wep = m.GetWeapon(EnumHand.MAIN_HAND);
        if (m.CanUseFeed()) tex.bindTexture(f_crshr);
        else if (wep.CrosshairTex() != null)
        {
            tex.bindTexture(wep.CrosshairTex());
            texsize = wep.CrosshairSize();
        } 
        else tex.bindTexture(crshr);
        
        GraphicsUtils.GlSetColor(GetColor(m), 1f);
        super.drawTexRect(0.5f, 0.5f, 0, 0, 1, 1, texsize, texsize);

        StartDrawBloom();

        GraphicsUtils.GlSetColor(GetColor(m), 0.6f);
        super.drawTexRect(0.5f, 0.5f, 0, 0, 1, 1, texsize, texsize);

        EndDrawBloom();
    }
    @Override 
    public void drawString(String str, float xanch, float yanch, float xoffs, float yoffs, float text_scale, int color, boolean centered)
    {
        super.drawString(str, xanch+deltaX, yanch+deltaY, xoffs, yoffs, text_scale, color, centered);
        StartDrawBloom();
        super.drawString(str, xanch+deltaX, yanch+deltaY, xoffs, yoffs, text_scale, color, centered);
        EndDrawBloom();
    }
    @Override 
    public void drawTexRect(float xanch, float yanch,int xoffs, int yoffs, float u, float v, int width, int height)
    {
        super.drawTexRect(xanch + deltaX, yanch + deltaY, xoffs, yoffs, u, v, width, height);
        StartDrawBloom();
        super.drawTexRect(xanch + deltaX, yanch + deltaY, xoffs, yoffs, u, v, width, height);
        EndDrawBloom();
    }
    @Override 
    public void drawTexRectUncentered(float xanch, float yanch,int xoffs, int yoffs, int u, int v, int width, int height)
    {
        super.drawTexRectUncentered(xanch + deltaX, yanch + deltaY, xoffs, yoffs, u, v, width, height);
        StartDrawBloom();
        super.drawTexRectUncentered(xanch + deltaX, yanch + deltaY, xoffs, yoffs, u, v, width, height);
        EndDrawBloom();
    }
    protected int GetColor(DDMorph m)
    {
        if (m.GetHeatManager().overheat != OverheatLevel.Red) return baseColor;
        return baseRedOverHeatColor;
    }

    protected class GuiRect
    {
        public float timeleft;
        public float xanch;
        public float yanch;
        public ResourceLocation tex;
        public int texW;
        public int texH;
        public boolean shaky; // for solver error
        public GuiRect(float t, ResourceLocation te, int w, int h, boolean sh)
        {
            timeleft = t;
            tex = te;
            texW = w;
            texH = h;
            shaky = sh;
        }
        public GuiRect(GuiRect r, float x, float y)
        {
            timeleft = r.timeleft;
            xanch = x;
            yanch = y;
            tex = r.tex;
            texW = r.texW;
            texH = r.texH;
            shaky = r.shaky;
        }
    }
    public void DrawPersistentRects(TextureManager tex, DDMorph dm)
    {
        for (GuiRect r : rects)
        {
            if (r != null) drawGuiRect(r, tex, dm);
        }
        rects.removeIf((GuiRect r) -> {return r == null || r.timeleft <= 0f;});
    }
    protected void drawGuiRect(GuiRect rect, TextureManager tex, DDMorph dm)
    {
        tex.bindTexture(rect.tex);
        float x = rect.xanch;
        float y = rect.yanch;
        float al = Math.min(1f, rect.timeleft / 12f);
        if (rect.shaky)
        {
            GraphicsUtils.GlSetColor(baseColor, 1f, al);
            x += rand.Next() * 0.02f - 0.01f;
            y += rand.Next() * 0.02f - 0.01f;
        }
        else 
        {
            GraphicsUtils.GlSetColor(GetColor(dm), 1f, al);
        }
        drawTexRect(x, y, 0, 0, 1f, 1f, rect.texW, rect.texH);
        rect.timeleft -= dt;
    }
    @Override 
    public void CleanUp()
    {
        prevTime = 0f;
        rects.clear();
    }
    @Override 
    public boolean HasGuiGlow()
    {
        return true;
    }
}
