package com.emerging.framework.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/***
 * Message tools (International feature not implemented)
 * 
 * @ClassName: MessageUtils
 * @author mark
 */
@Component
public class MessageUtils {
    private static MessageSource messageSource;

    /**
     * Set the message source, the method of using Spring MessageSource
     * 
     * @param messageSource
     */
    @Autowired(required = true)
    public void setMessageSource(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    /**
     * According to the news Key news
     * 
     * @param Key
     * @return The message content
     */
    public static String getMessage(String key) {
        return getMessage(key, null);
    }

    /**
     * According to the news Key to get news, can pass parameters
     * 
     * @param Key
     * @param args
     *            The parameter array parameter format: for example: Msg: user {0}{1}{2}...
     * @return The message content
     */
    public static String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, null);
    }
}
