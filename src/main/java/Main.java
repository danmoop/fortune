import General.GameBehaviour;
import Graphics.FortuneWindow;

public class Main implements GameBehaviour
{
    FortuneWindow window;

    public Main()
    {
        window = new FortuneWindow(this);
        window.setTitle("Hello");
        window.setResizable(false);
        window.setDimensions(600, 600);
        window.show();
    }

    public static void main(String[] args)
    {
        new Main();
    }

    public void onPreload()
    {
        System.out.println("preloaded");
    }

    public void onStart()
    {
        System.out.println("started");
    }

    public void onRender()
    {
        System.out.println("rendering");
    }

    public void onUpdate()
    {
        System.out.println("updating");
    }

    public void onDispose()
    {
        System.out.println("disposed");
    }
}