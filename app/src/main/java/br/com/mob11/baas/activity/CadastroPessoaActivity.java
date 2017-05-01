package br.com.mob11.baas.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.com.mob11.baas.R;
import br.com.mob11.baas.bean.Pessoa;

import static android.view.View.GONE;

public class CadastroPessoaActivity extends BaseActivity {

    private final String FIREBASE_CHILD = "pessoa";
    private DatabaseReference mDatabase;

    EditText nome;
    EditText email;
    EditText idade;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        mDatabase = FirebaseDatabase.getInstance().getReference(FIREBASE_CHILD);

        nome = (EditText) findViewById(R.id.texto_nome);
        email = (EditText) findViewById(R.id.texto_email);
        idade = (EditText) findViewById(R.id.texto_idade);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventoAnalytics("CadastroPessoaActivity");
    }

    public void gravarPessoa(View view){
        progressBar.setVisibility(View.VISIBLE);
        Pessoa pessoa = new Pessoa(nome.getText().toString(), email.getText().toString(),
                Integer.parseInt(idade.getText().toString()));

        inserirFirebase(pessoa);
    }

    private void inserirFirebase(Pessoa pessoa) {
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(pessoa);
        progressBar.setVisibility(GONE);
        finish();
    }
}
