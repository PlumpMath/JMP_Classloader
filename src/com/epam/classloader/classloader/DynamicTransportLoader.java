package com.epam.classloader.classloader;

import com.epam.classloader.Interface.ITransport;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Yury Zaranok (yury_zaranok@epam.com) on 01.10.14.
 */
public class DynamicTransportLoader {

    public static List<ITransport> loadAllTransport(String pathToJar) {
        List<ITransport> loadedTransportList = new ArrayList<ITransport>();
        JarClassLoader loader = new JarClassLoader(pathToJar);

        try {
            Enumeration<JarEntry> jarEntries = new JarFile(pathToJar).entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace("/", ".").substring(0, jarEntry.getName().indexOf(".class"));
                    Class c = Class.forName(className, true, loader);
                    for (Class i : c.getInterfaces()) {
                        if ("com.epam.classloader.Interface.ITransport".equals(i.getName())) {
                            ITransport transport = (ITransport) c.newInstance();
                            loadedTransportList.add(transport);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Unable load classes: " + e);
        }

        return loadedTransportList;
    }
}
