//1 - Package (grupo de classes)
package petstore;

//2 - Library
import io.restassured.specification.Argument;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

//3 - Class
public class Pet {
    //3.1 - Attributes/characteristics
    String uri = "https://petstore.swagger.io/v2/pet"; //Pet entity address

    //3.2 - Methods (actions that don't return value(s)) and Functions (when value(s) are returned)
    public String readJson(String jsonPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(jsonPath)));
    }

    //Include - Create - Post
    @Test  //identifies the method of function as a test for TestNG
    public void includePet() throws IOException {
        String jsonBody = readJson("db/pet1.json");

        //Gherkin Syntax
        //Given - When - Then

        given()
                .contentType("application/json") // in more recent apps REST API - and in older ones "text/xml"
                .log().all()
                .body(jsonBody)
        .when() //including Pet data
                .post(uri)
        .then()
                .log().all()
                .statusCode(200) //status code with success
                .body("name", is("Max"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("STA"))
        ;

    }


}