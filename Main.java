// Very cool imports.
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
// The code begins...
public class Main {
    public static void main(String[] args) {
        // Get some user input! (Use integers.)
        Scanner user = new Scanner(System.in);
        boolean isValid = true;
        String ipAddress = "";
        // System.out.println("Please enter the first octet: ");
        int octet1 = (int) (Math.random() * 255);// user.nextInt();
//        System.out.println("Please enter the first octet: " + octet1);
        int octet2 = (int) (Math.random() * 255);// user.nextInt();
//        System.out.println("Please enter the second octet: " + octet2);
        int octet3 = (int) (Math.random() * 255);// user.nextInt();
//        System.out.println("Please enter the third octet: " + octet3);
        int octet4 = (int) (Math.random() * 255);// user.nextInt();
//        System.out.println("Please enter the fourth octet: " + octet4);
        // Verify that each octet is between 0 and 255!
        if (octet1 < 0 || octet1 > 255) {
            System.out.println("Octet 1 is invalid.");
            isValid = false;
        }
        if (octet2 < 0 || octet2 > 255) {
            System.out.println("Octet 2 is invalid.");
            isValid = false;
        }
        if (octet3 < 0 || octet3 > 255) {
            System.out.println("Octet 3 is invalid.");
            isValid = false;
        }
        if (octet4 < 0 || octet4 > 255) {
            System.out.println("Octet 4 is invalid.");
            isValid = false;
        }
        if (isValid) {
            // Display an output!
            ipAddress = octet1 + "." + octet2 + "." + octet3 + "." + octet4;
            System.out.println("Random IP Address: " + ipAddress);
            String geoLocationData = getGeoLocation(ipAddress);
            System.out.println("Metadata: " + geoLocationData);
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("continent_code") - 1, geoLocationData.indexOf(", \"continent_name")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("continent_name") - 1, geoLocationData.indexOf(", \"country_code")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("country_code") - 1, geoLocationData.indexOf(", \"country_name")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("country_name") - 1, geoLocationData.indexOf(", \"region_code")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("region_code") - 1, geoLocationData.indexOf(", \"region_name")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("region_name") - 1, geoLocationData.indexOf(", \"city")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("city") - 1, geoLocationData.indexOf(", \"zip")));
            System.out.println(geoLocationData.substring(geoLocationData.indexOf("zip") - 1, geoLocationData.indexOf(", \"latitude")));
        }
    } // Ending of main() method.
    // The API shenanigans and stuff...
    public static String getGeoLocation(String ipAddress) {
        String apiKey = "a33e74b51990ebbc240c6ff949ff62fb"; // Don't share it's Ms. Paulino's key
        // We are constructing the url for the API request here.
        String url = "http://api.ipstack.com/" + ipAddress + "?access_key=" + apiKey;
        // Now we can create an HTTP client object, so we can send a request.
        HttpClient client = HttpClient.newHttpClient();
        // First, we build an HTTP request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        // Then, we send the request and we get a response.
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        // Finally, we get the response from the API.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}