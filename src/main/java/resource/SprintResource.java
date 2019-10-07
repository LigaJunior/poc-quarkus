package resource;

import model.RequestModel.SprintRM;
import model.ViewModel.ExtendSprintVM;
import service.SprintService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("sprints")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class SprintResource {
    @Inject
    public SprintResource(SprintService service) {
        this.service = service;
    }

    private SprintService service;

    @GET
    public Response get() {
        return Response.ok(this.service.findAll()).status(200).build();
    }

    @GET
    @Path("{sprintID}")
    public Response getById(@PathParam("sprintID") Long sprintId) {
        return Response.ok(this.service.findById(sprintId)).status(200).build();
    }

    @POST
    @Transactional
    public Response create(SprintRM sprintRM) {
        return Response.ok(this.service.saveOne(sprintRM)).status(200).build();
    }

    @GET
    @Path("active")
    public Response findActiveSprints() {
        return Response.ok(this.service.findActiveSprints()).status(200).build();
    }

    @GET
    @Path("active/player-rank")
    public Response getPlayerRankForActiveSprint() {
        return Response.ok(this.service.getPlayerRankForActiveSprint()).status(200).build();
    }

    @PATCH
    @Transactional
    @Path("extend")
    public Response extendActiveSprintDeadLine(ExtendSprintVM endDate) {
        return Response.ok(this.service.extendActiveSprintDeadLine(endDate)).status(200).build();
    }

    @PATCH
    @Transactional
    @Path("/{playerID}/{sprintID}")
    public Response addPlayer(@PathParam("playerID") Long playerId, @PathParam("sprintID") Long sprintId) {
        return Response
                .ok(this.service.addPlayer(playerId, sprintId))
                .build();
    }

    @GET
    @Path("most-junk")
    public Response getMostJunk() {
        return Response
                .ok(this.service.getMostJunkSprint())
                .build();
    }

    @GET
    @Path("rank")
    public Response getFoodConsumptionRank() {
        return Response
                .ok(this.service.getSprintRank())
                .build();
    }


}
