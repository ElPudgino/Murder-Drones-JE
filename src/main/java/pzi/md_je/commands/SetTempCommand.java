package pzi.md_je.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import pzi.md_je.MD;
import pzi.md_je.capability.IMorph;
import pzi.md_je.generic.IHeatHolder;
import pzi.md_je.network.PacketHandler;
import pzi.md_je.network.to_client.CMorphPacket;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;

public class SetTempCommand extends CommandBase
{
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

	@Override
	public String getName() 
    {
		return "settemp";
	}

	@Override
	public String getUsage(ICommandSender sender) 
    {
		return "/settemp";
	}

	@Override
	public List<String> getAliases() 
    {
		List<String> aliases = Lists.<String>newArrayList();
		aliases.add("settemp");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
    {	   
        if (args.length < 2)
        {
            return;
        }
        EntityPlayer player = getPlayer(server,sender,args[0]); 
        MD.logger.info("called settemp with args: "+args[0]+", "+args[1]);  
        IMorph m = PlayerMorphs.GetMorph(player);
        if (!(m instanceof IHeatHolder)) return;
        ((IHeatHolder)m).GetHeatManager().temp = Float.valueOf(args[1]);
        ((IHeatHolder)m).GetHeatManager().coreTemp = Float.valueOf(args[1]);
        CMorphPacket p = new CMorphPacket(player, false);
        PacketHandler.INSTANCE.sendToAllTracking(p, player);
        PacketHandler.INSTANCE.sendTo(p, (EntityPlayerMP)player);
        sender.sendMessage(format(net.minecraft.util.text.TextFormatting.YELLOW, "Set temp: "+args[1]));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,	BlockPos targetPos) 
    {
		if (args.length == 1) {return Arrays.asList(server.getOnlinePlayerNames());}
		List<String> ms = new ArrayList<>();
		ms.add(String.valueOf(40));
        ms.add(String.valueOf(300));
        ms.add(String.valueOf(600));
        ms.add(String.valueOf(1100));
        
        if (args.length == 2) {return ms;}
		
        return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
    {
		return false;
	}
	
	private TextComponentTranslation format(TextFormatting color, String str, Object... args)
    {
        TextComponentTranslation ret = new TextComponentTranslation(str, args);
        ret.getStyle().setColor(color);
        return ret;
    }    
}
