package org.example.userauthservice_begmay2025.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class UserSession extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;


    public UserSession() {
        this.setState(State.ACTIVE);
        this.setCreatedAt(new Date());
        this.setLastUpdatedAt(new Date());
    }



}
