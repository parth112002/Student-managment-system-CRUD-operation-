package parth.first.studentmanagmentsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import parth.first.studentmanagmentsystem.R;
import parth.first.studentmanagmentsystem.models.adaptermodel;
import parth.first.studentmanagmentsystem.models.user;

public class informationAdapter extends RecyclerView.Adapter<informationAdapter.viewholder> {

    Context context;
    ArrayList<adaptermodel> userlist;

    public informationAdapter(Context context, ArrayList<adaptermodel> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @NotNull
    @Override
    public viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterlayout,parent,false) ;
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull informationAdapter.viewholder holder, int position) {
        holder.name.setText(userlist.get(position).getName());
        holder.phone.setText(userlist.get(position).getPhone());
        holder.email.setText(userlist.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        private final TextView name,phone,email;
        public viewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.rename);
            phone=(TextView) itemView.findViewById(R.id.rephone);
            email=(TextView) itemView.findViewById(R.id.reemail);
        }
    }
}
