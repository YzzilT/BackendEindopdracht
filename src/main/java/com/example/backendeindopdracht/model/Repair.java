package com.example.backendeindopdracht.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repairs")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repairNumber;
    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @JsonIgnore
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    private String problemDescription;
    private long userid;
    private long productid;
    private int fileId;


}
