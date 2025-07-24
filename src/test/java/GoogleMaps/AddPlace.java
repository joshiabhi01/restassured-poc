/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:18-07-2025
 * Time:16:40
 */
package GoogleMaps;

import POJO.GoogleAPI.AddPlacePOJO;
import POJO.GoogleAPI.LocationPOJO;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddPlace {
    AddPlacePOJO addPlacePOJO ;

    @BeforeTest
    public void buildBody(){
        List<String> myTypes = new ArrayList<String>();
        myTypes.add("Mahadev Temple");
        myTypes.add("Pheonix Mall");
        myTypes.add("VR Mall");
        LocationPOJO locationPOJO = new LocationPOJO();
        locationPOJO.setLat(12.987095);
        locationPOJO.setLng(77.6862749);
        addPlacePOJO = new AddPlacePOJO();
        addPlacePOJO.setAccuracy(50);
        addPlacePOJO.setAddress("Mahadevpura, 560048");
        addPlacePOJO.setLanguage("Spanish");
        addPlacePOJO.setLocation(locationPOJO);
        addPlacePOJO.setPhone_number(675382368);
        addPlacePOJO.setWebsite("https://www.google.com/maps/place/Mahadevapura,+Bengaluru,+Karnataka");
        addPlacePOJO.setName("Mahadevpura, Bangalore");
        addPlacePOJO.setTypes(myTypes);
    }

    @Test
    public void addPlace(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String responsePost = given()
                .log()
                .all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(addPlacePOJO)
                .when()
                .post("maps/api/place/add/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)")
                .extract()
                .response()
                .asString();
        System.out.println(responsePost);
    }
}
