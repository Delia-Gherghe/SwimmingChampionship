package com.example.swimmingchampionship.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@ApiModel(value = "Sponsor details", description = "Full information stored for a sponsor")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The id of the sponsor", required = true, example = "1")
    private int id;

    @NotBlank(message = "Sponsor name can't be empty or null!")
    @ApiModelProperty(notes = "The name of the company", required = true, example = "Arena")
    private String name;

    @NotBlank(message = "Sponsor site can't be empty or null!")
    @ApiModelProperty(notes = "The link for the site of the company", required = true, example = "https://www.arenasport.com/")
    private String siteLink;

    @ManyToMany
    @JoinTable(name = "contract", joinColumns = @JoinColumn(name = "sponsor_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    @ApiModelProperty(notes = "The swimmers the company is sponsoring")
    private List<Swimmer> swimmerList = new ArrayList<>();

    public Sponsor() {
    }

    public Sponsor(String name, String siteLink, List<Swimmer> swimmerList) {
        this.name = name;
        this.siteLink = siteLink;
        this.swimmerList = swimmerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public List<Swimmer> getSwimmerList() {
        return swimmerList;
    }

    public void setSwimmerList(List<Swimmer> swimmerList) {
        this.swimmerList = swimmerList;
    }
}
