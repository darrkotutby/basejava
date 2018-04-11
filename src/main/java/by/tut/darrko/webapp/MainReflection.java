package by.tut.darrko.webapp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        String resumeClassName = "by.tut.darrko.webapp.model.Resume";
        Class<?> resumeClass = Class.forName(resumeClassName);
        Constructor<?> dogConstructor = resumeClass.getConstructor(String.class);
        Object resume = dogConstructor.newInstance("test1");
        Method getUuidMethod = resume.getClass().getMethod("getUuid");
        System.out.println("getUuid: " + getUuidMethod.invoke(resume));
        Method toStringMethod = resume.getClass().getMethod("toString");
        System.out.println("toString: " + toStringMethod.invoke(resume));
    }
}
