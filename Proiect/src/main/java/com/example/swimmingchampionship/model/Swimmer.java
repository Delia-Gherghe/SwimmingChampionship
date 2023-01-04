package com.example.swimmingchampionship.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@ApiModel(value = "Swimmer details", description = "Full information stored for a swimmer")
public class Swimmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the swimmer", required = true, example = "1")
    private int id;

    @NotBlank(message = "First name can't be empty or null!")
    @ApiModelProperty(notes = "The first name of the swimmer", required = true, example = "David")
    private String firstName;

    @NotBlank(message = "Last name can't be empty or null!")
    @ApiModelProperty(notes = "The last name of the swimmer", required = true, example = "Popovici")
    private String lastName;

    @ApiModelProperty(notes = "The swimmer's date of birth", example = "2004-09-15")
    private java.sql.Date birthday;

    @NotBlank(message = "Country can't be empty or null!")
    @ApiModelProperty(notes = "The country of the swimmer", required = true, example = "Romania")
    private String country;

    public Swimmer() {
    }

    public Swimmer(String firstName, String lastName, java.sql.Date birthday, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.country = country;
    }

    public Swimmer(int id, String firstName, String lastName, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Swimmer swimmer = (Swimmer) o;
        return id == swimmer.id && firstName.equals(swimmer.firstName) && lastName.equals(swimmer.lastName) && Objects.equals(birthday, swimmer.birthday) && country.equals(swimmer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday, country);
    }

}
