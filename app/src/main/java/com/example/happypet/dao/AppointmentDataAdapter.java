package com.example.happypet.dao;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.happypet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentDataAdapter extends FirebaseRecyclerAdapter <AppointmentDataModel, AppointmentDataAdapter.appointViewHolder>{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AppointmentDataAdapter(@NonNull FirebaseRecyclerOptions<AppointmentDataModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull appointViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull AppointmentDataModel model) {
        holder.dateA.setText(model.getDate());
        holder.timeA.setText(model.getTime());
        holder.petNameA.setText(model.getPetName());
        holder.petOwnerA.setText(model.getPetOwner());
        holder.petOwnerPhoneA.setText(model.getPetOwnerPhone());
        holder.reasonA.setText(model.getReason());

        Glide.with(holder.appointImg.getContext())
                .load(model).placeholder(R.drawable.pet_owner_logo)
                .circleCrop().error(R.drawable.pet_owner_logo).into(holder.appointImg);


       holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(holder.petNameA.getContext());
               builder.setTitle("Are you Sure?");

               builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       FirebaseDatabase.getInstance().getReference().child("Appointments")
                               .child(getRef(position).getKey()).removeValue();
                   }
               });
               builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               builder.show();

           }
       });

    }

    @NonNull
    @Override
    public appointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointemnts_dashboard,parent,false);
        return new appointViewHolder(view);

    }

    class appointViewHolder extends RecyclerView.ViewHolder{

        CircleImageView appointImg;
        TextView dateA, timeA, petNameA, petOwnerA, petOwnerPhoneA, reasonA;
        Button acceptBtn, deleteBtn;

        public appointViewHolder(@NonNull View itemView) {
            super(itemView);

            appointImg = (CircleImageView)itemView.findViewById(R.id.appointImg);
            dateA = (TextView)itemView.findViewById(R.id.dateA);
            timeA = (TextView)itemView.findViewById(R.id.timeA);
            petNameA = (TextView)itemView.findViewById(R.id.petNameA);
            petOwnerA = (TextView)itemView.findViewById(R.id.petOwnerA);
            petOwnerPhoneA = (TextView)itemView.findViewById(R.id.petOwnerPhoneA);
            reasonA = (TextView)itemView.findViewById(R.id.reasonA);

            acceptBtn = (Button)itemView.findViewById(R.id.acceptBtn);
            deleteBtn = (Button)itemView.findViewById(R.id.deleteBtn);


        }
    }
}
