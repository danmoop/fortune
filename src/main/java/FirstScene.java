import Graphics.FortuneWindow;
import Input.CursorPos;
import Input.Keyboard;
import Input.Mouse;
import Scene.SceneBehaviour;

import static Input.Mouse.LEFT_MOUSE_BUTTON;
import static Input.Mouse.RIGHT_MOUSE_BUTTON;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;

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
        glClearColor(26 / 255f, 188 / 255f, 156 / 255f,1.0f);
    }

    @Override
    public void render()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(wind.getGameWindow());
    }

    @Override
    public void update()
    {
        if(Keyboard.isKeyDown(GLFW_KEY_SPACE))
        {
                SceneBehaviour scene = wind.getSceneByName("SecondScene");
                wind.setSceneAsActive(scene);
        }

        if(Keyboard.isKeyDown(GLFW_KEY_R))
            System.out.println("hello from " + (wind.getScenes().indexOf(this) + 1) + " scene");

        if(Mouse.isMouseButtonDown(LEFT_MOUSE_BUTTON))
            System.out.println("LMB pressed");

        if(Mouse.isMouseButtonDown(RIGHT_MOUSE_BUTTON))
            System.out.println("RMB pressed");

        System.out.println("X: " + CursorPos.getXpos());
        System.out.println("Y: " + CursorPos.getYpos());

    }

    @Override
    public void dispose()
    {
    }
}