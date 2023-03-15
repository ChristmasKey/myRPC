package com.djn.register;

import com.djn.common.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Name: MapRemoteRegister
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-14 17:10
 */
public class MapRemoteRegister {

    private static Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName, URL url) {
        List<URL> list = map.get(interfaceName);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(url);

        map.put(interfaceName, list);

        saveFile();
    }

    public static List<URL> get(String interfaceName) {
        map = getFile();
        return map.get(interfaceName);
    }

    public static void saveFile() {
        try {
            //为了实现Provider注册的服务能够被Consumer共享，我们可以将注册的服务缓存到Redis中
            //这里由于我们的项目都是在同一台机器上，所以用文件代替Redis实现
            FileOutputStream fileOutputStream = new FileOutputStream("/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<URL>> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
