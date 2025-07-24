/**
 * Problem Statement:
 * Author: Abhishek Joshi
 * User:joshia3
 * Date:18-07-2025
 * Time:16:46
 */
package POJO.GoogleAPI;

import java.util.List;

public class AddPlacePOJO {
    private LocationPOJO location;
    private Integer accuracy;
    private String name;
    private Integer phone_number;
    private String address;
    private List<String> types;
    private String website;
    private String language;

    public LocationPOJO getLocation() {
        return location;
    }

    public void setLocation(LocationPOJO location) {
        this.location = location;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Integer phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
