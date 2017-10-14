package test;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReflectionTesting {

    private double cosine(double angle) {
        return Math.cos(angle);
    }

    public static void main (String[] args) {
        Map<String, Class> primitives = new HashMap<>();
        primitives.put("double", Double.TYPE);
        ReflectionTesting t = new ReflectionTesting();
        Properties properties = new Properties();
        InputStream filestream = ReflectionTesting.class.getClassLoader()
                .getResourceAsStream("test/Test.properties");
        try {
            properties.load(filestream);
        } catch (Exception e) {
            System.out.println("file not found");
        }
        try {
            String[] methodInfo = properties.getProperty("COS".toLowerCase()).split(",");
            Class methodHost = Class.forName(methodInfo[0]);
            Class[] parameterClasses = new Class[methodInfo.length-2];
            for (int i = 2; i < methodInfo.length; i++) {
                if (primitives.containsKey(methodInfo[i])) {
                    parameterClasses[i-2] = primitives.get(methodInfo[i]);
                } else {
                    parameterClasses[i-2] = Class.forName(methodInfo[i]);
                }
            }
            Method method = methodHost.getDeclaredMethod(methodInfo[1], parameterClasses);
            System.out.println(method.getName());
            method.setAccessible(true);
            System.out.println(method.invoke(t, 1.1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
