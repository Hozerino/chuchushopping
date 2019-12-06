package ws.domain.user;

import java.util.List;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty
    private String name;

    @JsonProperty("cpf")
    @NotNull
    private String cpf;

    @JsonProperty
    private String cellphone;

    @JsonProperty
    private List<String> likes;

    @JsonProperty
    private String password;

    public User(String name, String cpf, String cellphone, List<String> likes, String password) {
        this.name = name;
        this.cpf = cpf;
        this.cellphone = cellphone;
        this.likes = likes;
        this.password = password;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
