package com.chaoyue.test.message;

import com.chaoyue.message.MessageUtils;
import com.chaoyue.test.AbstractSpringContextTest;
import org.junit.Test;
import org.springframework.util.Assert;

public class MessageUtilsTest extends AbstractSpringContextTest {

    @Test
    public void getLocaleMessageTest() {
        String code = "0";
        String result = MessageUtils.getLocaleMessage(code);
        System.out.println(result);
        Assert.notNull(result);
    }
}
