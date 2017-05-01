package br.com.mob11.baas.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.com.mob11.baas.R;

public class ResetSenhaActivity extends BaseActivity {

    private Context context;
    private EditText emailCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);

        context = this;

        emailCadastro = (EditText) findViewById(R.id.email_cadastrato);
        Button botaoEnviarEmail = (Button) findViewById(R.id.botao_enviar_email);

        botaoEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailCadastro.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    exibeDialogoAlerta(context, "favor informar e-mail!");
                    return;
                }
                redefinirSenha(email);
            }
        });
    }

    public void redefinirSenha(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "e-mail enviado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}
