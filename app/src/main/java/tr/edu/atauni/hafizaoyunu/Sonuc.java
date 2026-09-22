package tr.edu.atauni.hafizaoyunu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Sonuc extends AppCompatActivity {

    private static final String SEPARATOR = "|||";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        TextView oyuncuAdi = findViewById(R.id.oyuncuAdi);
        TextView sureText = findViewById(R.id.sureText);
        TextView hamleText = findViewById(R.id.hamleText);
        TextView temaText = findViewById(R.id.temaText);
        TextView zorlukText = findViewById(R.id.zorlukText);
        LinearLayout skorlarListesiLayout = findViewById(R.id.skorlarListesiLayout);
        Button tekrarOynaBtn = findViewById(R.id.tekrarOynaBtn);
        Button anaMenuBtn = findViewById(R.id.anaMenuBtn);

        Intent i = getIntent();
        int hamleSayisi = i.getIntExtra("hamle", 0);
        long gecenSure = i.getLongExtra("sure", 0);
        String zorluk = i.getStringExtra("zorluk");
        int temaId = i.getIntExtra("temaId", 0);
        String username = i.getStringExtra("username");

        long dakika = TimeUnit.MILLISECONDS.toMinutes(gecenSure);
        long saniye = TimeUnit.MILLISECONDS.toSeconds(gecenSure) % 60;
        String sureFormatted = String.format("%02d:%02d", dakika, saniye);

        String temaStr;
        switch (temaId) {
            case 0: temaStr = "Hayvanlar"; break;
            case 1: temaStr = "Meyveler"; break;
            case 2: temaStr = "Bayraklar"; break;
            case 3: temaStr = "Çiçekler"; break;
            default: temaStr = "Bilinmiyor"; break;
        }

        String zorlukStr;
        if ("orta".equals(zorluk)) {
            zorlukStr = "Orta (6x6)";
        } else if ("zor".equals(zorluk)) {
            zorlukStr = "Zor (8x8)";
        } else {
            zorlukStr = "Kolay (4x4)";
        }

        oyuncuAdi.setText(username);
        sureText.setText("Süre: " + sureFormatted);
        hamleText.setText("Hamle: " + hamleSayisi);
        temaText.setText("Tema: " + temaStr);
        zorlukText.setText("Zorluk: " + zorlukStr);

        SharedPreferences prefs = getSharedPreferences("skorlar", MODE_PRIVATE);
        Set<String> skorlarSet = prefs.getStringSet("skorlar", new HashSet<>());

        Set<String> yeniSkorlarSet = new HashSet<>();
        for (String skorKaydi : skorlarSet) {
            if (skorKaydi.split(Pattern.quote(SEPARATOR)).length == 5) {
                yeniSkorlarSet.add(skorKaydi);
            }
        }

        String yeniSkor = username + SEPARATOR + sureFormatted + SEPARATOR + hamleSayisi + SEPARATOR + temaStr + SEPARATOR + zorlukStr;
        yeniSkorlarSet.add(yeniSkor);
        prefs.edit().putStringSet("skorlar", yeniSkorlarSet).apply();

        List<String> skorListesi = new ArrayList<>(yeniSkorlarSet);

        Collections.sort(skorListesi, (s1, s2) -> {
            try {
                String[] parts1 = s1.split(Pattern.quote(SEPARATOR));
                String[] parts2 = s2.split(Pattern.quote(SEPARATOR));
                if (parts1.length > 2 && parts2.length > 2) {
                    int hamle1 = Integer.parseInt(parts1[2]);
                    int hamle2 = Integer.parseInt(parts2[2]);
                    return Integer.compare(hamle1, hamle2);
                }
            } catch (NumberFormatException e) {
                return 0;
            }
            return 0;
        });

        skorlarListesiLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String skorStr : skorListesi) {
            String[] skorDetaylari = skorStr.split(Pattern.quote(SEPARATOR));

            View skorView = inflater.inflate(R.layout.list_item_skor, skorlarListesiLayout, false);

            TextView skorUsername = skorView.findViewById(R.id.skorUsername);
            TextView skorSure = skorView.findViewById(R.id.skorSure);
            TextView skorHamle = skorView.findViewById(R.id.skorHamle);
            TextView skorTema = skorView.findViewById(R.id.skorTema);
            TextView skorZorluk = skorView.findViewById(R.id.skorZorluk);

            skorUsername.setText(skorDetaylari[0]);
            skorSure.setText("Süre: " + skorDetaylari[1]);
            skorHamle.setText("Hamle: " + skorDetaylari[2]);
            skorTema.setText("Tema: " + skorDetaylari[3]);
            skorZorluk.setText("Zorluk: " + skorDetaylari[4]);

            skorlarListesiLayout.addView(skorView);
        }

        tekrarOynaBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), oyunEkrani.class);
            intent.putExtra("zorluk", zorluk);
            intent.putExtra("temaId", temaId);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        });

        anaMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
