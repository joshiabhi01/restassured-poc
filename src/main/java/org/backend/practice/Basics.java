package org.backend.practice;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import payloads.MapsPayload;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //Add a place
        String responsePost = given()
                //.log()
               // .all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(MapsPayload.addPlace())
                .when()
                .post("maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope",equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)")
                .extract()
                .response()
                .asString();

        //System.out.println(response);
        JsonPath js = new JsonPath(responsePost);
        String place_id = js.getString("place_id");
        System.out.println(place_id);

        String newAddress = "70 winter walk, USA";


        //Update the Address
        given()
                //.log()
               // .all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+place_id+"\",\n" +
                        "\"address\":\""+ newAddress +"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when()
                .put("maps/api/place/update/json")
                .then()
                .assertThat()
                //.log()
                //.all()
                .statusCode(200)
                .body("msg",equalTo("Address successfully updated"));

        //Get the updated address
        String responseGet = given()
                .queryParam("key","qaclick123")
                .queryParam("place_id",place_id)
                .header("Content-Type","application/json")
                .when()
                .get("/maps/api/place/get/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("address",equalTo(newAddress))
                .extract()
                .response()
                .asString();
        JsonPath getJson = new JsonPath(responseGet);
        String updatedAddress = getJson.getString("address");
        System.out.println(updatedAddress.equals(newAddress));

        Assert.assertEquals(updatedAddress, newAddress);
    }
}