package Input;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class CursorPos implements GLFWCursorPosCallbackI
{
    private static double xpos;
    private static double ypos;

    @Override
    public void invoke(long window, double xpos, double ypos)
    {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public static double getXpos()
    {
        return xpos;
    }

    public static double getYpos()
    {
        return ypos;
    }
}