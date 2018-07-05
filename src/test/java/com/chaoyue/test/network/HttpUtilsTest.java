package com.chaoyue.test.network;

import com.chaoyue.network.HttpUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpUtilsTest {

    @Test
    public void getTest() {
        String url = "http://www.baidu.com";
        Map<String, String> param = new HashMap<>();
        String result = HttpUtils.get(url, param);
        System.out.println(result);
        Assert.assertNotNull(result);
    }
}
