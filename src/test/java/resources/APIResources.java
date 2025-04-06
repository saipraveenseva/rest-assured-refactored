package resources;

//enum is a special class in java which collection of constants or methods.
public enum APIResources {

    AddPlaceAPI("/maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    deletePlaceAPI("/maps/api/place/delete/json");

    private String resource;

    APIResources(String resource) { // A constructor is required while dealing with enums.

        this.resource=resource; // Whatever resource we receive here is assigned to the local private resource variable here.

    }

    public String getResource(){
        return resource;        // returning the resource once it is assigned.
    }
}
