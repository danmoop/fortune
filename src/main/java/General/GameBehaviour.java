package General;

public interface GameBehaviour
{
    public abstract void onPreload();
    public abstract void onStart();
    public abstract void onRender();
    public abstract void onUpdate();
    public abstract void onDispose();
}