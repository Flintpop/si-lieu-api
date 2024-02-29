import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LieuxIdApiTests {

  @Test
  public void testGetLocationById() {
    int testId = 2; // Assurez-vous que cet ID existe dans votre base de données ou mock
    given()
            .pathParam("id", testId)
            .when()
            .get("http://localhost:8082/lieux?id={id}")
            .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("id", equalTo(testId))
            .log().body();
  }

  @Test
  public void testUpdateLocation() {
    int testId = 1;
    String updatedLocationJson = """
                {
                    "id": 1,
                    "nom": "Lieu Mis à Jour",
                    "adresse": "321 Rue Exemple",
                    "capaciteAccueil": 200
                }
                """;

    given()
            .contentType("application/json")
            .body(updatedLocationJson)
            .when()
            .put("http://localhost:8082/lieux?id=" + testId)
            .then()
            .log().ifValidationFails()
            .statusCode(200)
            .log().body();
  }

  @Test
  public void testDeleteLocation() {
    int testId = 1; // Assurez-vous que cet ID existe et est supprimable
    given()
            .pathParam("id", testId)
            .when()
            .delete("http://localhost:8082/lieux?id={id}")
            .then()
            .log().ifValidationFails()
            .statusCode(204)
            .log().body();
  }
}
