package model.statics;
import com.google.common.base.Joiner;

/**
 * 业务逻辑异常，自定义的业务逻辑异常需要 extend 此类
 */
public class JBoyException extends RuntimeException {

    private String error;
    private Object[] args;
    private boolean printTrace = false;

    public JBoyException(String error, Object... args) {
        super(error + (args.length > 0 ? ("::" + Joiner.on("|").skipNulls().join(args)) : ""));
        this.error = error;
        this.args = args;
    }

    public String getError() {
        return error;
    }

    public Object[] getArgs() {
        return args;
    }

    public boolean isPrintTrace() {
        return printTrace;
    }

    public JBoyException printTrace(boolean printTrace) {
        this.printTrace = printTrace;
        return this;
    }
}
