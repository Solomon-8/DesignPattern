package createpattern.singletonpattern;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Solomon
 * @date 2018/10/21
 * if you founded any bugs in my code
 * look at my face
 * that's a feature
 * ─ wow ──▌▒█───────────▄▀▒▌───
 * ────────▌▒▒▀▄───────▄▀▒▒▒▐───
 * ───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐───
 * ─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐───
 * ───▄▀▒▒▒▒▒▒ such difference ─
 * ──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌──
 * ──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐──
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌─
 * ─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─
 * ─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐─
 * ▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒ electrons ▒▌
 * ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐─
 * ─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐──
 * ──▀ amaze ▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌──
 * ────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀───
 * ───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀─────
 * ──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀────────
 * " "
 */
public class Main {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, IOException {

        //寻找构造函数
        Constructor pvtConstructor = Class.forName("createpattern.singletonpattern.HungryDemo").getDeclaredConstructors()[0];
        //找到构造函数以后把构造函数的accessible设置为true
        pvtConstructor.setAccessible(true);
        //调用构造函数并且
        HungryDemo  notSingleton1 = (HungryDemo) pvtConstructor.newInstance(new Object[]{});
        System.out.println(notSingleton1.hashCode());
        System.out.println("notSingleton1 --->"+notSingleton1.toString());
        HungryDemo  notSingleton2 = (HungryDemo) pvtConstructor.newInstance(new Object[]{});
        System.out.println(notSingleton2.hashCode());
        System.out.println("notSingleton2 --->"+notSingleton2.toString());
        //无一幸免于难

        //如果设置了这个将无法修改accessible
//        System.setSecurityManager(new SecurityManager());
        HungryDemo hungryDemo = HungryDemo.getInstance();
        System.out.println(hungryDemo.hashCode());
        System.out.println("hungryDemo --->"+hungryDemo.toString());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(hungryDemo);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        HungryDemo test = (HungryDemo) objectInputStream.readObject();
        System.out.println(test.hashCode());
        System.out.println("test --->"+test.toString());

        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(outputStream1);
        objectOutputStream1.writeObject(hungryDemo);
        ByteArrayInputStream inputStream1 = new ByteArrayInputStream(outputStream1.toByteArray());
        ObjectInputStream objectInputStream1 = new ObjectInputStream(inputStream1);
        HungryDemo test1 = (HungryDemo) objectInputStream1.readObject();
        System.out.println(test1.hashCode());
        System.out.println("test1 --->"+test1.toString());

        // 两个classloader也会导致单例变成不是单例，因为需要加载jar有点麻烦，而且不好展示，所以就不弄了

        /*
         * ClassLoader cl1 = new URLClassLoader(new URL[]{"singleton.jar"}, null);
         * ClassLoader cl2 = new URLClassLoader(new URL[]{"singleton.jar"}, null);
         * Class<?> singClass1 = cl1.loadClass("hacking.Singleton");
         * Class<?> singClass2 = cl2.loadClass("hacking.Singleton");
         * //...
         * Method getInstance1 = singClass1.getDeclaredMethod("getInstance", ...);
         * Method getInstance2 = singClass2.getDeclaredMethod("getInstance", ...);
         * //...
         * Object singleton1 = getInstance1.invoke(null);
         * Object singleton2 = getInstance2.invoke(null);
         */


    }
}
