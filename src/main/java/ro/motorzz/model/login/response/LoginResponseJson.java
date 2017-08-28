package ro.motorzz.model.login.response;

public class LoginResponseJson {

    private String token;
    private String email;

    public LoginResponseJson(){

    }

    public LoginResponseJson(String token, String email){
        this
                .setToken(token)
                .setEmail(email);
    }

    public String getToken() {
        return token;
    }

    public LoginResponseJson setToken(String token) {
        this.token = token;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public LoginResponseJson setEmail(String email) {
        this.email = email;
        return this;
    }
}
