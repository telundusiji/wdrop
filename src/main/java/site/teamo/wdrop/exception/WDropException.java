package site.teamo.wdrop.exception;

import lombok.Data;

@Data
public class WDropException extends RuntimeException {

    private Integer errorCode;

    private String errorMsg;

    private String description;

    public WDropException(WDropErrorCode wDropErrorCode, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = wDropErrorCode.getCode();
        this.description = wDropErrorCode.getDescription();
    }

    public WDropException(WDropErrorCode wDropErrorCode, String errMsg, Throwable e) {
        super(errMsg, e);
        this.errorMsg = errMsg;
        this.errorCode = wDropErrorCode.getCode();
        this.description = wDropErrorCode.getDescription();
    }
}
