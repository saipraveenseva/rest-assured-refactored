package Thirteen_Important;

public class LoginResponse {

    // These are the values generated in the response of the Login request. 4
    // We create variables of those and the Getters Setters

    String token;
    String userId;
    String message;

    // Alt + Insert to generate Getters and Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
