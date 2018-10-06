import General.GameBehaviour;
import Graphics.FortuneWindow;

public class Main implements GameBehaviour
{
    FortuneWindow window;
    FirstScene scene;
    SecondScene scene2;
    ThirdScene scene3;

    public Main()
    {
        window = new FortuneWindow(this, false);
        scene = new FirstScene(window);
        scene2 = new SecondScene(window);
        scene3 = new ThirdScene(window);
        window.addScene(scene);
        window.addScene(scene2);
        window.addScene(scene3);
        window.setResizable(true);
        window.setDimensions(600, 400);
        window.show();
    }

    public static void main(String[] args)
    {
        new Main();
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