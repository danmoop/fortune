package Graphics;

import General.Debug;
import General.GameBehaviour;
import Scene.SceneBehaviour;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

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
    private SceneBehaviour activeScene;
    private long gameWindow;
    private int width = -1;
    private int height = -1;
    private String title;
    private Thread renderThread;
    private boolean renderThreadRunning = true;

    public FortuneWindow(GameBehaviour gameBehaviour)
    {
        this.gameBehaviour = gameBehaviour;
        scenes = new ArrayList<SceneBehaviour>();
        gameBehaviour.onPreload();
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

        glfwSetKeyCallback(gameWindow, (gameWindow, key, scancode, actions, mods) -> {
            if(key == GLFW_KEY_ESCAPE && actions == GLFW_RELEASE)
                glfwSetWindowShouldClose(gameWindow, true);
        });

        glfwShowWindow(gameWindow);

        gameBehaviour.onStart();

        renderThread = new Thread(() -> {
            if(renderThreadRunning)
                renderScene();
        });

        renderThread.start();

        while (!glfwWindowShouldClose(gameWindow))
        {
            glfwPollEvents();
            activeScene.onUpdate();
        }

        gameBehaviour.onDispose();
        activeScene.onDispose();
        glfwTerminate();
        System.exit(0);
    }

    public void renderScene()
    {
        glfwMakeContextCurrent(gameWindow);
        glfwSwapInterval(1);
        GL.createCapabilities();
        glClearColor(52 / 255f, 152 / 255f, 219 / 255f,1.0f);

        while(!glfwWindowShouldClose(gameWindow))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(gameWindow);
            activeScene.onRender();
        }
    }

    public void addScene(SceneBehaviour sceneBehaviour)
    {
        scenes.add(sceneBehaviour);

        if(scenes.size() == 1)
            activeScene = sceneBehaviour;
    }

    public void setSceneAsActive(SceneBehaviour scene)
    {
        activeScene = scene;
        initScene(activeScene);
    }

    public void initScene(SceneBehaviour scene)
    {
        scene.onStart();
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
}