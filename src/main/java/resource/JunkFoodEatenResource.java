package resource;

import model.JunkFoodEaten;
import model.RequestModel.JunkFoodRM;
import service.JunkFoodEatenService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("junk-foods-eaten")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class JunkFoodEatenResource {
    @Inject
    JunkFoodEatenService service;

    @GET
    public Response get() {
        JunkFoodEaten[] foods = this.service.findAll();
        return Response.ok(foods).status(200).build();
    }

    @POST
    @Transactional
    public Response create(JunkFoodEaten junkFoodEaten) {
        JunkFoodEaten food = this.service.saveOne(junkFoodEaten);
        return Response.ok(food).status(200).build();
    }
}