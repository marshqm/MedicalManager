package model.statics;
import static model.statics.HTTP.*;

public enum CommonErrorCode implements ErrorCode{
    // 请求参数
    BAD_REQUEST("1000", SC_400_BAD_REQUEST),
    FIELD_MISSING("1001", SC_422_UNPROCESSABLE_ENTITY),
    FIELD_INVALID("1002", SC_422_UNPROCESSABLE_ENTITY),

    // 授权认证
    UNAUTHORIZED("2000", SC_401_UNAUTHORIZED),
    RATE_LIMIT("2001", SC_403_FORBIDDEN),
    NOT_FOUND("2002", SC_404_NOT_FOUND),

    // 服务异常
    SERVER_ERROR("5000", SC_500_INTERNAL_ERROR),
    SERVER_NPE("5001", SC_500_INTERNAL_ERROR),
    DATABASE_ERROR("5002", SC_500_INTERNAL_ERROR),
    DATABASE_ACQUIRE_LOCK_ERROR("5003", SC_500_INTERNAL_ERROR),
    DATABASE_DUPLICATE_KEY_ERROR("5004", SC_500_INTERNAL_ERROR),
    DATABASE_DATA_INTEGRITY_ERROR("5005", SC_500_INTERNAL_ERROR),
    SERVICE_ERROR("5100", SC_500_INTERNAL_ERROR),
    SERVICE_ERROR_NETWORK("5101", SC_500_INTERNAL_ERROR),
    SERVICE_ERROR_DATABASE("5102", SC_500_INTERNAL_ERROR),

    // 业务异常
    INVALID("6000", SC_200_OK),
    MISSING("6001", SC_200_OK),
    ALREADY_EXISTS("6002", SC_200_OK),
    NOT_EXIST("6003", SC_200_OK),
            ;

    private String code;
    private int httpStatus;

    CommonErrorCode(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getValue() {
        return code;
    }
}
