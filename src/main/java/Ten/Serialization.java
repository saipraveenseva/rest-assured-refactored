package Ten;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)     // We'll know about this later.

public class Serialization {

    public String url;      // Every attribute present in endpoint https://rahulshettyacademy.com/ is declared here in a class called Serialization
    public String services;
    public String expertise;
    public Courses Courses;             // THis is what a POJO class looks like. We declare the attributes and write setters and getter.
    public String instructor;           // This Java object is converted to Request body(Payload).
    public String linkedIn;             // Rather than writing the payload as a code this is simpler to just declare variables and write setters getters.

                                            // THis is called Serialization

    public String getUrl() {
        return url;
    }       // All these attributes need to have their Setter and Getter methods.

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Courses getCourses() {
        return Courses;
    }       // The Course attribute has sub-fields. It has course names list. So we write Courses.java to further expand this. Visit Courses.java

    public void setCourses(Courses courses) {
        Courses = courses;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLinkedin() {
        return linkedIn;
    }

    public void setLinkedin(String Linkedin) {
        this.linkedIn = linkedIn;
    }
}
