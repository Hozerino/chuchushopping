package ws.domain.user;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {
    @JsonProperty
    private String name;
        @Id
    @JsonProperty
    private String CPF;

    @JsonProperty
    private String cellphone;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty
    private List<String> likes;

    public User(String name, String CPF, String cellphone, List<String> likes) {
        this.name = name;
        this.CPF = CPF;
        this.cellphone = cellphone;
        this.likes = likes;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
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
