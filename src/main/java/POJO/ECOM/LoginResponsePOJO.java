/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:19-07-2025
 * Time:14:26
 */
package POJO.ECOM;

public class LoginResponsePOJO {
    private String token;
    private String userId;
    private String message;

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
