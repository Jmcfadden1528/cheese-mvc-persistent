package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Entity //flag that tells spring boot we want to manage this class in DB
public class Cheese {


    @Id // says that this should be a primary key in DB
    @GeneratedValue // tells hibernate to generate and manage this value
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty ya dumb dumb")
    private String description;

    @ManyToOne
    private Category category;

    private int rating = 5;

    @ManyToMany(mappedBy="cheeses")
    private List<Menu> menus;

    public Cheese() {  }

    public Cheese(String name, String description, int rating, Category category) {

        this.name = name;
        this.description = description;
        this.rating = rating;
        this.category = category;

    }



    public int getId() { return id; }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }


}
