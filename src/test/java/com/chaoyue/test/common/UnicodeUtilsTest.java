package com.chaoyue.test.common;

import com.chaoyue.common.UnicodeUtils;
import org.junit.Test;
import org.springframework.util.Assert;

public class UnicodeUtilsTest {
    @Test
    public void encodeAndDecodeTest() {
        String source = "chaoyue_test";
        String encode = UnicodeUtils.unicode(source);
        System.out.println(encode);
        Assert.notNull(encode);
        String decode = UnicodeUtils.decodeUnicode(encode);
        Assert.notNull(decode);
        Assert.isTrue(decode.endsWith(source));
        System.out.println(decode);
    }
}
