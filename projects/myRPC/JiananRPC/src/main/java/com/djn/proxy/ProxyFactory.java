package com.djn.proxy;

import com.djn.common.Invocation;
import com.djn.protocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Name: ProxyFactory
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-14 09:45
 */
public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass) {
        //可以根据用户配置去定义用什么样的方式进行动态代理

        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                (proxy, method, args) -> {
                    Invocation invocation = new Invocation(interfaceClass.getName(),
                            method.getName(),
                            method.getParameterTypes(), args);

                    HttpClient httpClient = new HttpClient();

                    return httpClient.send("localhost", 8080, invocation);
                });

        return (T) proxyInstance;
    }
}
