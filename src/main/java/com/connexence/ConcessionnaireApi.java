package com.connexence;

import com.fasterxml.jackson.annotation.JsonView;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/concessionnaires")
public class ConcessionnaireApi {
    @GET
    @JsonView(Vues.Simple.class)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Concessionnaire> getConcessionnaire() {
        return Concessionnaire.listAll();
    }

    @Path("/{id}")
    @GET
    @JsonView(Vues.Simple.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Concessionnaire getConcessionnaire(@PathParam("id") String id) {
        return Concessionnaire.findById(id);
    }

    @POST
    @JsonView(Vues.Simple.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Concessionnaire creerConcessionnaire(Concessionnaire concessionnaire) {
        concessionnaire.persist();
        return concessionnaire;
    }

    @Path("/{id}")
    @PUT
    @JsonView(Vues.Simple.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Concessionnaire majConcessionnaire(Long id, Concessionnaire concessionnaire) {
        Concessionnaire entity = Concessionnaire.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.nom = concessionnaire.nom;
        return entity;
    }

    @Path("/{id}")
    @DELETE
    @Transactional
    public void supprimerConcessionnaire(Long id) {
        Concessionnaire entity = Concessionnaire.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    @Path("/{idConcessionnaire}/voitures")
    @GET
    @JsonView(Vues.Simple.class)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Voiture> getVoitures(@PathParam("idConcessionnaire") String id) {
        Concessionnaire entity = Concessionnaire.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity.voitures;
    }

    @Path("/{idConcessionnaire}/voitures")
    @POST
    @JsonView(Vues.Simple.class)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Voiture creerVoiture(Long idConcessionnaire, Voiture voiture) {
        Concessionnaire concessionnaire = Concessionnaire.findById(idConcessionnaire);
        if (concessionnaire == null) {
            throw new NotFoundException();
        }
        voiture.concessionnaire = concessionnaire;
        voiture.persist();
        return voiture;
    }


}
