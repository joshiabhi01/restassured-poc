/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:17-07-2025
 * Time:23:06
 */
package OAuthExample;

import POJO.CourseAPI.ApiCoursePOJO;
import POJO.CourseAPI.GetCoursePOJO;
import POJO.CourseAPI.WebAutomationCoursePOJO;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTest {
    String token;
    String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
    @Test(priority = 1)
    public void getAuthToken(){

        String tokenResponse =  given()
                .formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","trust")
                //.header("Content-Type","multipart/form-data")
                .when()
                //.log()
                //.all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath parsedJson = new JsonPath(tokenResponse);
        //parsedJson.prettyPrint();
        token = parsedJson.getString("access_token");
        System.out.println(token);
    }

    @Test(priority = 2)
    public void getCourseDetails(){
        String courseDetailResponse = given()
                .queryParam("access_token", token)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .response()
                .asString();

        JsonPath parsedJson = new JsonPath(courseDetailResponse);
        parsedJson.prettyPrint();
        Assert.assertEquals(parsedJson.getString("expertise"),"Automation");
    }

    @Test(priority = 3)
    public void getCourseDetailsUsingPOJO(){
        GetCoursePOJO courseDetailResponse = given()
                .queryParam("access_token", token)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .response()
                .as(GetCoursePOJO.class);

        System.out.println(courseDetailResponse.getLinkedIn());
        System.out.println(courseDetailResponse.getInstructor());

        System.out.println(courseDetailResponse.getCourses().getApi().get(1).getCourseTitle());
        List<ApiCoursePOJO> apiCourses = courseDetailResponse.getCourses().getApi();

        //Printing the price and course title of a matching Api course
        for (ApiCoursePOJO apiCours : apiCourses) {
            if (apiCours.getCourseTitle().equalsIgnoreCase("SoapUi Webservices testing")) {
                System.out.println(apiCours.getPrice());
                break;
            }
        }

        //Printing all the course titles of Web Automation Courses
        List<WebAutomationCoursePOJO> webAutomationCourses = courseDetailResponse.getCourses().getWebAutomation();
        ArrayList<String> courseTitlesActual = new ArrayList<String>();

        for(WebAutomationCoursePOJO webAutomationCoursePOJO: webAutomationCourses){
            courseTitlesActual.add(webAutomationCoursePOJO.getCourseTitle());
        }
        List<String> courseTitlesExpected = Arrays.asList(courseTitles);

        Assert.assertEquals(courseTitlesExpected, courseTitlesActual);
    }


}
