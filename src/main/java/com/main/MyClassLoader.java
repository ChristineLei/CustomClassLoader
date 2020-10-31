package com.main;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class MyClassLoader extends ClassLoader {

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("Class Loading Started for " + name);
        if (name.startsWith("com")) {
            return getClass(name);
        }else if(name.startsWith("Hello")) {
            return getClass_1(name);
        }
        return super.loadClass(name);
    }

    /**
     * Loading of class from .class file
     * happens here You Can modify logic of
     * this method to load Class
     * from Network or any other source
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private Class<?> getClass(String name) throws ClassNotFoundException {
        System.out.println("*********Inside getClass*********");

        String file = name.replace('.', File.separatorChar) + ".class";
        System.out.println("Name of File: " + file);
        byte[] byteArr = null;
        try {
            // This loads the byte code data from the file
            byteArr = loadClassData(file);
            System.out.println("Size of byte array "+byteArr.length);
            Class<?> c = defineClass(name, byteArr, 0, byteArr.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads a given file and converts
     * it into a Byte Array
     * @param name
     * @return
     * @throws IOException
     */
    private byte[] loadClassData(String name) throws IOException {

        System.out.println("<<<<<<<<<Inside loadClassData>>>>>>");

        InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
        int size = stream.available();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(stream);
        // Reading the binary data
        in.readFully(buff);
        in.close();
        return buff;
    }
    /**
     * Loading of class from .class file
     * happens here You Can modify logic of
     * this method to load Class
     * from Network or any other source
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private Class<?> getClass_1(String name) throws ClassNotFoundException {
        System.out.println("*********Inside getClass*********");

        String file = name.replace('.', File.separatorChar) + ".class";
        System.out.println("Name of File: " + file);
        byte[] byteArr = null;
        try {
            // This loads the byte code data from the file
            byteArr = loadClassData_1(file);
            System.out.println("Size of byte array "+byteArr.length);
            Class<?> c = defineClass(name, byteArr, 0, byteArr.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private byte[] loadClassData_1(String name) throws IOException, ClassNotFoundException {

        System.out.println("<<<<<<<<<Inside loadClassData>>>>>>");
        try {
//            File directory = new File("./");
//            System.out.println(directory.getAbsolutePath());
            byte[] bytes = FileUtils.readFileToByteArray(new File("./src/main/java/com/main/Hello.xlass"));
            for(int i=0; i<bytes.length; i++) {
                bytes[i] = (byte)(255 - (int)bytes[i]);
            }
            return bytes;
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }

    }
}
