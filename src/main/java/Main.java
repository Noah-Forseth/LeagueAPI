import java.io.IOException;
import java.util.Scanner;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
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
        HttpRequest playerRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+name+"/"+tag))
                .header("X-Riot-Token", System.getenv("RIOT_API_KEY"))
                .GET()
                .build();
        HttpRequest rankedRequest;
        HttpResponse<String> playerResponse;
        HttpResponse<String> rankedResponse;
        try {
            playerResponse = client.send(playerRequest, HttpResponse.BodyHandlers.ofString());
            Account account = gson.fromJson(playerResponse.body(), Account.class);
            rankedRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://na1.api.riotgames.com/lol/league/v4/entries/by-puuid/" + account.puuid))
                    .header("X-Riot-Token", System.getenv("RIOT_API_KEY"))
                    .GET()
                    .build();
            rankedResponse = client.send(rankedRequest, HttpResponse.BodyHandlers.ofString());
            RankedInfo[] rankedInfoArray = gson.fromJson(rankedResponse.body(), RankedInfo[].class);
            for (int i = 0; i < rankedInfoArray.length; i++) {
                if (rankedInfoArray[i].queueType.equals("RANKED_SOLO_5x5")) {
                    System.out.println(name + " is ranked " + rankedInfoArray[i].tier + " " + rankedInfoArray[i].rank
                    + " with " + rankedInfoArray[i].leaguePoints + " LP. " + name + " has " + rankedInfoArray[i].wins
                    + " wins and " + rankedInfoArray[i].losses + " losses.");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}