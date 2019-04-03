package com.mlib.ioc;

import android.app.Activity;
import android.view.View;

import com.mlib.ioc.annotations.ContentView;
import com.mlib.ioc.annotations.EventBase;
import com.mlib.ioc.annotations.InjectView;
import com.mlib.ioc.listener.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created on 2019-4-2 16:55.
 * Describe: TODO
 */

public class InjectManager {
    public static void inject(Activity activity) {
        //布局注入
        injectLayout(activity);

        // 控件注入
        injectViews(activity);

        // 事件注入
        injectEvents(activity);
    }

    //布局注入
    private static void injectLayout(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类的注解
        ContentView layout = clazz.getAnnotation(ContentView.class);
        if (layout != null) {
            // 获取注解的值（R.layout.xxx）
            int layoutId = layout.value();
            try {
                // 获取指定的方法（setContentView）坑：getMethod
                Method method = clazz.getMethod("setContentView", int.class);
                // 执行方法
                method.invoke(activity, layoutId);
                // 另外的写法:
//                activity.setContentView(layoutId);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // 控件注入
    private static void injectViews(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 循环，拿到每个属性
        for (Field field : fields) {
            // 获得属性上的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            // 获取注解的值
            if (injectView != null) {   // 并不是所有的属性都有注解
                int viewId = injectView.value();
                // 获取findViewById方法，并执行
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    // 另一种写法：
                    // view = activity.findViewById(viewId);
                    // 还有坑：访问修饰符
                    field.setAccessible(true);      // 设置访问权限private
                    field.set(activity, view);      // 属性的值赋给控件，在当前Activity
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 事件注入
    private static void injectEvents(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类的所有方法
        Method[] methods = clazz.getMethods();
        // 遍历方法
        for (Method method : methods) {
            // 获取每个方法的注解（多个控件id）
            Annotation[] annotations = method.getAnnotations();
            // 遍历注解
            for (Annotation annotation : annotations) {
                // 获取注解上的注解
                // 获取OnClick注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    // 通过EventBase指定获取
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    // 有些方法没有EventBase注解，所以要判null
                    if (eventBase != null) {
                        // 监听事件的三个成员
                        // 1、set方法名
                        String listenerSetter = eventBase.listenerSetter();
                        // 2、监听的对象
                        Class<?> listenerType = eventBase.listenerType();
                        // 3、回调方法
                        String callBackListener = eventBase.callBackListener();

                        // 获取注解的值，执行方法再去获得注解的值
                        try {
                            // 通过annotationType获取onClick/OnLongClick注解的value值
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            // 执行value方法获得注解的值
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            // 代理方式（3个成员组合）
                            // 拦截方法
                            // 得到监听的代理对象（新建代理单例、类的加载器，指定要代理的对象类的类型、class实例）
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callBackListener, method);
                            // 监听对象的代理对象
                            // ClassLoader loader:指定当前目标对象使用类加载器,获取加载器的方法是固定的
                            // Class<?>[] interfaces:目标对象实现的接口的类型,使用泛型方式确认类型
                            // InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType},
                                    handler);

                            // 遍历注解的值
                            for (int viewId : viewIds) {
                                // 获得当前activity的view（赋值）
                                View view = activity.findViewById(viewId);
                                // 获取指定的方法
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                // 执行方法
                                setter.invoke(view, listener);
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
