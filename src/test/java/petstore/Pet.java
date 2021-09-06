//1 - Package (grupo de classes)
package petstore;

//2 - Library
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
    @Test(priority = 1)  //identifies the method of function as a test for TestNG
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
                .body("category.name", is("RR20210906PRT"))
                .body("tags.name", contains("STA"))
        ;

    }

    @Test(priority = 2)  //identifies the method of function as a test for TestNG
    public void consultPet(){
        //Attributes
        String petId = "1904090598";
        String token =
        //Get Pet from petId
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Max"))
                .body("status", is("available"))
                .body("category.name", is("RR20210906PRT"))
        .extract()
                .path("category.name")
        ;

        System.out.println("The token is " + token);
    }

    @Test(priority = 3)
    public void editPet () throws IOException {
        String jsonBody = readJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Max"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 4)
    public void deletePet() {
        String petId = "1904090598";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }
}