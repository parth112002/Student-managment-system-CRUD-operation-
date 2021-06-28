package parth.first.studentmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import parth.first.studentmanagmentsystem.databinding.ActivitySignInBinding;

public class log_in extends AppCompatActivity {
    ActivitySignInBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private String email, password;
    private FirebaseAuth auth=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(binding.emailadd.getText().toString(),binding.passwordoe.getText().toString()).addOnCompleteListener(log_in.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(log_in.this,MainActivity.class));
                        }else {
                            Toast.makeText(log_in.this, "wrong email and password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(log_in.this, "error : "+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        binding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(log_in.this, registration.class);
                startActivity(intent);
            }
        });

        if (!(auth.getCurrentUser() ==null)){
            Intent intent=new Intent(log_in.this,MainActivity.class);
            startActivity(intent);
        }
    }
}