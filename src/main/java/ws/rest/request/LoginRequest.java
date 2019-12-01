package ws.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty
    private String CPF;

    @JsonProperty
    private String password;

    public String getCPF() {
        return CPF;
    }

    public String getPassword() {
        return password;
    }
}
