package pzi.md_je.network;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.minecraft.nbt.NBTTagCompound;
import pzi.md_je.player.Morphs;
import pzi.md_je.util.TagUtil;

/** Bounds for the small morph-state payloads used by this protocol. */
public final class PacketData
{
    public static final int MAX_PLAYERS = 4096;
    private static final int MAX_NBT_BYTES = 1024 * 1024;

    private PacketData() {}

    public static int readInt(ByteBuf buf)
    {
        if (buf.readableBytes() < 4) throw new DecoderException("Truncated morph packet");
        return buf.readInt();
    }

    public static int readMorph(ByteBuf buf)
    {
        int id = readInt(buf);
        if (id < 0 || id >= Morphs.values().length)
            throw new DecoderException("Unknown morph: " + id);
        return id;
    }

    public static NBTTagCompound readNBT(ByteBuf buf)
    {
        int length = readInt(buf);
        if (length < 0 || length > MAX_NBT_BYTES || length > buf.readableBytes())
            throw new DecoderException("Invalid morph NBT length: " + length);
        if (length == 0) return null;
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        NBTTagCompound tag = TagUtil.fromByteArray(bytes);
        if (tag == null) throw new DecoderException("Invalid morph NBT");
        return tag;
    }
}
