package Scene;

import org.lwjgl.glfw.GLFWKeyCallbackI;

public interface SceneBehaviour extends GLFWKeyCallbackI
{
    public abstract String getSceneName();
    public abstract void onStart();
    public abstract void onRender();
    public abstract void onUpdate();
    public abstract void onDispose();

    @Override
    void invoke(long window, int key, int scancode, int action, int mods);
}
