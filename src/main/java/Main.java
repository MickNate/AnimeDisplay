import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static final String CLIENT_ID = "9deb8357507d57b68c2d29afaef15f38";
    private static final String MAIN_URL = "https://api.myanimelist.net/v2/anime/season/";

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(MAIN_URL + "2017/summer?limit=4"))
                    .header("X-MAL-CLIENT-ID", CLIENT_ID)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            writer.write(response.body());
            writer.close();

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

