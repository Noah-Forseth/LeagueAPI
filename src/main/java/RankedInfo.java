public class RankedInfo {
    String queueType;
    String tier;
    String rank;
    int leaguePoints;
    int wins;
    int losses;
    public RankedInfo (String queueType, String tier, String rank,
                    int leaguePoints, int wins, int losses) {
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
    }
}
