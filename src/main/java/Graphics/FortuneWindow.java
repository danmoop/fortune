package Graphics;

import General.Debug;
import General.GameBehaviour;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.system.MemoryUtil.NULL;

public class FortuneWindow
{
    private GameBehaviour gameBehaviour;
    private long gameWindow;

    private int resizable = GLFW_TRUE;
    private String title;
    private int width = -1;
    private int height = -1;

    public FortuneWindow(GameBehaviour gameBehaviour)
    {
        this.gameBehaviour = gameBehaviour;
    }

    public void show()
    {
        try{
            handleErrors();
            GLFWErrorCallback.createPrint(System.err).set();

            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
            glfwWindowHint(GLFW_RESIZABLE, resizable);

            gameWindow = glfwCreateWindow(width, height, title, NULL, NULL);

            if(gameWindow == NULL)
                Debug.error("Unable to create game window");

            glfwShowWindow(gameWindow);

            gameBehaviour.onPreload();
            gameBehaviour.onStart();

            new Thread(new Runnable() {
                public void run() {
                    render();
                }
            }).start();

            while (!glfwWindowShouldClose(gameWindow))
            {
                //UPDATE
                glfwPollEvents();
                gameBehaviour.onUpdate();
            }
        } finally {
            gameBehaviour.onDispose();
            glfwTerminate();
            glfwSetErrorCallback(null).free();
            System.exit(0);
        }
    }

    private void render()
    {
        //RENDER
        glfwMakeContextCurrent(gameWindow);
        glfwSwapInterval(1);

        GL.createCapabilities();
        glClearColor(52 / 255f, 152 / 255f, 219 / 255f,1.0f);

        while ( !glfwWindowShouldClose(gameWindow) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(gameWindow);
            glfwPollEvents();

            gameBehaviour.onRender();
        }
    }

    // GETTERS & SETTERS BELOW

    public void setDimensions(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setResizable(boolean resizable)
    {
        if(resizable) this.resizable = GLFW_TRUE;
        else this.resizable = GLFW_FALSE;
    }

    private void handleErrors()
    {
        if(width == -1 || height == -1)
            Debug.error("Width and height aren't initialized");
        if(!glfwInit())
            Debug.error("Unable to initialize glfw");
        if(title == null)
            Debug.error("Title is initialized\nUse name.setTitle('Anything');");
    }

    public void getWindowInfo()
    {
        System.out.println("FortuneWindow{" +
                "gameBehaviour=" + gameBehaviour +
                ", gameWindow=" + gameWindow +
                ", resizable=" + resizable +
                ", title='" + title + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}');
    }
}