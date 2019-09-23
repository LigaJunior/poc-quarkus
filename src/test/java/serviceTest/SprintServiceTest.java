package serviceTest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import service.SprintService;
import static io.restassured.RestAssured.given;
import javax.inject.Inject;

@QuarkusTest
public class SprintServiceTest {

    @Inject
    SprintService service;


}
