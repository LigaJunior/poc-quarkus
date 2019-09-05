package controller;

import model.JunkFood;
import model.RequestModel.JunkFoodRM;
import service.JunkFoodService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("junk-foods")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class JunkFoodResource {
    @Inject
    JunkFoodService service;

    @GET
    public Response get() {
        JunkFood[] foods = this.service.findAll();
        return Response.ok(foods).status(200).build();
    }

    @POST
    @Transactional
    public Response create(JunkFoodRM foodRM) {
        JunkFood food = this.service.saveOne(foodRM);
        return Response.ok(food).status(200).build();
    }
}
