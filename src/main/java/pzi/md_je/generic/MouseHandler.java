package pzi.md_je.generic;

import net.minecraft.client.Minecraft;
import pzi.md_je.MD;
import pzi.md_je.player.PlayerMorphs;

public class MouseHandler 
{
    public static boolean lmbHeld;
    public static boolean rmbHeld;
    public static void Update(int keycode, boolean down)
    {
        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode() == keycode)
        {
            if (down)
            {
                if (!lmbHeld)
                {
                    PlayerMorphs.OnMouseClick(0);
                }
                lmbHeld = true;
            }
            else
            {
                lmbHeld = false;
            }
        }

        if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode() == keycode)
        {
            if (down)
            {
                if (!rmbHeld)
                {
                    PlayerMorphs.OnMouseClick(1);
                }
                rmbHeld = true;
            }
            else
            {
                rmbHeld = false;
            }
        }
    }
}
