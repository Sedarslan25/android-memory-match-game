package tr.edu.atauni.hafizaoyunu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<Score> scores;

    public LeaderboardAdapter(List<Score> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Score score = scores.get(position);
        holder.usernameTextView.setText(score.getUsername());
        holder.movesTextView.setText(score.getMoves() + " hamle");
        holder.detailsTextView.setText(score.getDetails());
        holder.timeTextView.setText(score.getTime());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView movesTextView;
        TextView detailsTextView;
        TextView timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            movesTextView = itemView.findViewById(R.id.movesTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
