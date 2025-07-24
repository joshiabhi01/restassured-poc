/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:17-07-2025
 * Time:12:48
 */
package payloads;

import java.util.HashMap;

public class LibraryPayloads {

    public static HashMap<String, Object> addBookPayload(String name, String isbn, String aisle, String author){
        HashMap<String, Object> addBookMap = new HashMap<>();
        addBookMap.put("name", name);
        addBookMap.put("isbn",isbn);
        addBookMap.put("aisle",aisle);
        addBookMap.put("author",author);

        return addBookMap;
        /*return "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";*/
    }

    public static String deletePayload(String isbn,String aisle){
        return "{\n" +
                "\"ID\" : \""+isbn+aisle+"\"\n" +
                "} \n";
    }
}
