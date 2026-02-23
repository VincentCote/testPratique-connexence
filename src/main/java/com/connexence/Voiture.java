package com.connexence;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Voiture extends BaseEntity {
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView(Vues.Complet.class)
    @ManyToOne
    public Concessionnaire concessionnaire;

    @JsonView(Vues.Simple.class)
    public String marque;

    @JsonView(Vues.Simple.class)
    public String modele;

    @JsonView(Vues.Simple.class)
    public Integer annee;
    
    @JsonView(Vues.Simple.class)
    public Integer prix;

    @Transient
    @JsonView(Vues.Simple.class)
    public Long getIdConcessionnaire() {
        return concessionnaire.id;
    }
}
