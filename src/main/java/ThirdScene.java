import Graphics.FortuneWindow;
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
    public void onStart() {
        glClearColor(0f, 0f, 0f,1.0f);
    }

    @Override
    public void onRender() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(wind.getGameWindow());
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDispose() {

    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(wind.getActiveScene() == this)
        {
            if(key == GLFW_KEY_SPACE && action == GLFW_PRESS)
            {
                SceneBehaviour scene = wind.getSceneByName("FirstScene");
                wind.setSceneAsActive(scene);

                scene.onStart();

                wind.setWindowTitle(scene.getSceneName());
            }

            if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                glfwSetWindowShouldClose(wind.getGameWindow(), true);
        }
    }
}