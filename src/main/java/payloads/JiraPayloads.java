/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:17-07-2025
 * Time:17:08
 */
package payloads;

public class JiraPayloads {

    public static String createBugPayload(String summary){
        return "{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \"SCRUM\"\n" +
                "       },\n" +
                "       \"summary\": \""+summary+"\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Bug\"\n" +
                "       }\n" +
                "   }\n" +
                "}";
    }
}
