package controller;

import model.RequestModel.SprintRM;
import model.Sprint;
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
    SprintService service;

    @GET
    public Response get() {
        Sprint[] sprints = this.service.findAll();
        return Response.ok(sprints).status(200).build();
    }

    @POST
    @Transactional
    public Response create(SprintRM sprintRM) {
        Sprint sprint = this.service.saveOne(sprintRM);
        return Response.ok(sprint).status(200).build();
    }
}
