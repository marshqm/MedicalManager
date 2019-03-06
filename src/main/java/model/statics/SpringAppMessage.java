package model.statics;

import com.google.common.base.Joiner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 根据 code，从 {@link #MESSAGE_FILE} 中加载以error 开头的消息，
 * <p> 例如：code 为1024，从 messages.properties 中查询 error.1020=xxx
 */

@Component
public class SpringAppMessage {
    static final Locale locale = Locale.ROOT;
    static final String MESSAGE_FILE = "classpath:messages";
    static final String MESSAGE_PREFIX = "error.";

    private MessageSource messageSource;

    public SpringAppMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(ErrorCode errorCode, Object... args) {
        String defaultMessage = errorCode.getClass().getSimpleName();
        if(errorCode.getClass().isEnum()) {
            defaultMessage = errorCode.toString();
        }
        String message = messageSource.getMessage(MESSAGE_PREFIX + errorCode.getValue(), null, defaultMessage, locale);

        return (args != null && args.length > 0) ?
                (message + ": " + Joiner.on(",").skipNulls().join(args)) : message;
    }

    /**
     * @return code 不存在返回 null
     */
    public String getMessage(String code) {
        return getMessage(code, null, null);
    }

    public String getMessage(String code, String[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}