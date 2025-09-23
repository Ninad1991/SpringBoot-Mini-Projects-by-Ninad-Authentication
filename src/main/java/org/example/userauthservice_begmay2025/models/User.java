package org.example.userauthservice_begmay2025.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Entity
public class User extends BaseModel {

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    private String password;

    public User() {
        this.setState(State.ACTIVE);
        this.setCreatedAt(new Date());
        this.setLastUpdatedAt(new Date());
    }

}
