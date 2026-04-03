package com.example.lab_15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

import com.example.lab_15.Adapter.EtudiantAdapter;
import com.example.lab_15.classes.Etudiant;
import com.example.lab_15.service.EtudiantService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nom, prenom, id, search;
    Button add, chercher, supprimer;
    TextView res;

    RecyclerView recycler;
    EtudiantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EtudiantService es = new EtudiantService(this);

        // init views
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        id = findViewById(R.id.id);
        search = findViewById(R.id.search);

        add = findViewById(R.id.bn);
        chercher = findViewById(R.id.load);
        supprimer = findViewById(R.id.delete);

        res = findViewById(R.id.res);

        // RecyclerView
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EtudiantAdapter(es.findAll());
        recycler.setAdapter(adapter);

        //  FILTRAGE
        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Etudiant> filtered = new ArrayList<>();

                for (Etudiant e : es.findAll()) {
                    if (e.getNom().toLowerCase().contains(s.toString().toLowerCase())) {
                        filtered.add(e);
                    }
                }

                adapter.setList(filtered);
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        //  AJOUT
        add.setOnClickListener(v -> {
            es.create(new Etudiant(
                    nom.getText().toString(),
                    prenom.getText().toString()
            ));

            nom.setText("");
            prenom.setText("");

            adapter.setList(es.findAll());

            Toast.makeText(this, "Ajout réussi", Toast.LENGTH_SHORT).show();
        });

        //  RECHERCHE
        chercher.setOnClickListener(v -> {
            if (id.getText().toString().isEmpty()) {
                Toast.makeText(this, "Entrer ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Etudiant e = es.findById(Integer.parseInt(id.getText().toString()));

            if (e == null) {
                res.setText("");
                Toast.makeText(this, "Introuvable", Toast.LENGTH_SHORT).show();
            } else {
                res.setText(e.getNom() + " " + e.getPrenom());
            }
        });

        //  SUPPRESSION
        supprimer.setOnClickListener(v -> {
            if (id.getText().toString().isEmpty()) {
                Toast.makeText(this, "Entrer ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Etudiant e = es.findById(Integer.parseInt(id.getText().toString()));

            if (e != null) {
                es.delete(e);
                res.setText("");

                adapter.setList(es.findAll());

                Toast.makeText(this, "Supprimé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Aucun étudiant", Toast.LENGTH_SHORT).show();
            }
        });
    }
}