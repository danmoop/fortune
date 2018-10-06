package Input;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_HOVERED;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Mouse implements GLFWMouseButtonCallbackI
{
    public static final int LEFT_MOUSE_BUTTON = 0;
    public static final int RIGHT_MOUSE_BUTTON = 1;
    public static final int MIDDLE_MOUSE_BUTTON = 2;

    private static boolean buttons[] = new boolean[128];

    @Override
    public void invoke(long window, int button, int action, int mods)
    {
        buttons[button] = action != GLFW_RELEASE;
    }

    public static boolean isMouseButtonPressed(int mouseButton)
    {
        return buttons[mouseButton];
    }
}