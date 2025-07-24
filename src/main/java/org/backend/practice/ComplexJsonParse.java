/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:16-07-2025
 * Time:20:09
 */
package org.backend.practice;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import payloads.MapsPayload;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath parserPath = new JsonPath(MapsPayload.CoursePrice());
        parserPath.prettyPrint();
        //Number of courses in response
        int countCourses = parserPath.getInt("courses.size()");
        System.out.println(countCourses);

        //Print purchase amount
        int purchaseAmount = parserPath.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        //Print the titleFirst of the first course
        String titleFirst = parserPath.getString("courses[0].title");
        System.out.println(titleFirst);

        //Print all course titles and their respective prices
        for(int i=0; i<countCourses; i++){
            System.out.println(parserPath.getString("courses["+i+"].title")+": "+parserPath.getString("courses["+i+"].price"));
        }

        //Print no of copies sold by RPA Course
        int numberOfCopies = 0;
        String course = "RPA";
        for(int i=0; i<countCourses; i++){
            if(parserPath.getString("courses["+i+"].title").equals(course)){
               numberOfCopies = parserPath.getInt("courses["+i+"].copies");
                break;
            }
        }
        System.out.println("Number of copies sold by "+course+" are: "+numberOfCopies);

        //Verify if sum of all the course prices matches with purchase Amount
        int courseSum = 0;
        for(int i=0; i<countCourses; i++){
            courseSum += parserPath.getInt("courses["+i+"].price");
        }
        Assert.assertEquals(purchaseAmount,courseSum);
    }
}
