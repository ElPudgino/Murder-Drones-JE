package pzi.md_je.player;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;
import pzi.md_je.MD;
import pzi.md_je.capability.DDMorph;
import pzi.md_je.capability.IMorph;
import pzi.md_je.capability.IMorphCap;
import pzi.md_je.capability.MorphCapProvider;
import pzi.md_je.capability.MorphCapStorage;
import pzi.md_je.entities.DD.EntityDD;
import pzi.md_je.entities.DD.StatsDD.AbilityType;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CMorphPacket;
import pzi.md_je.network.to_client.CMorphSyncPacket;
import pzi.md_je.network.to_server.ability.dd.SDDCombatModePacket;
import pzi.md_je.network.to_server.ability.dd.SDDTakeOffPacket;
import pzi.md_je.network.to_server.ability.dd.SDDToggleWingsPacket;
import pzi.md_je.network.to_server.ability.dd.SDDWeaponTogglePacket;

public class PlayerMorphs 
{
    public static IMorph GetMorph(EntityPlayer pl)
    {
        IMorphCap cap = pl.getCapability(MorphCapProvider.MORPH_CAP, null);
        if (cap == null) return null;
        return cap.GetMorph();
    }
    public static void SetMorph(EntityPlayer pl, Morphs morph)
    {
        pl.getCapability(MorphCapProvider.MORPH_CAP, null).SetMorph(morph);
    }
    public static void CopyMorph(EntityPlayer orig, EntityPlayer n)
    {
        IMorphCap cap = orig.getCapability(MorphCapProvider.MORPH_CAP, null);
        IMorphCap cap_n = n.getCapability(MorphCapProvider.MORPH_CAP, null);
        if (cap == null)
        {
            MD.logger.error("Original player had no morph cap when cloning");
            return;
        }
        if (cap_n == null)
        {
            MD.logger.error("New player had no morph cap when cloning");
            return;
        }
        MorphCapProvider.MORPH_CAP.getStorage().readNBT(MorphCapProvider.MORPH_CAP, cap_n, null, 
            MorphCapProvider.MORPH_CAP.getStorage().writeNBT(MorphCapProvider.MORPH_CAP, cap, null)
        );
    }
    public static void SetMorphServer(EntityPlayer pl, Morphs morph)
    {
        SetMorph(pl, morph);
        PacketHandler.INSTANCE.sendToAll(new CMorphPacket(pl, true));
    }
    public static CMorphSyncPacket GetFullSyncPacket()
    {
        List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        return new CMorphSyncPacket(players);
    }
    @SideOnly(Side.CLIENT)
    public static void OnMouseClick(int type)
    {   
        IMorph m = PlayerMorphs.GetMorph(Minecraft.getMinecraft().player);
        if (m != null) 
        {
            if (type == 0) m.OnLeftClick();
            else if (type == 1) m.OnRightClick();
        }
    }
    public static void TryUseAbility(AbilityType ab, EntityPlayer pl)
    {
        IMorph m = PlayerMorphs.GetMorph(pl);
        if (m == null) return;
        if (m.GetMorph() == Morphs.DD)
        {
            DDMorph dm = (DDMorph)m;
            if (ab == AbilityType.Fly)
            {
                dm.flightController.tryStartFlying();
                if (dm.flightController.isFlying())
                    PacketHandler.INSTANCE.sendToServer(new SDDToggleWingsPacket(true, true));                                
            }
            else if (ab == AbilityType.FlyStop)
            {
                dm.flightController.stopFlying();
                PacketHandler.INSTANCE.sendToServer(new SDDToggleWingsPacket(dm.flightController.areWingsOut(), false));
            }
            else if (ab == AbilityType.UnfoldWings)
            {
                dm.flightController.tryUnfoldWings();
                if (dm.flightController.areWingsOut())
                    PacketHandler.INSTANCE.sendToServer(new SDDToggleWingsPacket(true, dm.flightController.isFlying()));
            }
            else if (ab == AbilityType.FoldWings)
            {
                dm.flightController.foldWings();
                PacketHandler.INSTANCE.sendToServer(new SDDToggleWingsPacket(false, false));
            }
            else if (ab == AbilityType.TakeOff)
            {
                if (!dm.GetFlightController().canTakeOff()) return;
                dm.flightController.tryTakeOff();
                PacketHandler.INSTANCE.sendToServer(new SDDTakeOffPacket());
            }
            else if (ab == AbilityType.CombatToggle)
            {
                dm.SetCombatMode(!dm.IsCombatMode());
                PacketHandler.INSTANCE.sendToServer(new SDDCombatModePacket(dm.IsCombatMode()));
            }
            else if (ab == AbilityType.WeaponSwap)
            {
                if (!dm.IsCombatMode()) return;
                dm.NextWeapon();
                PacketHandler.INSTANCE.sendToServer(new SDDWeaponTogglePacket(dm.GetWeapon(EnumHand.MAIN_HAND).GetNumericId()));
            }
        }     
    }
}
