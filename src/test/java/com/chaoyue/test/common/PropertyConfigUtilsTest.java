package com.chaoyue.test.common;

import com.chaoyue.test.AbstractSpringContextTest;
import com.chaoyue.common.PropertyConfigUtils;
import org.junit.Test;
import org.springframework.util.Assert;

public class PropertyConfigUtilsTest extends AbstractSpringContextTest {

    @Test
    public void getPropTest() {
        String name = PropertyConfigUtils.getProp("chaoyue.test.name");
        System.out.println(name);
        Assert.notNull(name);
    }
}
