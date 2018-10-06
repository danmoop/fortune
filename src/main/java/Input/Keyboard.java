package Input;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard implements GLFWKeyCallbackI
{

    private static boolean keys[] = new boolean[65535];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyPressed(int key)
    {
        return keys[key];
    }
}