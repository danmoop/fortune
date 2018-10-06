import Graphics.FortuneWindow;
import Input.Keyboard;
import Input.Mouse;
import Scene.SceneBehaviour;

import static Graphics.FortuneWindow.clearBackground;
import static Graphics.FortuneWindow.renderWindow;
import static Input.Mouse.LEFT_MOUSE_BUTTON;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;

public class ThirdScene implements SceneBehaviour
{
    private FortuneWindow wind;

    public ThirdScene(FortuneWindow wind)
    {
        this.wind = wind;
    }

    @Override
    public String getSceneName() {
        return "ThirdScene";
    }

    @Override
    public void start()
    {
        clearBackground(0, 0, 0, 1);
    }

    @Override
    public void render()
    {
        renderWindow();
    }

    @Override
    public void update()
    {
        if(Mouse.isMouseButtonPressed(LEFT_MOUSE_BUTTON))
        {
            SceneBehaviour scene = wind.getSceneByName("FirstScene");
            wind.openScene(scene);
        }
    }

    @Override
    public void dispose()
    {

    }
}