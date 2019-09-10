package resource;

import model.RequestModel.SprintRM;
<<<<<<< HEAD
import model.ViewModel.ExtendSprintVM;
=======
import model.Sprint;
>>>>>>> dev-fernando
import service.SprintService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
<<<<<<< HEAD
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
=======
import javax.json.bind.annotation.JsonbDateFormat;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
>>>>>>> dev-fernando

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

<<<<<<< HEAD
    @Path("active")
    @GET
    public Response findActiveSprints(){
        return Response.ok( this.service.findActiveSprints()).status(200).build();
    }

    @Path("active/player-rank")
    @GET
    public Response getPlayerRankForActiveSprint(){
        return Response.ok( this.service.getPlayerRankForActiveSprint()).status(200).build();
    }

    @Path("extend")
    @PUT
    @Transactional
    public Response findActiveSprints(ExtendSprintVM endDate){
        return Response.ok( this.service.extendActiveSprintDeadLine(endDate)).status(200).build();
    }

    @PATCH
=======
    @Path("/{endDate}")
    @Transactional
    @GET
    public Response findActiveSprints(@PathParam("endDate")String endDate){
        return Response.ok( this.service.findActiveSprints(endDate)).status(200).build();
    }

    @GET
>>>>>>> dev-fernando
    @Transactional
    @Path("/{playerID}/{sprintID}")
    public Response addToSprint(@PathParam("playerID") Long playerId,@PathParam("sprintID") Long sprintId){
        return Response
                .ok(this.service.addToSprint(playerId,sprintId))
                .build();
    }
}
