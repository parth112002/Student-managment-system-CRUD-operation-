package parth.first.studentmanagmentsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import parth.first.studentmanagmentsystem.databinding.ActivityEditdataBinding;

public class editdata extends AppCompatActivity {

    ActivityEditdataBinding binding;
    private CollectionReference notebookref ;
    private FirebaseFirestore firestore= FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private String name, phone, email, password;
    private String dname,dphone,demail,dpassword,id;
    private ProgressDialog progressbar,progressbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditdataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressbar=new ProgressDialog(editdata.this);
        progressbar.setMessage("fetching data");
        progressbar.setCancelable(false);
        progressbar.show();

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        firestore = FirebaseFirestore.getInstance();
        if (user != null) {
            firestore.collection("users").whereEqualTo("email",userEmail).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for(DocumentSnapshot data:queryDocumentSnapshots){
                            name=(String)data.getString("name");
                            phone=(String)data.getString("phone");
                            password=(String)data.getString("password");

                            binding.n.setText(name);
                            binding.p.setText(phone);
                            binding.pwd.setText(password);

                            progressbar.dismiss();
                        }
                    });

            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressbar1=new ProgressDialog(editdata.this);
                    progressbar1.setMessage("updating data");
                    progressbar1.show();

                    updatedata();

                    progressbar1.dismiss();
                    Toast.makeText(editdata.this, "updated successfully", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void updatedata() {
        dname=binding.n.getText().toString();
        dphone=binding.p.getText().toString();
        dpassword=binding.pwd.getText().toString();

        notebookref= firestore.collection("users");
        notebookref.addSnapshotListener(this, (value, error) -> {

            for (DocumentChange dc:value.getDocumentChanges()){
                DocumentSnapshot documentSnapshot = dc.getDocument();

                String docemail=documentSnapshot.getString("email");
                if (docemail.equals(user.getEmail())){
                    id=documentSnapshot.getId();
                    notebookref.document(id).update("name",dname);
                    notebookref.document(id).update("phone",dphone);
                    notebookref.document(id).update("password",dpassword);
                }
            }
        });
    }
}