package tr.edu.atauni.hafizaoyunu;

import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class oyunEkrani extends AppCompatActivity {

    private int sonKartId = 0;
    private boolean bekle = false;
    private int dogruTahmin = 0;
    private int hamleSayisi = 0;
    private int yanlisHamleSayisi = 0;

    private GridLayout gridLayout;
    private TextView hamleSayisiText;
    private TextView eslesmeSayisiText;
    private Chronometer zamanlayici;
    private ProgressBar progressBar;
    private TextView feedbackTextView;

    private int satir = 4;
    private int sutun = 4;
    private String zorlukSeviyesi;
    private int temaId;
    private String username;

    private long pauseOffset;
    private boolean isPaused = false;

    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;
    private MediaPlayer winSound;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);

        handler = new Handler();

        gridLayout = findViewById(R.id.grdLyt);
        hamleSayisiText = findViewById(R.id.hamleSayisi);
        eslesmeSayisiText = findViewById(R.id.eslesmeSayisi);
        zamanlayici = findViewById(R.id.zamanlayici);
        progressBar = findViewById(R.id.progressBar);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView themeTextView = findViewById(R.id.themeTextView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        ImageButton pauseButton = findViewById(R.id.pauseButton);
        ImageButton restartButton = findViewById(R.id.restartButton);
        ImageButton homeButton = findViewById(R.id.homeButton);

        correctSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.wrong);
        winSound = MediaPlayer.create(this, R.raw.win);

        Intent intent = getIntent();
        zorlukSeviyesi = intent.getStringExtra("zorluk");
        temaId = intent.getIntExtra("temaId", 0);
        username = intent.getStringExtra("username");

        usernameTextView.setText(username);

        if (zorlukSeviyesi != null) {
            if (zorlukSeviyesi.equals("orta")) {
                satir = 6;
                sutun = 6;
                difficultyTextView.setText("6x6");
            } else if (zorlukSeviyesi.equals("zor")) {
                satir = 8;
                sutun = 8;
                difficultyTextView.setText("8x8");
            } else {
                difficultyTextView.setText("4x4");
            }
        }

        switch (temaId) {
            case 0:
                themeTextView.setText("Hayvanlar");
                break;
            case 1:
                themeTextView.setText("Meyveler");
                break;
            case 2:
                themeTextView.setText("Bayraklar");
                break;
            case 3:
                themeTextView.setText("Çiçekler");
                break;
        }

        eslesmeSayisiText.setText("0/" + (satir * sutun) / 2);

        oyunuBaslat();

        restartButton.setOnClickListener(v -> oyunuBaslat());
        homeButton.setOnClickListener(v -> finish());
        pauseButton.setOnClickListener(v -> {
            if (isPaused) {
                zamanlayici.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                zamanlayici.start();
                ((ImageButton) v).setImageResource(R.drawable.ic_pause);
                isPaused = false;
            } else {
                pauseOffset = SystemClock.elapsedRealtime() - zamanlayici.getBase();
                zamanlayici.stop();
                ((ImageButton) v).setImageResource(R.drawable.ic_play);
                isPaused = true;
            }
        });
    }

    private void oyunuBaslat() {
        sonKartId = 0;
        bekle = false;
        dogruTahmin = 0;
        hamleSayisi = 0;
        yanlisHamleSayisi = 0;
        hamleSayisiText.setText("0");
        eslesmeSayisiText.setText("0/" + (satir * sutun) / 2);
        progressBar.setProgress(0);
        feedbackTextView.setText("");

        zamanlayici.setBase(SystemClock.elapsedRealtime());
        zamanlayici.start();

        gridLayout.removeAllViews();
        gridLayout.setRowCount(satir);
        gridLayout.setColumnCount(sutun);

        List<Integer> oyunResimleri = getTemaResimleriListesi();
        final int kartSayisi = satir * sutun;
        List<Integer> kartIcinResimler = new ArrayList<>();

        int gerekliResimSayisi = kartSayisi / 2;
        if (oyunResimleri.size() < gerekliResimSayisi) {
            // Not enough images, handle this error
            return;
        }
        for (int i = 0; i < gerekliResimSayisi; i++) {
            Integer resimId = oyunResimleri.get(i);
            kartIcinResimler.add(resimId);
            kartIcinResimler.add(resimId);
        }
        Collections.shuffle(kartIcinResimler);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int margin = 16;
        int cardSize = (screenWidth - (sutun * margin)) / sutun;

        for (int i = 0; i < kartSayisi; i++) {
            final Kart kart = new Kart(this, kartIcinResimler.get(i));
            kart.setId(View.generateViewId());

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cardSize;
            params.height = cardSize;
            params.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            kart.setLayoutParams(params);

            kart.setOnClickListener(v -> kartTiklandi((Kart) v));
            gridLayout.addView(kart);
        }
    }

    private void kartTiklandi(final Kart suankiKart) {
        if (suankiKart.acikMi || suankiKart.eslesti || bekle || isPaused) return;

        suankiKart.dondur();

        if (sonKartId != 0) { // Bu ikinci kartın seçimi
            bekle = true;
            hamleSayisi++;
            hamleSayisiText.setText(String.valueOf(hamleSayisi));

            final Kart oncekiKart = findViewById(sonKartId);

            if (oncekiKart.resId == suankiKart.resId) { // Doğru Eşleşme
                if (correctSound != null) {
                    correctSound.start();
                }
                feedbackTextView.setText("Doğru");
                feedbackTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                handler.postDelayed(() -> feedbackTextView.setText(""), 1000);
                oncekiKart.eslesti = true;
                suankiKart.eslesti = true;
                oncekiKart.eslestiAnimasyonu();
                suankiKart.eslestiAnimasyonu();
                dogruTahmin += 2;
                eslesmeSayisiText.setText(dogruTahmin / 2 + "/" + (satir * sutun) / 2);
                progressBar.setProgress((dogruTahmin * 100) / (satir * sutun));
                bekle = false;
                sonKartId = 0;

                if (dogruTahmin == satir * sutun) { // Oyun Bitti
                    if (winSound != null) {
                        winSound.start();
                    }
                    zamanlayici.stop();
                    handler.postDelayed(() -> {
                        long gecenSure = SystemClock.elapsedRealtime() - zamanlayici.getBase();
                        Intent i = new Intent(getApplicationContext(), Sonuc.class);
                        i.putExtra("kazandiMi", "evet");
                        i.putExtra("sure", gecenSure);
                        i.putExtra("hamle", hamleSayisi);
                        i.putExtra("yanlisHamle", yanlisHamleSayisi);
                        i.putExtra("zorluk", zorlukSeviyesi);
                        i.putExtra("temaId", temaId);
                        i.putExtra("username", username);
                        startActivity(i);
                        finish();
                    }, 500);
                }
            } else { // Yanlış Eşleşme
                if (wrongSound != null) {
                    wrongSound.start();
                }
                feedbackTextView.setText("Yanlış");
                feedbackTextView.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                yanlisHamleSayisi++;
                handler.postDelayed(() -> {
                    oncekiKart.dondur();
                    suankiKart.dondur();
                    bekle = false;
                    sonKartId = 0;
                    feedbackTextView.setText("");
                }, 1000);
            }
        } else { // Bu ilk kartın seçimi
            sonKartId = suankiKart.getId();
        }
    }

    private List<Integer> getTemaResimleriListesi() {
        int arrayId;
        switch (temaId) {
            case 0:
                arrayId = R.array.hayvanlar_tema;
                break;
            case 1:
                arrayId = R.array.meyveler_tema;
                break;
            case 2:
                arrayId = R.array.bayraklar_tema;
                break;
            case 3:
                arrayId = R.array.cicekler_tema;
                break;
            default:
                arrayId = R.array.hayvanlar_tema;
                break;
        }

        TypedArray res = getResources().obtainTypedArray(arrayId);
        List<Integer> resimListesi = new ArrayList<>();
        for (int i = 0; i < res.length(); i++) {
            resimListesi.add(res.getResourceId(i, 0));
        }
        res.recycle();
        Collections.shuffle(resimListesi);
        return resimListesi;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (correctSound != null) {
            if (correctSound.isPlaying()) {
                correctSound.stop();
            }
            correctSound.release();
        }
        if (wrongSound != null) {
            if (wrongSound.isPlaying()) {
                wrongSound.stop();
            }
            wrongSound.release();
        }
        if (winSound != null) {
            if (winSound.isPlaying()) {
                winSound.stop();
            }
            winSound.release();
        }
    }
}
