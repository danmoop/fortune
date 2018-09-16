package General;

public class Debug
{
    public static void error(String text)
    {
        throw new java.lang.Error(text);
    }
}