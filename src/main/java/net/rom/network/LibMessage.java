package net.rom.network;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


/**
 * The Class LibMessage.
 *
 * @param <REQ> the generic type
 */
public class LibMessage<REQ extends LibMessage> implements Serializable, IMessage, IMessageHandler<REQ, IMessage> {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7308897241619184096L;
	
	/** The Constant handlers. */
	private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap();
    
    /** The Constant fieldCache. */
    private static final HashMap<Class, Field[]> fieldCache = new HashMap();

    static {
        map(byte.class, LibMessage::readByte, LibMessage::writeByte);
        map(short.class, LibMessage::readShort, LibMessage::writeShort);
        map(int.class, LibMessage::readInt, LibMessage::writeInt);
        map(long.class, LibMessage::readLong, LibMessage::writeLong);
        map(float.class, LibMessage::readFloat, LibMessage::writeFloat);
        map(double.class, LibMessage::readDouble, LibMessage::writeDouble);
        map(boolean.class, LibMessage::readBoolean, LibMessage::writeBoolean);
        map(char.class, LibMessage::readChar, LibMessage::writeChar);
        map(String.class, LibMessage::readString, LibMessage::writeString);
        map(NBTTagCompound.class, LibMessage::readNBT, LibMessage::writeNBT);
        map(ItemStack.class, LibMessage::readItemStack, LibMessage::writeItemStack);
        map(BlockPos.class, LibMessage::readBlockPos, LibMessage::writeBlockPos);
    }

    /**
     * Handle message.
     *
     * @param context the context
     * @return the i message
     */
    public IMessage handleMessage(MessageContext context) {
        return null;
    }

