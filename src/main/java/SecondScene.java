import Graphics.FortuneWindow;
import Scene.SceneBehaviour;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;

public class SecondScene implements SceneBehaviour
{

    FortuneWindow wind;

    public SecondScene(FortuneWindow window)
    {
        wind = window;
    }

    @Override
    public String getSceneName()
    {
        return "SecondScene";
    }

    @Override
    public void onStart()
    {
        glClearColor(52 / 255f, 152 / 255f, 219 / 255f,1.0f);
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
        if(wind.getActiveScene() == this)
        {
            if(key == GLFW_KEY_SPACE && action == GLFW_RELEASE)
            {
                SceneBehaviour scene = wind.getSceneByName("FirstScene");
                wind.setSceneAsActive(scene);

                scene.onStart();
            }
        }
    }
}