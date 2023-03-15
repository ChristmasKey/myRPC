package com.djn;

import com.djn.proxy.ProxyFactory;

/**
 * Name: Consumer
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-13 12:51
 */
public class Consumer {

    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("JiaNan456");
        System.out.println(result);
    }
}
