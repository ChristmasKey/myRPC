package com.djn;

import com.djn.common.URL;
import com.djn.protocol.HttpServer;
import com.djn.register.LocalRegister;
import com.djn.register.MapRemoteRegister;

/**
 * Name: Provider
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-13 13:10
 */
public class Provider {

    public static void main(String[] args) {
        //注册HelloService的两个实现类（本地注册）
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);

        //注册中心注册
        URL url = new URL("localhost", 8080);
        MapRemoteRegister.register(HelloService.class.getName(), url);

        //通过Netty、Tomcat等来接收网络请求
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());
    }
}
