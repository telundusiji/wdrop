package site.teamo.wdrop.core.exception;

public enum WDropErrorCode {
    CLASS_LOADER_ERROR(10001,"WDropClassLoader error!"),
    CONTEXT_ERROR(20001,"WDropContext error!"),
    CONTAINER_ERROR(30001,"WDropContainer error!"),
    CONTROLLER_ERROR(40001,"WDropController error!");
    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    WDropErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
