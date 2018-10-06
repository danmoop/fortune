import Graphics.FortuneWindow;
import Input.Keyboard;
import Input.Mouse;
import Scene.SceneBehaviour;

import static Graphics.FortuneWindow.clearBackground;
import static Graphics.FortuneWindow.renderWindow;
import static Input.Mouse.LEFT_MOUSE_BUTTON;
import static Input.Mouse.MIDDLE_MOUSE_BUTTON;
import static Input.Mouse.RIGHT_MOUSE_BUTTON;
import static org.lwjgl.glfw.GLFW.*;

public class FirstScene implements SceneBehaviour
{
    private FortuneWindow wind;

    public FirstScene(FortuneWindow window)
    {
        this.wind = window;
    }

    @Override
    public String getSceneName()
    {
        return "FirstScene";
    }

    @Override
    public void start()
    {
        clearBackground(26, 62, 225, 1);
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
            SceneBehaviour scene = wind.getSceneByName("SecondScene");
            wind.openScene(scene);
        }
    }

    @Override
    public void dispose()
    {
    }
}