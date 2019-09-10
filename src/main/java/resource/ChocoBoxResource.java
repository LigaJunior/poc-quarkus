package resource;

import model.RequestModel.ChocoBoxRM;
import service.ChocoBoxService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
@Path("choco")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ChocoBoxResource {
    @Inject
    ChocoBoxService service;

    @GET
    public Response get(){
        return Response
                .ok(this.service.findAll())
                .build();
    }

    @POST
    @Transactional
    public Response create(ChocoBoxRM chocoBoxRM){
        return Response
                .ok(this.service.saveOne(chocoBoxRM))
                .build();
    }

    @PUT
    @Transactional
    @Path("/{playerId}/")
    public Response update(@PathParam("playerID") Long playerId){
        return Response.ok(this.service.updateChoco(playerId)).build();
    }


}
