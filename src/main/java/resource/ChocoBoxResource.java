package resource;

import model.RequestModel.ChocoBoxRM;
import service.ChocoBoxService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
@Path("choco")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ChocoBoxResource {
    @Inject
    public ChocoBoxResource(ChocoBoxService service) {
        this.service = service;
    }
    private ChocoBoxService service;

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

    @PATCH
    @Transactional
    @Path("/{playerId}/")
    public Response update(@PathParam("playerId") Long playerId){
        return Response.ok(this.service.updateChoco(playerId)).build();
    }

    @PATCH
    @Transactional
    @Path("/pay/{chocoId}")
    public Response payOne(@PathParam("chocoId") Long chocoId){
        this.service.payOne(chocoId);
        return Response.ok().build();
    }
}
