import java.io.IOException;
import java.util.Scanner;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Main {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .build();

        String name = "";
        String tag = "";
        Scanner scanner = new Scanner(System.in);
        String ID;
        boolean run = true;
        while(run) {
            System.out.print("Welcome to LeagueAPI, enter a Riot ID: ");
            ID = scanner.nextLine();
            String[] array = ID.split("#");
            if(array.length == 2 && !array[0].equals("") && !array[1].equals("")) {
                run = false;
                name = array[0];
                tag = array[1];
                System.out.println("You are looking for " + ID);
            }
            else {
                System.out.println("Invalid Riot ID");
            }

        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+name+"/"+tag))
                .header("X-Riot-Token", System.getenv("RIOT_API_KEY"))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}