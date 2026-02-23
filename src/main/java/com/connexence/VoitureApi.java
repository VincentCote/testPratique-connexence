package com.connexence;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/voitures")
public class VoitureApi {
    @GET
    @Path("/{id}")
    @JsonView(Vues.Simple.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Voiture getVoiture(Long id) {
        return Voiture.findById(id);
    }

    @PUT
    @Path("/{id}")
    @JsonView(Vues.Simple.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Voiture updateVoiture(Long id, Voiture voiture) {
        Voiture entity = Voiture.findById(id);
        if(entity == null){
            throw new NotFoundException();
        }

        entity.annee = voiture.annee;
        entity.marque = voiture.marque;
        entity.modele = voiture.modele;
        entity.prix = voiture.prix;
        return entity;
    }

    @Path("/{id}")
    @DELETE
    @Transactional
    public void supprimerVoiture(Long id) {
        Voiture entity = Voiture.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
