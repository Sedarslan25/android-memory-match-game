package tr.edu.atauni.hafizaoyunu;

public class Skor {
    private final String username;
    private final String sure;
    private final int hamle;
    private final String tema;
    private final String zorluk;

    public Skor(String username, String sure, int hamle, String tema, String zorluk) {
        this.username = username;
        this.sure = sure;
        this.hamle = hamle;
        this.tema = tema;
        this.zorluk = zorluk;
    }

    public String getUsername() {
        return username;
    }

    public String getSure() {
        return sure;
    }

    public int getHamle() {
        return hamle;
    }

    public String getTema() {
        return tema;
    }

    public String getZorluk() {
        return zorluk;
    }
}
