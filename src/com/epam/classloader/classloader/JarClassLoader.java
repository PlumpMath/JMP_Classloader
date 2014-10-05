package com.epam.classloader.classloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Yury Zaranok (yury_zaranok@epam.com) on 01.10.14.
 */
public class JarClassLoader extends ClassLoader {
    private String pathToJar; //Path to the jar file
    private Map<String, Class> classes = new HashMap<String, Class>(); //cache for already defined classes

    public JarClassLoader(String pathToJar) {
        super(JarClassLoader.class.getClassLoader());
        this.pathToJar = pathToJar;
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    @Override
    public Class findClass(String className) {
        byte classByte[];
        Class resultClass = null;

        resultClass = classes.get(className); //loading class from cache
        if (resultClass != null) {
            return resultClass;
        }

        try {
            return findSystemClass(className);
        } catch (ClassNotFoundException e) {
            System.err.println("Class Not Found! " + e);
        }

        try {
            JarFile jar = new JarFile(pathToJar);
            JarEntry entry = jar.getJarEntry(className + ".class");
            InputStream is = jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (nextValue != -1) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();
            resultClass = defineClass(className, classByte, 0, classByte.length, null);
            classes.put(className, resultClass);
            return resultClass;
        } catch (Exception e) {
            return null;
        }
    }

}