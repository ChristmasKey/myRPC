package com.djn.protocol;

import com.djn.common.Invocation;
import com.djn.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * Name: HttpServerHandler
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-13 16:30
 */
public class HttpServerHandler {

    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        //处理请求 --> 请求要求调用哪个接口、哪个方法、传了哪些方法参数
        try {
            //获取Invocation对象
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            //获取接口名
            String interfaceName = invocation.getInterfaceName();

            //从本地注册中获取实现类
            Class classImpl = LocalRegister.get(interfaceName, "1.0");
            //获取调用方法
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            //利用反射执行方法
            String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());

            //将方法执行返回的结果写入response
            IOUtils.write(result, resp.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
