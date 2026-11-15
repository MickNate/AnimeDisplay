import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Scanner;

public class Main {
    private static final String CLIENT_ID = "9deb8357507d57b68c2d29afaef15f38";
    private static final String MAIN_URL = "https://api.myanimelist.net/v2/anime/season/";

    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        int year = getYear();
        String season = getSeason();
        String response = sendRequest(year, season);
        assert response != null;
       Response resObject = gson.fromJson(response, Response.class);

        System.out.println("The anime of " + season + " " + year + " are:");

        for(int i=0; i<10; i++){
            System.out.println(resObject.getData().get(i).getNode().getTitle());
        }
    }

    static int getYear(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a year: ");
        int year = scanner.nextInt();
        return year;
    }

    static String getSeason(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a season: ");
        String season = scanner.nextLine();
        return season;
    }

    static String sendRequest(int year, String season){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    //.uri(URI.create(MAIN_URL + "2017/summer?limit=4"))
                    .uri(URI.create(MAIN_URL + year + '/' + season + "?limit=10"))
                    .header("X-MAL-CLIENT-ID", CLIENT_ID)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            /*BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            writer.write(response.body());
            writer.close();*/

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

