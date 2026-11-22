package pzi.md_je.commands;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import pzi.md_je.MD;
import pzi.md_je.player.Morphs;
import pzi.md_je.player.PlayerMorphs;


public class SetMorphCommand extends CommandBase
{
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

	@Override
	public String getName() 
    {
		return "setmorph";
	}

	@Override
	public String getUsage(ICommandSender sender) 
    {
		return "/setmorph";
	}

	@Override
	public List<String> getAliases() 
    {
		List<String> aliases = Lists.<String>newArrayList();
		aliases.add("setmorph");
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
        MD.logger.info("called morph with args: "+args[0]+", "+args[1]);  
        PlayerMorphs.SetMorphServer(player, Morphs.valueOf(args[1]));
        sender.sendMessage(format(net.minecraft.util.text.TextFormatting.DARK_GREEN, "Morph: "+args[1]));
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,	BlockPos targetPos) 
    {
		if (args.length == 1) {return Arrays.asList(server.getOnlinePlayerNames());}
		List<String> ms = new ArrayList<>();
		for (Morphs m : Morphs.values())
		{	
			ms.add(m.toString());
		}
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