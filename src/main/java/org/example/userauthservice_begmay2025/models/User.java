package org.example.userauthservice_begmay2025.models;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class User extends BaseModel {


    private String email;

    private String password;


}