    /**
     * On message.
     *
     * @param message the message
     * @param context the context
     * @return the i message
     */
    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        return message.handleMessage(context);
    }

    /**
     * From bytes.
     *
     * @param buf the buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            Class<?> clazz = getClass();
            Field[] clFields = getClassFields(clazz);
            for (Field f : clFields) {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    readField(f, type, buf);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error at reading packet " + this, e);
        }
    }

    /**
     * To bytes.
     *
     * @param buf the buf
     */
    @Override
    public void toBytes(ByteBuf buf) {
        try {
            Class<?> clazz = getClass();
            Field[] clFields = getClassFields(clazz);
            for (Field f : clFields) {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    writeField(f, type, buf);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    /**
     * Gets the class fields.
     *
     * @param clazz the clazz
     * @return the class fields
     */
    @SuppressWarnings("unlikely-arg-type")
    private static Field[] getClassFields(Class<?> clazz) {
        if (fieldCache.containsValue(clazz))
            return fieldCache.get(clazz);
        else {
            Field[] fields = clazz.getFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            fieldCache.put(clazz, fields);
            return fields;
        }
    }

    /**
     * Write field.
     *
     * @param f the f
     * @param clazz the clazz
     * @param buf the buf
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    private final void writeField(Field f, Class clazz, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        handler.getRight().write(f.get(this), buf);
    }

    /**
     * Read field.
     *
     * @param f the f
     * @param clazz the clazz
     * @param buf the buf
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    private final void readField(Field f, Class clazz, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        f.set(this, handler.getLeft().read(buf));
    }

    /**
     * Gets the handler.
     *
     * @param clazz the clazz
     * @return the handler
     */
    private static Pair<Reader, Writer> getHandler(Class<?> clazz) {
        Pair<Reader, Writer> pair = handlers.get(clazz);
        if (pair == null)
            throw new RuntimeException("No R/W handler for  " + clazz);
        return pair;
    }

    /**
     * Accept field.
     *
     * @param f the f
     * @param type the type
     * @return true, if successful
     */
    private static boolean acceptField(Field f, Class<?> type) {
        int mods = f.getModifiers();
        if (Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods))
            return false;

        return handlers.containsKey(type);
    }

    /**
     * Map.
     *
     * @param <T> the generic type
     * @param type the type
     * @param reader the reader
     * @param writer the writer
     */
    private static <T extends Object> void map(Class<T> type, Reader<T> reader, Writer<T> writer) {
        handlers.put(type, Pair.of(reader, writer));
    }

    /**
     * Read byte.
     *
     * @param buf the buf
     * @return the byte
     */
    private static byte readByte(ByteBuf buf) {
        return buf.readByte();
    }

    /**
     * Write byte.
     *
     * @param b the b
     * @param buf the buf
     */
    private static void writeByte(byte b, ByteBuf buf) {
        buf.writeByte(b);
    }

    /**
     * Read short.
     *
     * @param buf the buf
     * @return the short
     */
    private static short readShort(ByteBuf buf) {
        return buf.readShort();
    }

    /**
     * Write short.
     *
     * @param s the s
     * @param buf the buf
     */
    private static void writeShort(short s, ByteBuf buf) {
        buf.writeShort(s);
    }

    /**
     * Read int.
     *
     * @param buf the buf
     * @return the int
     */
    private static int readInt(ByteBuf buf) {
        return buf.readInt();
    }

    /**
     * Write int.
     *
     * @param i the i
     * @param buf the buf
     */
    private static void writeInt(int i, ByteBuf buf) {
        buf.writeInt(i);
    }

    /**
     * Read long.
     *
     * @param buf the buf
     * @return the long
     */
    private static long readLong(ByteBuf buf) {
        return buf.readLong();
    }

    /**
     * Write long.
     *
     * @param l the l
     * @param buf the buf
     */
    private static void writeLong(long l, ByteBuf buf) {
        buf.writeLong(l);
    }

    /**
     * Read float.
     *
     * @param buf the buf
     * @return the float
     */
    private static float readFloat(ByteBuf buf) {
        return buf.readFloat();
    }

    /**
     * Write float.
     *
     * @param f the f
     * @param buf the buf
     */
    private static void writeFloat(float f, ByteBuf buf) {
        buf.writeFloat(f);
    }

    /**
     * Read double.
     *
     * @param buf the buf
     * @return the double
     */
    private static double readDouble(ByteBuf buf) {
        return buf.readDouble();
    }

    /**
     * Write double.
     *
     * @param d the d
     * @param buf the buf
     */
    private static void writeDouble(double d, ByteBuf buf) {
        buf.writeDouble(d);
    }

    /**
     * Read boolean.
     *
     * @param buf the buf
     * @return true, if successful
     */
    private static boolean readBoolean(ByteBuf buf) {
        return buf.readBoolean();
    }

    /**
     * Write boolean.
     *
     * @param b the b
     * @param buf the buf
     */
    private static void writeBoolean(boolean b, ByteBuf buf) {
        buf.writeBoolean(b);
    }

    /**
     * Read char.
     *
     * @param buf the buf
     * @return the char
     */
    private static char readChar(ByteBuf buf) {
        return buf.readChar();
    }

    /**
     * Write char.
     *
     * @param c the c
     * @param buf the buf
     */
    private static void writeChar(char c, ByteBuf buf) {
        buf.writeChar(c);
    }

    /**
     * Read string.
     *
     * @param buf the buf
     * @return the string
     */
    private static String readString(ByteBuf buf) {
        return ByteBufUtils.readUTF8String(buf);
    }

    /**
     * Write string.
     *
     * @param s the s
     * @param buf the buf
     */
    private static void writeString(String s, ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, s);
    }

    /**
     * Read NBT.
     *
     * @param buf the buf
     * @return the NBT tag compound
     */
    private static NBTTagCompound readNBT(ByteBuf buf) {
        return ByteBufUtils.readTag(buf);
    }

    /**
     * Write NBT.
     *
     * @param cmp the cmp
     * @param buf the buf
     */
    private static void writeNBT(NBTTagCompound cmp, ByteBuf buf) {
        ByteBufUtils.writeTag(buf, cmp);
    }

    /**
     * Read item stack.
     *
     * @param buf the buf
     * @return the item stack
     */
    private static ItemStack readItemStack(ByteBuf buf) {
        return ByteBufUtils.readItemStack(buf);
    }

    /**
     * Write item stack.
     *
     * @param stack the stack
     * @param buf the buf
     */
    private static void writeItemStack(ItemStack stack, ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
    }

    /**
     * Read block pos.
     *
     * @param buf the buf
     * @return the block pos
     */
    private static BlockPos readBlockPos(ByteBuf buf) {
        return BlockPos.fromLong(buf.readLong());
    }

    /**
     * Write block pos.
     *
     * @param pos the pos
     * @param buf the buf
     */
    private static void writeBlockPos(BlockPos pos, ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    /**
     * The Interface Writer.
     *
     * @param <T> the generic type
     */
    // Functional interfaces
    public interface Writer<T extends Object> {
        
        /**
         * Write.
         *
         * @param t the t
         * @param buf the buf
         */
        void write(T t, ByteBuf buf);
    }

    /**
     * The Interface Reader.
     *
     * @param <T> the generic type
     */
    public interface Reader<T extends Object> {
        
        /**
         * Read.
         *
         * @param buf the buf
         * @return the t
         */
        T read(ByteBuf buf);
    }

}
