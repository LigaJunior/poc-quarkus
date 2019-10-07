package resource;

import model.Player;
import model.RequestModel.PlayerRM;
import service.PlayerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static model.ViewModel.VMConverter.convertPlayer;
import static model.ViewModel.VMConverter.convertPlayers;

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
    public Response get() {
        List<Player> sourcePlayerList = this.service.findAll();
        return Response
                .ok(convertPlayers(sourcePlayerList))
                .build();
    }

    @GET
    @Path("/unallocated")
    public Response getUnallocated() {
        return Response
                .ok(convertPlayers(this.service.findUnallocated()))
                .build();
    }

    @POST
    @Transactional
    public Response create(PlayerRM playerRM) {
        return Response
                .ok(convertPlayer(this.service.saveOne(playerRM)))
                .build();
    }

    @PATCH
    @Transactional
    @Path("/{playerId}")
    public Response delete(@PathParam("playerId") Long playerId) {
        return Response.ok(convertPlayers(this.service.deleteOne(playerId)))
                .build();
    }

    @GET
    @Path("rank")
    public Response getPlayerRank() {
        return Response
                .ok(this.service.getPlayerRank())
                .build();
    }
}
