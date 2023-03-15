package com.djn.protocol;

import com.djn.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Name: HttpClient
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-14 09:19
 */
public class HttpClient {

    public String send(String hostname, Integer port, Invocation invocation) throws IOException {
        //可以根据用户的配置去定义用什么样的方式发送Http请求

        //这里使用最原始的方式
        URL url = new URL("http", hostname, port, "/");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        //配置
        OutputStream outputStream = httpURLConnection.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        oos.writeObject(invocation);
        oos.flush();
        oos.close();

        InputStream inputStream = httpURLConnection.getInputStream();
        return IOUtils.toString(inputStream);
    }
}
