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
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class FortuneWindow
{
    private GameBehaviour gameBehaviour;
    private List<SceneBehaviour> scenes;
    private SceneBehaviour activeScene = null;
    private static long gameWindow;
    private int width = -1;
    private int height = -1;
    private String title;
    private boolean isWindowShown = false;
    private boolean vSync;
    private final String version = "v0.03";
    private int resizable = GLFW_TRUE;

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

        // setting window properties
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, resizable);

        if(width == -1 || height == -1)
            throw new NullPointerException("Width or height or title aren't initialized. Use setDimensions() method");

        if(title == null) title = "Fortune " + version;

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

        // everything down here is executed when window is closed

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

    public void openScene(SceneBehaviour scene)
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

        throw new NullPointerException("Can't find scene named: " + name + ". Perhaps you forgot to add it");
    }

    public void setResizable(boolean resizable)
    {
        if(resizable) this.resizable = GLFW_TRUE;
        else this.resizable = GLFW_FALSE;
    }

    public void setWindowTitle(String title)
    {
        glfwSetWindowTitle(gameWindow, title);
    }

    public static void clearBackground(float red, float green, float blue, float alpha)
    {
        glClearColor(red / 255f, green / 255f, blue / 255f, alpha);
    }

    public static void renderWindow()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(gameWindow);
    }

    public long getGameWindow()
    {
        return gameWindow;
    }
}