package jvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

public class HelloXlassLoader extends ClassLoader{

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        HelloXlassLoader loader = new HelloXlassLoader();
        Class Hello = loader.findClass("Hello");
        try {
            Method hello = Hello.getMethod("hello");
            hello.invoke(Hello.newInstance());
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private byte[] processFileAndCompute() {
        System.out.println("#readFile");
        String base64Encoded = "NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfWqf7/+7yQm5r+//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+//W3mpOTkNGVnome8//4//f4/+nz/+j/5/7/7Leak5OQ09+ck56MjLOQnpuajd74/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk56RmNCshoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+NlpGLrIuNmp6S/v/4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/+f///////f/+//j/9//+//b////i//7//v////rVSP/+Tv////7/9f////n//v////7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9////+//3//r//v/z/////f/y";
        byte[] decoded = decode(base64Encoded);
        byte[] converted = new byte[decoded.length];
        for (int i = 0; i < decoded.length; i++) {
//            String s1 = String.format("%8s", Integer.toBinaryString(~decoded[i] & 0xFE));
//            System.out.println(s1);

//                converted[i] = (byte) (~decoded[i] & 0xFE);
                converted[i] = (byte) (255-decoded[i]);
        }
        return converted;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] classBytes = processFileAndCompute();
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
