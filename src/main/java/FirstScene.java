import Graphics.FortuneWindow;
import Scene.SceneBehaviour;
import org.lwjgl.opengl.GL;

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
    public void onStart()
    {
        glfwMakeContextCurrent(wind.getGameWindow());
        glfwSwapInterval(1);
        GL.createCapabilities();
        glClearColor(26 / 255f, 188 / 255f, 156 / 255f,1.0f);
    }

    @Override
    public void onRender()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(wind.getGameWindow());
    }

    @Override
    public void onUpdate()
    {

    }

    @Override
    public void onDispose()
    {

    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        if(wind.getActiveScene().getSceneName() == getSceneName())
        {
            System.out.println(key);

            if(key == GLFW_KEY_SPACE && action == GLFW_RELEASE)
            {
                SceneBehaviour scene = wind.getSceneByName("SecondScene");
                wind.setSceneAsActive(scene);

                scene.onStart();
            }
        }
    }
}