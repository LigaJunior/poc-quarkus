package resource;

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
    public JunkFoodResource(JunkFoodService service) {
        this.service = service;
    }

    private JunkFoodService service;

    @GET
    public Response get() {
        return Response.ok(this.service.findAll()).status(200).build();
    }

    @POST
    @Transactional
    public Response create(JunkFoodRM foodRM) {
        return Response.ok(this.service.saveOne(foodRM)).status(200).build();
    }
}
