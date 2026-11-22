package pzi.md_je.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import pzi.md_je.MD;
import pzi.md_je.network.to_client.CMorphPacket;
import pzi.md_je.network.to_client.CMorphSyncPacket;
import pzi.md_je.network.to_client.CParticlePacket;
import pzi.md_je.network.to_client.CUseWeaponPacket;
import pzi.md_je.network.to_client.ability.dd.CDDCombatModePacket;
import pzi.md_je.network.to_client.ability.dd.CDDFeedPacket;
import pzi.md_je.network.to_client.ability.dd.CDDTakeOffPacket;
import pzi.md_je.network.to_client.ability.dd.CDDToggleWingsPacket;
import pzi.md_je.network.to_client.ability.dd.CDDWeaponTogglePacket;
import pzi.md_je.network.to_server.SUseWeaponPacket;
import pzi.md_je.network.to_server.ability.dd.SDDCombatModePacket;
import pzi.md_je.network.to_server.ability.dd.SDDFeedPacket;
import pzi.md_je.network.to_server.ability.dd.SDDTakeOffPacket;
import pzi.md_je.network.to_server.ability.dd.SDDToggleWingsPacket;
import pzi.md_je.network.to_server.ability.dd.SDDWeaponTogglePacket;

public class PacketHandler 
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MD.MODID);    

    public static void registerPackets()
    {
        int id = 0;
        PacketHandler.INSTANCE.registerMessage(CMorphPacket.Handler.class, CMorphPacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CParticlePacket.Handler.class, CParticlePacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CMorphSyncPacket.Handler.class, CMorphSyncPacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CUseWeaponPacket.Handler.class, CUseWeaponPacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CDDFeedPacket.Handler.class, CDDFeedPacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CDDToggleWingsPacket.Handler.class, CDDToggleWingsPacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CDDCombatModePacket.Handler.class, CDDCombatModePacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CDDWeaponTogglePacket.Handler.class, CDDWeaponTogglePacket.class, id++, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(CDDTakeOffPacket.Handler.class, CDDTakeOffPacket.class, id++, Side.CLIENT);

        PacketHandler.INSTANCE.registerMessage(SUseWeaponPacket.Handler.class, SUseWeaponPacket.class, id++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage(SDDFeedPacket.Handler.class, SDDFeedPacket.class, id++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage(SDDToggleWingsPacket.Handler.class, SDDToggleWingsPacket.class, id++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage(SDDTakeOffPacket.Handler.class, SDDTakeOffPacket.class, id++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage(SDDCombatModePacket.Handler.class, SDDCombatModePacket.class, id++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage(SDDWeaponTogglePacket.Handler.class, SDDWeaponTogglePacket.class, id++, Side.SERVER);
        //fourth parameter is the side where packet will be received
    }
}
