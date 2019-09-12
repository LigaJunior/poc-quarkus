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
    @Transactional
    @Path("/{playerID}/{sprintID}")
    public Response addToSprint(@PathParam("playerID") Long playerId,@PathParam("sprintID") Long sprintId){
        return Response
                .ok(this.service.addToSprint(playerId,sprintId))
                .build();
    }

    @GET
    @Transactional
    @Path("all-player-rank")
    public Response getPlayerRankOfAllSprints(){
        return Response
                .ok(this.service.getPlayerRankOfAllSprints())
                .build();
    }

    @GET
    @Transactional
    @Path("sprint-ranked-food")
    public Response getSprintRankedJunkFood(){
        return Response
                .ok(this.service.getSprintRankedJunkFood())
                .build();
    }

    @GET
    @Transactional
    @Path("sprint-rank-of-food-consumption")
    public Response getSprintRankOfFoodConsumption(){
        return Response
                .ok(this.service.getSprintRankOfFoodConsumption())
                .build();
    }


}
