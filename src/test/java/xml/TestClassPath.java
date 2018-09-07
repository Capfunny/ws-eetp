package xml;

public class TestClassPath {
    public static void main(String[] args) {
        String classpathStr = System.getProperty("java.class.path");
        System.out.print(classpathStr);
    }
}
