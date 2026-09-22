package tr.edu.atauni.hafizaoyunu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SkorAdapter extends ArrayAdapter<Skor> {

    public SkorAdapter(@NonNull Context context, @NonNull List<Skor> skorlar) {
        super(context, 0, skorlar);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_skor, parent, false);
        }

        Skor skor = getItem(position);

        TextView skorUsername = convertView.findViewById(R.id.skorUsername);
        TextView skorSure = convertView.findViewById(R.id.skorSure);
        TextView skorHamle = convertView.findViewById(R.id.skorHamle);
        TextView skorTema = convertView.findViewById(R.id.skorTema);
        TextView skorZorluk = convertView.findViewById(R.id.skorZorluk);

        if (skor != null) {
            skorUsername.setText(skor.getUsername());
            skorSure.setText("Süre: " + skor.getSure());
            skorHamle.setText("Hamle: " + skor.getHamle());
            skorTema.setText("Tema: " + skor.getTema());
            skorZorluk.setText("Zorluk: " + skor.getZorluk());
        }

        return convertView;
    }
}
