package parth.first.studentmanagmentsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import parth.first.studentmanagmentsystem.adapters.informationAdapter;
import parth.first.studentmanagmentsystem.databinding.ActivityStudentDataBinding;
import parth.first.studentmanagmentsystem.models.adaptermodel;
import parth.first.studentmanagmentsystem.models.user;

public class student_data extends AppCompatActivity {

    ActivityStudentDataBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<adaptermodel> arrayList;
    private FirebaseFirestore firestore;
    private informationAdapter adapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudentDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("fetching data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firestore= FirebaseFirestore.getInstance();
        arrayList=new ArrayList<>();
        adapter=new informationAdapter(student_data.this,arrayList);
        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        firestore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot data:queryDocumentSnapshots.getDocuments()){
                    adaptermodel adaptermodel=data.toObject(adaptermodel.class);
                    arrayList.add(adaptermodel);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        });

    }
}