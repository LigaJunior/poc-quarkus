package resourceTest;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import model.RequestModel.SprintRM;
import model.Sprint;
import org.junit.jupiter.api.Test;
import service.SprintService;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


@QuarkusTest
@Transactional
public class SprintResourceTest {
    @Inject
    SprintService service;
    LocalDate date;


     @Test
    public void getAll() {
        given()
                .when().get("sprints")
                .then()
                .statusCode(200)
                .body(notNullValue());

    }

        @Test
    public void testGreetingEndpoint() {
            date = LocalDate.now();
            SprintRM sprint = new SprintRM();
            sprint.setName("Test3");
            sprint.setSprintNumber(Long.parseLong("1"));
            sprint.setStartDate(date);
            sprint.setEndDate(date.plusDays(15));
            service.saveOne(sprint);
        given()
                .when().get("active")
                .then()
                .statusCode(200)
                .body(is("hello "));
    }

//    @Test
//    public void testGreetingEndpoint() {
//        String uuid = UUID.randomUUID().toString();
//        given()
//                .pathParam("name", uuid)
//                .when().get("/hello/greeting/{name}")
//                .then()
//                .statusCode(200)
//                .body(is("hello " + uuid));
//    }
}
