package com.djn.proxy;

import com.djn.common.Invocation;
import com.djn.common.URL;
import com.djn.loadbalance.LoadBalance;
import com.djn.protocol.HttpClient;
import com.djn.register.MapRemoteRegister;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

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
                    //服务Mock
                    //String mock = System.getProperty("mock");
                    //if (mock != null && mock.startsWith("return:")) {
                    //    return mock.replace("return:", "");
                    //}

                    Invocation invocation = new Invocation(interfaceClass.getName(),
                            method.getName(),
                            method.getParameterTypes(), args);

                    HttpClient httpClient = new HttpClient();

                    //服务发现
                    List<URL> urls = MapRemoteRegister.get(interfaceClass.getName());

                    //负载均衡
                    //URL url = LoadBalance.random(urls);

                    //服务调用
                    String result = null;
                    List<URL> invokedUrls = new ArrayList<>();

                    //服务重试
                    int max = 3;
                    while (max > 0) {

                        //负载均衡
                        urls.removeAll(invokedUrls);
                        URL url = LoadBalance.random(urls);
                        invokedUrls.add(url);

                        try {
                            result = httpClient.send(url.getHostname(), url.getPort(), invocation);
                        } catch (Exception e) {
                            if (max-- != 0) {
                                continue;
                            }
                            //这里可以实现容错的功能
                            //可以通过error-callback去调用自定义的错误回调处理
                            //(例如com.djn.HelloServiceErrorCallback)
                            return "报错了";
                        }
                    }

                    return result;
                });

        return (T) proxyInstance;
    }
}
