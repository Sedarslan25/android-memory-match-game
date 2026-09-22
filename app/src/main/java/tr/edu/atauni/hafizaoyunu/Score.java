package tr.edu.atauni.hafizaoyunu;

public class Score {
    private String username;
    private int moves;
    private String details;
    private String time;

    public Score(String username, int moves, String details, String time) {
        this.username = username;
        this.moves = moves;
        this.details = details;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public int getMoves() {
        return moves;
    }

    public String getDetails() {
        return details;
    }

    public String getTime() {
        return time;
    }
}
