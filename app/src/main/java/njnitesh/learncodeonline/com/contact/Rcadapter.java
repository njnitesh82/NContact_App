package njnitesh.learncodeonline.com.contact;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmResults;

public class Rcadapter extends RecyclerView.Adapter<Rcadapter.Rcholder> {
    Activity ctx;
    int[] color;
    RealmResults<model> data;
    Random random;
    int r=0;
    public Rcadapter(Activity a ,RealmResults<model> datad,int [] color) {
        this.ctx=a;
        this.data=datad;
        this.color= color;
        random=new Random();
    }

    @NonNull
    @Override
    public Rcholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Rcholder(LayoutInflater.from(ctx).inflate(R.layout.con_card,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Rcholder rcholder, final int i) {
          final model single=data.get(i);
          r=random.nextInt(color.length-1);
          //setuping the random color background
          //rcholder.av.setImageResource(r);

          //finding the first latter of Name
          char first=single.getName().toUpperCase().charAt(0);
          rcholder.avt.setText(""+first);
          //rcholder.av.setImageResource(r);
          Picasso.with(ctx).load(color[r]).fit().into(rcholder.av);

          rcholder.avname.setText(single.getName());
          rcholder.avdesig.setText(single.getDesig());

          rcholder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent=new Intent(ctx,Profile.class);
                  intent.putExtra("selection",i);
                  intent.putExtra("uid",single.getUid());
                  ctx.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Rcholder extends RecyclerView.ViewHolder{
         TextView avt,avname, avdesig;
         ImageView av;
        public Rcholder(@NonNull View itemView) {
            super(itemView);
            av=itemView.findViewById(R.id.av);
            avt=itemView.findViewById(R.id.avt);
            avname=itemView.findViewById(R.id.avname);
            avdesig=itemView.findViewById(R.id.avdesig);

        }
    }
}
