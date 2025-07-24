/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:19-07-2025
 * Time:14:06
 */
package EComAPI;

import POJO.ECOM.LoginRequestPOJO;
import POJO.ECOM.LoginResponsePOJO;
import POJO.ECOM.OrderDetailsPOJO;
import POJO.ECOM.OrderPOJO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


public class ProductE2ETest {
    RequestSpecification requestLoginSpecification;
    RequestSpecification requestProductSpecification;
    ResponseSpecification responseSpecification;
    LoginRequestPOJO login;
    String tokenValue;
    String userId;
    String message;
    String productId;

    @BeforeTest
    public void specBuilder(){
        requestLoginSpecification = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .build();
        requestProductSpecification = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.MULTIPART)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(anyOf(is(200),is(201)))
                                .build();
        login = new LoginRequestPOJO();
        login.setUserEmail("abhishektecharts@gmail.com");
        login.setUserPassword("Amazon@8867");
    }

    @Test(priority = 1)
    public void login(){
       LoginResponsePOJO response = given()
                .spec(requestLoginSpecification)
                .body(login)
                .when()
                .post("/api/ecom/auth/login")
                .then()
               //.log()
               //.all()
                .spec(responseSpecification)
                .extract()
                .response()
                .as(LoginResponsePOJO.class);
        tokenValue = response.getToken();
        userId = response.getUserId();
        message = response.getMessage();
    }

    @Test(dependsOnMethods = "login")
    public void addProduct(){
    String createProductResponse = given()
                .spec(requestProductSpecification)
                .header("Authorization",tokenValue)
                .param("productName","Laptop")
                .param("productAddedBy",userId)
                .param("productCategory","Electronics")
                .param("productSubCategory","Computer")
                .param("productPrice",11500)
                .param("productDescription","Lenovo Sharp")
                .param("productFor","All")
                .multiPart("productImage",new File("C:\\Users\\joshia3\\OneDrive - Autodesk\\Documents\\POC\\BE\\Backend\\src\\main\\resources\\Screenshots\\pngimg.com - hummingbird_PNG91.png"))
                .when()
                .post("/api/ecom/product/add-product")
                .then()
                //.log()
                //.all()
                .spec(responseSpecification)
                .extract()
                .response()
                .asString();
        JsonPath parsedJson = new JsonPath(createProductResponse);
        productId = parsedJson.getString("productId");
    }

    @Test(dependsOnMethods = "addProduct")
    public void createOrder(){
        OrderDetailsPOJO orderDetails = new OrderDetailsPOJO();
        orderDetails.setProductOrderedId(productId);
        orderDetails.setCountry("India");

        List<OrderDetailsPOJO> orderList = new ArrayList<>();
        orderList.add(orderDetails);

        OrderPOJO newOrder = new OrderPOJO();
        newOrder.setOrders(orderList);

        String createOrderResponse = given()
                .spec(requestLoginSpecification)
                .header("Authorization",tokenValue)
                .body(newOrder)
                .log()
                .all()
                .when()
                .post("/api/ecom/order/create-order")
                .then()
                .log()
                .all()
                .spec(responseSpecification)
                .extract()
                .response()
                .asString();
        System.out.println(createOrderResponse);
    }

    @Test(dependsOnMethods = "createOrder")
    public void deleteOrder(){
     String responseMessage = given()
                .spec(requestLoginSpecification)
                .header("Authorization", tokenValue)
                .pathParam("productId",productId)
                .when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then()
             .log()
             .all()
                .spec(responseSpecification)
                .extract()
                .response()
                .asString();
     JsonPath parsedJson = new JsonPath(responseMessage);
     Assert.assertEquals(parsedJson.getString("message"),"Product Deleted Successfully");
    }
}
