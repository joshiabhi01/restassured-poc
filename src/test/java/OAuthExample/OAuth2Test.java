/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:22-07-2025
 * Time:21:48
 */
package OAuthExample;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OAuth2Test {
    private String accessToken;
    private String partialCode;
    private String code;

    @Test(priority = 1)
    public void getCodeUsingUI() throws InterruptedException {
        /*WebDriver driver = new FirefoxDriver();
        driver.get("https://accounts.google.com/v3/signin/identifier?opparams=%253Fauth_url%253Dhttps%25253A%25252F%25252Faccounts.google.com%25252Fo%25252Foauth2%25252Fv2%25252Fauth&dsh=S-80036389%3A1753203038613265&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&o2v=2&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&response_type=code&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&service=lso&flowName=GeneralOAuthFlow&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hANE6iPJ5ZhA-avTAxOxFDqh6yXvbOuRyHVEdiqmKcmvFSFLg7x9dYaLlS42KlXYhIrRcNDyeFZbSzaeRPQ-iM1m7QsUJKzD2TeMnf400xLNnwv2yFyudNdFwF29cdtSbEgdUm_nzOW4fFVAWiU24eZ_9ZXAStK_PUIaspTZiB0ov1SjztKxf8R7XlCQrQKv8UPX6pxIf17sdHt5VVqNk6PaiNxj7P6pa6bbqtNzFUWYjFZujdP2wZq88DlOfaoE9el0jXZc4sqrjbl2oGYcaxGVLdV70JTVe4hQRRxEZMl7_IpPXg7KpZWI4jvr4YZHkVeIo9sthW1g2LuEwJbxn-EjQcdZUBrSr03ZtH8T6ue9y_uvPANAFv0Tz2LM-2I8aDwI7p3ka10mq66B1eIDtmuAwbqnsY5Ok-5zC5tDtbYRbjPShp85HetVWNBmlVWhkArkIH6xrY0zUUkfvY87LmL_u8tmbg%26flowName%3DGeneralOAuthFlow%26as%3DS-80036389%253A1753203038613265%26client_id%3D692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com%23&app_domain=https%3A%2F%2Frahulshettyacademy.com&rart=ANgoxccDTABzyU9GYvvY90j7Cvy9Kr49RqHrnWR_WwnHqdTYAdI6woXuVMRkGFzG5Ll44CRkTWcAE_pykJtsNn2pevUy5-2Hphwry24vRm8uqzvzB3NKcr8");
        driver.findElement(By.id("identifierId")).sendKeys("abhishektecharts");
        driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Autodesk@8867");
        driver.findElement(By.id("#identifierId")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button/span[contains(text(), 'Continue')]")).click();

        String url = driver.getCurrentUrl();*/
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVMBsJgi4mU2BGlVbQmujIrHzbL1c5Ih7f2nJ69rwV73U_Pdk5J_8HOUxVS5-mxok5uABQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
        partialCode = url.split("code=")[1];
        code = partialCode.split("&scope")[0];
    }

    @Test(priority = 2)
    public void getCode(){
        //baseURI = "https://rahulshettyacademy.com";

        String accessTokenResponse = given()
                .urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when()
                .post("https://www.googleapis.com/oauth2/v4/token")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();
        JsonPath parsedJson = new JsonPath(accessTokenResponse);
        accessToken = parsedJson.getString("access_token");
    }

    @Test(priority = 3)
    public void getCourse(){
        //baseURI = "https://rahulshettyacademy.com";
        String response = given()
                .queryParam("access_token",accessToken)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .then()
                .assertThat()
                //.statusCode(200)
                .extract()
                .response()
                .asString();

        System.out.println(response);

    }


}
