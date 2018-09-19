package Graphics;

import General.Debug;
import General.GameBehaviour;
import Scene.SceneBehaviour;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.system.MemoryUtil.NULL;

public class FortuneWindow
{
    private GameBehaviour gameBehaviour;
    private List<SceneBehaviour> scenes;
    private SceneBehaviour activeScene = null;
    private long gameWindow;
    private int width = -1;
    private int height = -1;
    private String title;
    private boolean isWindowShown = false;

    public FortuneWindow(GameBehaviour gameBehaviour)
    {
        this.gameBehaviour = gameBehaviour;
        scenes = new ArrayList<SceneBehaviour>();
    }

    public void show()
    {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            Debug.error("Unable to initialize glfw");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        if(width == -1 || height == -1 || title == null)
            Debug.error("Width or height or title aren't initialized");

        gameWindow = glfwCreateWindow(width, height, title, NULL, NULL);

        if(gameWindow == NULL)
            Debug.error("Unable to create game window");

        glfwSetKeyCallback(gameWindow, activeScene);

        glfwMakeContextCurrent(gameWindow);
        glfwSwapInterval(1);
        GL.createCapabilities();
        glfwShowWindow(gameWindow);

        gameBehaviour.onStart();

        isWindowShown = true;

        if(activeScene == null)
            Debug.error("Active scene is not initialized. Add any Scene or make your scene active");

        renderScene();

        gameBehaviour.onDispose();
        activeScene.onDispose();
        glfwFreeCallbacks(gameWindow);
        glfwDestroyWindow(gameWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        System.gc();
        System.exit(0);
    }

    public void renderScene()
    {
        activeScene.onStart();

        while(!glfwWindowShouldClose(gameWindow))
        {
            glfwPollEvents();
            activeScene.onUpdate();
            activeScene.onRender();

            //System.out.println(activeScene.getSceneName());
        }
    }

    public void addScene(SceneBehaviour sceneBehaviour)
    {
        scenes.add(sceneBehaviour);
    }

    public void setSceneAsActive(SceneBehaviour scene)
    {
        activeScene = scene;

        if(isWindowShown)
            glfwSetKeyCallback(gameWindow, activeScene);

    }

    //GETTERS & SETTERS

    public int getHeight()
    {
        return height;
    }

    public String getTitle()
    {
        return title;
    }

    public int getWidth()
    {
        return width;
    }

    public List<SceneBehaviour> getScenes()
    {
        return scenes;
    }

    public SceneBehaviour getActiveScene()
    {
        return activeScene;
    }

    public void setDimensions(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public SceneBehaviour getSceneByName(String name)
    {
        for (SceneBehaviour scene: scenes)
        {
            if(scene.getSceneName().equals(name))
                return scene;
        }

        return null;
    }

    public long getGameWindow()
    {
        return gameWindow;
    }
}