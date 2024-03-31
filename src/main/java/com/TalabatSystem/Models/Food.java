package com.TalabatSystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private Category foodcategory;

    @Column(length = 1000)
    @ElementCollection
    private List<String> iamges;

    private boolean avialable;

    @ManyToOne
    private Restaurant restaurant;

    private boolean isVegeterian;
    private boolean isSeasonal;

    @ManyToMany
    private List<IngrediantsItem> ingrediants = new ArrayList<>();

    private Date creationDate;

}
