package parth.first.studentmanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import parth.first.studentmanagmentsystem.databinding.ActivityRegistrationBinding;
import parth.first.studentmanagmentsystem.models.user;

public class registration extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private String name, email, password, phoneno;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = binding.passwordoe.getText().toString();
                email = binding.emailadd.getText().toString();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent=new Intent(registration.this,log_in.class);
                            startActivity(intent);

                            Toast.makeText(registration.this, "register successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registration.this, "please try again. something is wrong : "+e, Toast.LENGTH_LONG).show();
                    }
                });

                adddata();
            }
        });

    }
    private void adddata(){
        name = binding.name.getText().toString();
        phoneno = binding.phoneno.getText().toString();

        user user=new user();
        user.setName(name);
        user.setPhone(phoneno);
        user.setEmail(email);
        user.setPassword(password);
        firebaseFirestore.collection("users").document().set(user);
    }
}