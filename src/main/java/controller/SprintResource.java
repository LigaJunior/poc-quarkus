package controller;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import model.RequestModel.SprintRM;
import model.Sprint;
import org.hibernate.criterion.BetweenExpression;
import org.hibernate.hql.internal.ast.tree.BetweenOperatorNode;
import service.SprintService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        Sprint[] sprints = this.service.findAll();
        return Response.ok(sprints).status(200).build();
    }

    @POST
    @Transactional
    public Response create(SprintRM sprintRM) {
        Sprint sprint = this.service.saveOne(sprintRM);
        return Response.ok(sprint).status(200).build();
    }
//    @Path("data")
//    @GET
//    @Transactional
//    public Response getActualSPrint(){
//        Sprint[] sprints = this.service.findActualSprint();
//        return Response.ok(sprints).status(200).build();
//    }

    @Path("data")
    @GET
    @Transactional
    public Response persist(){
        Sprint[] sprint = this.service.findActualSprint().toArray(new Sprint[0]);
        return Response.ok(sprint).status(200).build();
    }
}
