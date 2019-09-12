package resource;

import model.RequestModel.PlayerRM;
import service.PlayerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("players")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PlayerResource {
    @Inject
    public PlayerResource(PlayerService service) {
        this.service = service;
    }

    private PlayerService service;

    @GET
    public Response get(){
        return Response
                .ok(this.service.findAll())
                .build();
    }

    @POST
    @Transactional
    public Response create(PlayerRM playerRM){
        return Response
                .ok(this.service.saveOne(playerRM))
                .build();
    }

}
