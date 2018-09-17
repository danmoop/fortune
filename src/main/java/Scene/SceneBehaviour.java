package Scene;

public interface SceneBehaviour
{
    public abstract String getSceneName();
    public abstract void onStart();
    public abstract void onRender();
    public abstract void onUpdate();
    public abstract void onDispose();
}
