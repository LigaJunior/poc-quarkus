package resource;

import model.RequestModel.SprintRM;
import model.Sprint;
import service.SprintService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("sprints")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SprintResource {
    @Inject
    SprintService service;

    @GET
    public Response get() {
        return Response.ok(this.service.findAll()).status(200).build();
    }

    @POST
    @Transactional
    public Response create(SprintRM sprintRM) {
        return Response.ok(this.service.saveOne(sprintRM)).status(200).build();
    }

    @Path("active")
    @GET
    public Response findActiveSprints(){
        return Response.ok( this.service.findActiveSprints()).status(200).build();
    }

    @GET
    @Transactional
    @Path("/{playerID}/{sprintID}")
    public Response addToSprint(@PathParam("playerID") Long playerId,@PathParam("sprintID") Long sprintId){
        return Response
                .ok(this.service.addToSprint(playerId,sprintId))
                .build();
    }

    @Path("/{endDate}")
    @Transactional
    @GET
    public Response findActiveSprints(@PathParam("endDate")String endDate){
        return Response.ok( this.service.changeSprintDeadLine(endDate)).status(200).build();
    }
}

