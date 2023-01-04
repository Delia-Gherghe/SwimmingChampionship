package com.example.swimmingchampionship.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@ApiModel(value = "Swimmer request", description = "The details needed to add a new swimmer")
public class SwimmerRequest {
    @NotEmpty(message = "First name must not be empty!")
    @ApiModelProperty(notes = "The first name of the swimmer", required = true, example = "David")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty!")
    @ApiModelProperty(notes = "The last name of the swimmer", required = true, example = "Popovici")
    private String lastName;

    @ApiModelProperty(notes = "The swimmer's date of birth", example = "2004-09-15")
    private java.sql.Date birthday;

    @NotEmpty(message = "Country must not be empty!")
    @ApiModelProperty(notes = "The country of the swimmer", required = true, example = "Romania")
    private String country;

    public SwimmerRequest(String firstName, String lastName, Date birthday, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.country = country;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
