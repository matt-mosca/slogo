package test;

import utilities.CommandGetter;

import java.lang.reflect.Method;

public class ReflectionTesting {

    private double cosine(double angle) {
        return Math.cos(angle);
    }

    private double sum(double a, double b) {
        return a + b;
    }

    private double getPi() {
        return Math.PI;
    }

    public static void main(String[] args) {
        ReflectionTesting t = new ReflectionTesting();
        CommandGetter getter = new CommandGetter("languages/English.properties");
        String[] methodInfo = getter.getCommandInfo("pi".toLowerCase());
        int numberOfDoubleParameters = Integer.parseInt(methodInfo[1]);
        double result = Double.MIN_VALUE;
        Method method = null;
        try {
            switch (numberOfDoubleParameters) {
                case 0:
                    method = ReflectionTesting.class.getDeclaredMethod(methodInfo[0]);
                    method.setAccessible(true);
                    result = (double) method.invoke(t);
                    break;
                case 1:
                    method = ReflectionTesting.class.getDeclaredMethod(methodInfo[0], double.class);
                    method.setAccessible(true);
                    result = (double) method.invoke(t, Math.random());
                    break;
                case 2:
                    method = ReflectionTesting.class.getDeclaredMethod(methodInfo[0], double.class, double.class);
                    method.setAccessible(true);
                    result = (double) method.invoke(t, Math.random(), Math.random());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace(); // for testing only!!!
        }
        System.out.println(result);
    }
}
