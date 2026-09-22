package tr.edu.atauni.hafizaoyunu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup difficultyRadioGroup;
    private RadioGroup temaRadioGroup;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        temaRadioGroup = findViewById(R.id.temaRadioGroup);
        usernameEditText = findViewById(R.id.usernameEditText);
        Button startGameBtn = findViewById(R.id.startGameBtn);
        Button skorTablosuBtn = findViewById(R.id.skorTablosuBtn);

        startGameBtn.setOnClickListener(this);
        skorTablosuBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startGameBtn) {
            Intent intent = new Intent(getApplicationContext(), oyunEkrani.class);

            String username = usernameEditText.getText().toString();
            intent.putExtra("username", username);

            int selectedTemaId = temaRadioGroup.getCheckedRadioButtonId();
            if (selectedTemaId == R.id.hayvanlarRadioButton) {
                intent.putExtra("temaId", 0);
            } else if (selectedTemaId == R.id.meyvelerRadioButton) {
                intent.putExtra("temaId", 1);
            } else if (selectedTemaId == R.id.bayrakRadioButton) {
                intent.putExtra("temaId", 2);
            } else if (selectedTemaId == R.id.ciceklerRadioButton) {
                intent.putExtra("temaId", 3);
            }

            int selectedDifficultyId = difficultyRadioGroup.getCheckedRadioButtonId();
            if (selectedDifficultyId == R.id.kolayRadioButton) {
                intent.putExtra("zorluk", "kolay");
            } else if (selectedDifficultyId == R.id.ortaRadioButton) {
                intent.putExtra("zorluk", "orta");
            } else if (selectedDifficultyId == R.id.zorRadioButton) {
                intent.putExtra("zorluk", "zor");
            }
            startActivity(intent);
        } else if (id == R.id.skorTablosuBtn) {
            Intent intent = new Intent(getApplicationContext(), SkorTablosuActivity.class);
            startActivity(intent);
        }
    }
}
