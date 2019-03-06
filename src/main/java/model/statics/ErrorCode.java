package model.statics;

public interface ErrorCode extends Valuable {

    ErrorCode SUCCESS = () -> "10000";
    ErrorCode FAIL = () -> "500";

//    String getCode();

    default int getHttpStatus() {
        return 200;
    }
}
