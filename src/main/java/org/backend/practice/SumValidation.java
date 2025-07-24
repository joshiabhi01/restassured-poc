/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:16-07-2025
 * Time:22:26
 */
package org.backend.practice;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloads.MapsPayload;

public class SumValidation {

    @Test
    public void sumOfCourses(){
        //Verify if sum of all the course prices matches with purchase Amount
        int courseSum = 0;
        JsonPath parserPath = new JsonPath(MapsPayload.CoursePrice());
        int countCourses = parserPath.getInt("courses.size()");
        int purchaseAmount = parserPath.getInt("dashboard.purchaseAmount");


        for(int i=0; i<countCourses; i++){
            courseSum += (parserPath.getInt("courses["+i+"].price") * parserPath.getInt("courses["+i+"].copies"));
        }
        Assert.assertEquals(purchaseAmount,courseSum);
        System.out.println(purchaseAmount+"\n"+courseSum);
    }
}
