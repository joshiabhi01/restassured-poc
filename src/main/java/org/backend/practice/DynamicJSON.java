/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:17-07-2025
 * Time:12:45
 */
package org.backend.practice;

import DataDriven.DataExcel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payloads.LibraryPayloads;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DynamicJSON {

    @Test(/*dataProvider = "BooksData"*/)
    public void addBook(/*String isbn,String aisle*/) throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        DataExcel dataExcel = new DataExcel();
        ArrayList<String> testData = dataExcel.getTestData("library_tests","Test Case","addBook1");
        String response = given()
                .header("Content-Type","application/json")
                .body(LibraryPayloads.addBookPayload(testData.get(1),testData.get(2),testData.get(3),testData.get(4)))
                .when()
                .post("/Library/Addbook.php")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath parsedPath = new JsonPath(response);
        String id = parsedPath.get("ID");
        System.out.println(id);

    }

    //@Test(dataProvider = "BooksData")
    public void deleteBook(String isbn,String aisle){
        baseURI = "http://216.10.245.166";
        String response = given()
                .log()
                .all()
                .header("Content-Type","application/json")
                .body(LibraryPayloads.deletePayload(isbn,aisle))
                .when()
                .post("/Library/DeleteBook.php")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        System.out.println(response);
        JsonPath parsedPath = new JsonPath(response);
        parsedPath.prettyPrint();
        String deleteMessage = parsedPath.getString("msg");
        System.out.println(deleteMessage);

    }

    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return new Object[][]{{"newBookAJ-test1","2343242"},{"newBookAJ-test2","3874527"},{"newBookAJ-test3","6537621"}};
    }
}
