package nz.co.mircle.v1.lib.failedResponse.model;

public class FailedResponse {
    private String errorMessage;

    public FailedResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
