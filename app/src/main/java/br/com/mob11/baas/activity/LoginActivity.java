package br.com.mob11.baas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.mob11.baas.MainActivity;
import br.com.mob11.baas.R;

import static android.view.View.VISIBLE;

public class LoginActivity extends BaseActivity {

    private final String TAG = "LOG_LOGIN";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText email;
    EditText senha;
    TextView registrar;
    TextView resetSenha;
    Button botaoAcessar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
            }
        };

        email = (EditText) findViewById(R.id.texto_email_login);
        senha = (EditText) findViewById(R.id.texto_senha);
        registrar = (TextView) findViewById(R.id.botao_registrar);
        resetSenha = (TextView) findViewById(R.id.botao_reset_senha);
        botaoAcessar = (Button) findViewById(R.id.botao_acessar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(VISIBLE);
                criarConta(email.getText().toString(), senha.getText().toString());
            }
        });

        resetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direcionarResetSenha();
            }
        });

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(VISIBLE);
                acessar();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        eventoAnalytics("LoginActivity");
    }

    private void acessar() {
        fazerLogin(email.getText().toString(), senha.getText().toString());
    }

    public void fazerLogin(String email, String senha) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            exibeDialogoAlerta(this, "favor preencher e-mail e senha.");
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Falha no Login",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        direcionarLogin();
                    }
                });
    }

    private void direcionarLogin() {
        email.setText("");
        senha.setText("");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        progressBar.setVisibility(View.GONE);
    }

    private void direcionarResetSenha() {
        Intent i = new Intent(this, ResetSenhaActivity.class);
        startActivity(i);
    }

    public void criarConta(String email, String senha) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            exibeDialogoAlerta(this, "necessário preencher e-mail e senha para registro.");
            progressBar.setVisibility(View.GONE);
            return;
        }

        try {
            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Deu ruim");
                        }
                    })
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "usuário criado com sucesso!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Falha no criar usuario",
                                        Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } catch (Exception e) {
            Log.d("AQUI", e.getMessage());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}

