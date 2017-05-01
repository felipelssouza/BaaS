package br.com.mob11.baas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.mob11.baas.activity.BaseActivity;
import br.com.mob11.baas.activity.CadastroPessoaActivity;
import br.com.mob11.baas.activity.ListaPessoasActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirCadastro(View view) {
        Intent i = new Intent(this, CadastroPessoaActivity.class);
        startActivity(i);
    }

    public void abrirListaPessoas(View view) {
        Intent i = new Intent(this, ListaPessoasActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventoAnalytics("MainActivity");
    }

}
