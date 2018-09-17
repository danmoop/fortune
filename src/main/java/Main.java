import General.GameBehaviour;
import Graphics.FortuneWindow;

public class Main implements GameBehaviour
{
    FortuneWindow window;
    MyScene scene = new MyScene();

    public Main()
    {
        window = new FortuneWindow(this);
        window.addScene(scene);
        window.setDimensions(600, 600);
        window.setTitle("App");
        window.setSceneAsActive(window.getSceneByName("FirstScene"));
        window.show();
    }

    public static void main(String[] args)
    {
        new Main();
    }

    @Override
    public void onPreload()
    {

    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onDispose()
    {

    }
}