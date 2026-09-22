package tr.edu.atauni.hafizaoyunu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class SkorTablosuActivity extends AppCompatActivity {

    private static final String SEPARATOR = "|||";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skor_tablosu);

        ListView skorListesiView = findViewById(R.id.skor_listesi);
        Button anaMenuBtn = findViewById(R.id.anaMenuBtn);

        SharedPreferences prefs = getSharedPreferences("skorlar", MODE_PRIVATE);
        Set<String> skorlarSet = prefs.getStringSet("skorlar", new HashSet<>());

        List<Skor> skorListesi = new ArrayList<>();
        for (String skorStr : skorlarSet) {
            String[] skorDetaylari = skorStr.split(Pattern.quote(SEPARATOR));
            if (skorDetaylari.length == 5) {
                skorListesi.add(new Skor(skorDetaylari[0], skorDetaylari[1], Integer.parseInt(skorDetaylari[2]), skorDetaylari[3], skorDetaylari[4]));
            }
        }

        Collections.sort(skorListesi, (s1, s2) -> Integer.compare(s1.getHamle(), s2.getHamle()));

        SkorAdapter adapter = new SkorAdapter(this, skorListesi);
        skorListesiView.setAdapter(adapter);

        anaMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SkorTablosuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
