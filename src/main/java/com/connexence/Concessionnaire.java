package com.connexence;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Concessionnaire extends BaseEntity {
    @JsonView(Vues.Simple.class)
    public String nom;

    @JsonView(Vues.Complet.class)
    @OneToMany(mappedBy = "concessionnaire")
    public List<Voiture> voitures = new ArrayList<>();

    @JsonView(Vues.Simple.class)
    @Transient
    // Transient pour ne pas que ça persiste dans la BD 
    public int nombreVoitures() {
        return voitures.size();
    }
}
