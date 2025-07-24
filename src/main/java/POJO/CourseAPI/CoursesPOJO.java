/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:18-07-2025
 * Time:11:33
 */
package POJO.CourseAPI;

import java.util.List;

public class CoursesPOJO {
    private List<WebAutomationCoursePOJO> webAutomation;
    private List<ApiCoursePOJO> api;
    private List<MobileCoursePOJO> mobile;

    public List<WebAutomationCoursePOJO> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<WebAutomationCoursePOJO> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<ApiCoursePOJO> getApi() {
        return api;
    }

    public void setApi(List<ApiCoursePOJO> api) {
        this.api = api;
    }

    public List<MobileCoursePOJO> getMobile() {
        return mobile;
    }

    public void setMobile(List<MobileCoursePOJO> mobile) {
        this.mobile = mobile;
    }
}
