package br.com.mob11.baas.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.mob11.baas.R;
import br.com.mob11.baas.bean.Pessoa;
import br.com.mob11.baas.dialog.AlteraCadastroDialog;


public class ListaPessoaAdapter extends RecyclerView.Adapter<ListaPessoaAdapter.CustomViewHolder> {

    private List<Pessoa> mListaPessoas;
    private Context mContext;

    public ListaPessoaAdapter(List<Pessoa> mListaPessoas, Context mContext) {
        this.mListaPessoas = mListaPessoas;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_pessoa, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder viewHolder, int position) {
        final Pessoa pessoa = mListaPessoas.get(position);

        viewHolder.nome.setText(pessoa.getNome());
        viewHolder.email.setText(pessoa.getEmail());
        viewHolder.idade.setText(pessoa.getIdade().toString());

        viewHolder.remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoverCadastroListener listener = (RemoverCadastroListener) mContext;
                listener.onClickRemoverCadastro(pessoa.getId());
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((Activity) mContext).getFragmentManager();
                AlteraCadastroDialog dialog = AlteraCadastroDialog.newInstance(pessoa);
                dialog.show(fm, "fragment_altera_cadastro");
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mListaPessoas ? mListaPessoas.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView nome;
        private TextView email;
        private TextView idade;
        private TextView remover;

        private CustomViewHolder(View view) {
            super(view);
            this.nome = (TextView) view.findViewById(R.id.campo_lista_nome);
            this.email = (TextView) view.findViewById(R.id.campo_lista_email);
            this.idade = (TextView) view.findViewById(R.id.campo_lista_idade);
            this.remover = (TextView) view.findViewById(R.id.botao_remover);
        }
    }

    public void atualizaLista(List<Pessoa> pessoas) {
        this.mListaPessoas = pessoas;
        notifyDataSetChanged();
    }

    public interface RemoverCadastroListener {
        void onClickRemoverCadastro(String id);
    }

}

