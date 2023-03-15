package com.djn.loadbalance;

import com.djn.common.URL;

import java.util.List;
import java.util.Random;

/**
 * Name: LoadBalance
 * Description:
 * Copyright: Copyright (c) 2023 MVWCHINA All rights Reserved
 * Company: 江苏医视教育科技发展有限公司
 *
 * @author 丁佳男
 * @version 1.0
 * @since 2023-03-15 09:46
 */
public class LoadBalance {

    public static URL random(List<URL> urls) {
        Random random = new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);
    }
}
