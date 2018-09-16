import General.GameBehaviour;
import Graphics.FortuneWindow;

public class Main implements GameBehaviour
{
    FortuneWindow window;

    public Main()
    {
        window = new FortuneWindow(this);
        window.setTitle("Hello");
        window.setDimensions(600, 600);
        window.show();
    }

    public static void main(String[] args)
    {
        new Main();
    }

    @Override
    public void onPreload()
    {
        System.out.println("preload");
    }

    @Override
    public void onStart()
    {
        window.getWindowInfo();
    }

    @Override
    public void onRender()
    {

    }

    @Override
    public void onUpdate()
    {
    }

    @Override
    public void onDispose()
    {
        System.out.println("dispose");
    }
}