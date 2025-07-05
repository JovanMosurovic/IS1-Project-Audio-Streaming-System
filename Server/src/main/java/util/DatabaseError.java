package util;

import java.io.Serializable;

public class DatabaseError implements Serializable {
    private String errorMessage;
    private int errorCode;
    
    public DatabaseError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}