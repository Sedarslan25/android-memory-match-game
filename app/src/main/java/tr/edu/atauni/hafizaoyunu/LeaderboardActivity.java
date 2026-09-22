package tr.edu.atauni.hafizaoyunu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        RecyclerView leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);
        leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for the leaderboard
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("tuba", 18, "Kolay (4x4) Sayılar", "00:51"));
        scores.add(new Score("serap", 22, "Kolay (4x4) Emoji", "01:09"));

        LeaderboardAdapter adapter = new LeaderboardAdapter(scores);
        leaderboardRecyclerView.setAdapter(adapter);
    }
}
