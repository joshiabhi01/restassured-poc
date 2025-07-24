/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:17-07-2025
 * Time:17:05
 */
package Jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.JiraPayloads;

import java.io.File;

import static io.restassured.RestAssured.*;

public class BugTest {
public String bugSummary = "Radio button not working";
public String bugId;
public String expectedAttachment = "img.png";

    @Test()
    public void createBugInJira(){
        RestAssured.baseURI = "https://abhishektecharts.atlassian.net/";
        String createBugResponse = given()
                .header("Content-Type","application/json")
                .header("Authorization","Basic YWJoaXNoZWt0ZWNoYXJ0c0BnbWFpbC5jb206QVRBVFQzeEZmR0YwVnFVZEFaU2pBOUpRYlR2LWZSOGRleDFlWjZEcmcyWlpMdGQ2OHZSVUV6YmN0M3JUYUdBd3NMd3lWN1htbG1jODhTNDExNHBNY19oSmh5eGNkci1LYjZ5bXlXeEY3MVBYTDIwS19FZVU1OTJ0aHFxNVhBa2hkc1pkTFNlVTQ5REhaSXhaVHZfWG5SbkZwTHZjcmRGa1UtWmJISkxWSndSZEtqLUQ2MGFzZWJFPTE1NTk3OTk2")
                .body(JiraPayloads.createBugPayload(bugSummary))
                .when()
                .post("/rest/api/3/issue")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .response()
                .asString();


        JsonPath parsedJson = new JsonPath(createBugResponse);
        bugId = parsedJson.getString("id");
        System.out.println(bugId);
    }

    @Test(dependsOnMethods = "createBugInJira")
    public void addAttachmentToBug(){
        String attachmentResponse = given()
                .header("Content-Type","multipart/form-data")
                .header("X-Atlassian-Token","no-check")
                .header("Authorization","Basic YWJoaXNoZWt0ZWNoYXJ0c0BnbWFpbC5jb206QVRBVFQzeEZmR0YwVnFVZEFaU2pBOUpRYlR2LWZSOGRleDFlWjZEcmcyWlpMdGQ2OHZSVUV6YmN0M3JUYUdBd3NMd3lWN1htbG1jODhTNDExNHBNY19oSmh5eGNkci1LYjZ5bXlXeEY3MVBYTDIwS19FZVU1OTJ0aHFxNVhBa2hkc1pkTFNlVTQ5REhaSXhaVHZfWG5SbkZwTHZjcmRGa1UtWmJISkxWSndSZEtqLUQ2MGFzZWJFPTE1NTk3OTk2")
                .pathParam("bugIdKey", bugId)
                .multiPart("file",new File("C:\\Users\\joshia3\\OneDrive - Autodesk\\Documents\\POC\\BE\\Backend\\src\\main\\resources\\Screenshots\\img.png"))
                .log()
                .all()
                .when()
                .post("rest/api/3/issue/{bugIdKey}/attachments")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath parsedJson = new JsonPath(attachmentResponse);
        String actualAttachmentName = parsedJson.getString("[0].filename");
        Assert.assertEquals(actualAttachmentName,expectedAttachment);

    }
}
