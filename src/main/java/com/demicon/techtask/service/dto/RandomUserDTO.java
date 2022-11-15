package com.demicon.techtask.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.demicon.techtask.domain.RandomUser;

public class RandomUserDTO {
    
    private Long id;

    private String title;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 10)
    private String gender;

    private String country;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    @Size(max = 2)
    private String nat;

    public RandomUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public RandomUserDTO(RandomUser randomUser) {
        this.id = randomUser.getId();
        this.title = randomUser.getTitle();
        this.firstName = randomUser.getFirstName();
        this.lastName = randomUser.getLastName();
        this.gender = randomUser.getGender();
        this.country = randomUser.getCountry();
        this.email = randomUser.getEmail();
        this.nat = randomUser.getNat();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNat() {
        return this.nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", country='" + getCountry() + "'" +
            ", email='" + getEmail() + "'" +
            ", nat='" + getNat() + "'" +
            "}";
    }

}
