import Graphics.FortuneWindow;
import Input.Keyboard;
import Scene.SceneBehaviour;

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
        glClearColor(0f, 0f, 0f,1.0f);
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
            SceneBehaviour scene = wind.getSceneByName("FirstScene");
            wind.setSceneAsActive(scene);
        }

        if(Keyboard.isKeyDown(GLFW_KEY_R))
            System.out.println("hello from " + (wind.getScenes().indexOf(this) + 1) + " scene");
    }

    @Override
    public void dispose()
    {

    }
}