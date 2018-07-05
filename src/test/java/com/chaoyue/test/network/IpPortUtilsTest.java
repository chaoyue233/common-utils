package com.chaoyue.test.network;

import com.chaoyue.network.IpPortUtils;
import com.chaoyue.test.AbstractSpringContextTest;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.management.MalformedObjectNameException;

public class IpPortUtilsTest extends AbstractSpringContextTest {

    @Test
    public void getIpAddressAndPortTest() throws MalformedObjectNameException {
        String result = IpPortUtils.getIpAddressAndPort();
        System.out.println(result);
        Assert.notNull(result);
    }
}
