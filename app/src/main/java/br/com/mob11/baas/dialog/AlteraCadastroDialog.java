package br.com.mob11.baas.dialog;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.mob11.baas.R;
import br.com.mob11.baas.bean.Pessoa;

public class AlteraCadastroDialog extends DialogFragment implements View.OnClickListener{

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoIdade;
    private TextView botaoFechar;
    private Button botaoAlterar;

    private Pessoa pessoa;


    public AlteraCadastroDialog() {
    }

    public static AlteraCadastroDialog newInstance(Pessoa pessoa) {
        AlteraCadastroDialog frag = new AlteraCadastroDialog();
        frag.setPessoa(pessoa);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_altera_cadastro, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setCanceledOnTouchOutside(Boolean.FALSE);

        init(view);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == botaoAlterar.getId()) {

            pessoa.setNome(campoNome.getText().toString());
            pessoa.setEmail(campoEmail.getText().toString());
            pessoa.setIdade(Integer.parseInt(campoIdade.getText().toString()));

            AlterarCadastroListener listener = (AlterarCadastroListener) getActivity();
            listener.onClickAlterarCadastro(pessoa);
        }

        dismiss();

    }

    private void init(View view) {
        campoNome = (EditText) view.findViewById(R.id.campo_nome);
        campoEmail = (EditText) view.findViewById(R.id.campo_email);
        campoIdade = (EditText) view.findViewById(R.id.campo_idade);
        botaoFechar = (TextView) view.findViewById(R.id.botao_fechar);
        botaoAlterar = (Button) view.findViewById(R.id.botao_alterar);

        campoNome.setText(pessoa.getNome());
        campoEmail.setText(pessoa.getEmail());
        campoIdade.setText(String.valueOf(pessoa.getIdade()));

        botaoFechar.setOnClickListener(this);
        botaoAlterar.setOnClickListener(this);

        campoNome.requestFocus();
    }

    public interface AlterarCadastroListener {
        void onClickAlterarCadastro(Pessoa p);
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
