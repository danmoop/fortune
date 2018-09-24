package Graphics;

import General.Debug;
import General.GameBehaviour;
import Input.CursorEnter;
import Input.CursorPos;
import Input.Keyboard;
import Input.Mouse;
import Scene.SceneBehaviour;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
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
    private boolean vSync;

    public FortuneWindow(GameBehaviour gameBehaviour, boolean vSync)
    {
        this.gameBehaviour = gameBehaviour;
        this.vSync = vSync;
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

        glfwSetMouseButtonCallback(gameWindow, new Mouse());
        glfwSetKeyCallback(gameWindow, new Keyboard());
        glfwSetCursorPosCallback(gameWindow, new CursorPos());
        glfwSetCursorEnterCallback(gameWindow, new CursorEnter());
        glfwMakeContextCurrent(gameWindow);

        if(vSync) glfwSwapInterval(1);

        GL.createCapabilities();
        glfwShowWindow(gameWindow);

        // Center the window
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(gameWindow, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    gameWindow,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        isWindowShown = true;

        gameBehaviour.onStart();

        if(activeScene == null)
            Debug.error("Active scene is not initialized. Add any Scene or make your scene active");

        renderScene();

        gameBehaviour.onDispose();
        activeScene.dispose();
        glfwFreeCallbacks(gameWindow);
        glfwDestroyWindow(gameWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
        System.gc();
        System.exit(0);
    }

    public void renderScene()
    {
        activeScene.start();

        while(!glfwWindowShouldClose(gameWindow))
        {
            glfwWaitEvents();
            activeScene.update();
            activeScene.render();
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
        if(activeScene != null)
            activeScene.dispose();

        activeScene = scene;
        activeScene.start();
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

        Debug.error("Can't find scene named: " + name +". Perhaps you forgot to add it");

        return null;
    }

    public void setWindowTitle(String title)
    {
        glfwSetWindowTitle(gameWindow, title);
    }

    public long getGameWindow()
    {
        return gameWindow;
    }
}