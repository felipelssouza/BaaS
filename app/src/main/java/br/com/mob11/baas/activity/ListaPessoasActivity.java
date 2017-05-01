package br.com.mob11.baas.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mob11.baas.R;
import br.com.mob11.baas.adapter.ListaPessoaAdapter;
import br.com.mob11.baas.bean.Pessoa;
import br.com.mob11.baas.dialog.AlteraCadastroDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ListaPessoasActivity extends BaseActivity implements AlteraCadastroDialog.AlterarCadastroListener, ListaPessoaAdapter.RemoverCadastroListener {

    private RecyclerView mRecyclerView;
    private ListaPessoaAdapter adapter;
    private ProgressBar progressBar;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoas);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mDatabase = FirebaseDatabase.getInstance().getReference("pessoa");

        inicializaLista();
        carregaListaFirebase();
    }

    private void inicializaLista() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_lista);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),
                        layoutManager.getOrientation());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void carregaListaFirebase() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Pessoa> pessoas = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Pessoa pessoa = postSnapshot.getValue(Pessoa.class);
                    pessoa.setId(postSnapshot.getKey());
                    pessoas.add(pessoa);
                }
                if (adapter == null) {
                    carregaLista(pessoas);
                } else {
                    adapter.atualizaLista(pessoas);
                    progressBar.setVisibility(GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FALHA FIREBASE: ", databaseError.getMessage());
            }
        });
    }

    private void carregaLista(List<Pessoa> listaPessoa) {
        adapter = new ListaPessoaAdapter(listaPessoa, this);
        mRecyclerView.setAdapter(adapter);
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventoAnalytics("ListaPessoasActivity");
    }

    @Override
    public void onClickAlterarCadastro(Pessoa p) {
        progressBar.setVisibility(VISIBLE);
        atualizaCadastro(p);
    }

    private void atualizaCadastro(Pessoa p) {
        mDatabase.child(p.getId()).setValue(p);
    }

    @Override
    public void onClickRemoverCadastro(String id) {
        progressBar.setVisibility(VISIBLE);
        removeCadastro(id);
    }

    private void removeCadastro(String id) {
        mDatabase.child(id).removeValue();
    }
}
