package com.example.lab_15.Adapter;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.lab_15.R;

import com.example.lab_15.classes.Etudiant;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> {

    private List<Etudiant> list;

    public EtudiantAdapter(List<Etudiant> list) {
        this.list = list;
    }

    public void setList(List<Etudiant> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom, prenom;

        public ViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.tvNom);
            prenom = itemView.findViewById(R.id.tvPrenom);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_etudiant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Etudiant e = list.get(position);
        holder.nom.setText(e.getNom());
        holder.prenom.setText(e.getPrenom());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}