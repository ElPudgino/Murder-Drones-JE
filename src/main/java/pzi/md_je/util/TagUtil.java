package pzi.md_je.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TagUtil 
{
    public static float GetBaseMaxHP(NBTTagCompound nbt)
    {
        NBTTagList attrs = (NBTTagList)nbt.getTag("Attributes");
        float res = 0f;
        NBTTagCompound tag = null;
        for (int i = 0; i < attrs.tagCount(); i++)
        {
            tag = (NBTTagCompound)attrs.get(i);
            if ("generic.maxHealth".equals(tag.getString("Name")))
            {
                res = (float)tag.getDouble("Base");
            }
        }
        return res;
    }

    public static byte[] nbtToByteArray(NBTTagCompound compound) 
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            CompressedStreamTools.writeCompressed(compound, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }
    public static NBTTagCompound fromByteArray(byte[] data) 
    {
        try (java.io.DataInputStream input = new java.io.DataInputStream(
                new java.util.zip.GZIPInputStream(new ByteArrayInputStream(data))))
        {
            return CompressedStreamTools.read(input, new NBTSizeTracker(2L * 1024 * 1024));
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
