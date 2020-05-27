package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalgamer.R;

import java.util.List;

import modelo.Training;

public class ExerciciosAdapter extends RecyclerView.Adapter<ExerciciosAdapter.ExerciciosViewHolder> {
    private List<Training> trainings;

    public ExerciciosAdapter(List<Training> trainings) {
        this.trainings = trainings;
    }

    static class ExerciciosViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nome;

        ExerciciosViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nome = itemView.findViewById(R.id.tv_nome);
        }
    }

    @NonNull
    @Override
    public ExerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_exercicios, parent, false);

        return new ExerciciosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciciosViewHolder holder, int position) {
        Training training = trainings.get(position);

        holder.tv_nome.setText(training.getTraining());
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }
}
