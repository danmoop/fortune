package Scene;

public interface SceneBehaviour {
    String getSceneName();
    void start();
    void render();
    void update();
    void dispose();
}