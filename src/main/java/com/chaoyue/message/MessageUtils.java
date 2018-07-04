package com.chaoyue.message;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class MessageUtils implements MessageSourceAware {

    private static MessageSourceAccessor messageSourceAccessor;

    @Override
    public void setMessageSource(MessageSource messageSource) {
//		messageSourceAccessor = new MessageSourceAccessor(messageSource, Locale.US);
        messageSourceAccessor = new MessageSourceAccessor(messageSource, Locale.CHINA);
    }

    public static String getLocaleMessage(String code) {
        String message = getLocaleMessage(code, new Object[]{});
        if (!code.equals(MessageCode.SUCCESS)) {
            message += " hint: " + UUID.randomUUID();
        }
        return message;
    }

    /**
     * @param code 前端的错误码
     * @return 返回前端拼接完整的消息
     */
    private static String getLocaleMessage(String code, Object... args) {
        return messageSourceAccessor.getMessage(code, args, code);
    }
}
