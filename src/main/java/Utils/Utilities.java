package Utils;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Utilities
{
    public static FloatBuffer createFloatBuffer(float data[])
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    public static ByteBuffer createBufferIndices(byte[] data)
    {
        ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }
}