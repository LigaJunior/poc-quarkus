package resource;

import model.RequestModel.ConsumptionHistoryRM;
import model.ViewModel.ConsumptionHistoryVM;
import service.ConsumptionHistoryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("consumption-history")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ConsumptionHistoryResource {
    @Inject
    public ConsumptionHistoryResource(ConsumptionHistoryService service) {
        this.service = service;
    }

    private ConsumptionHistoryService service;

    @GET
    public Response get(){
        List<ConsumptionHistoryVM> consumption = this.service.findAll();
        return Response.ok(consumption).build();
    }

    @POST
    @Transactional
    public Response create(ConsumptionHistoryRM consumptionHistoryRM){
        ConsumptionHistoryVM consumption = this.service.saveOne(consumptionHistoryRM);
        return Response.ok(consumption).build();
    }
}
