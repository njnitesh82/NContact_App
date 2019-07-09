package njnitesh.learncodeonline.com.contact;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class home extends AppCompatActivity {
    String TAG="Mainactivity";
    RecyclerView recyclerView;
    ImageView burger,search;
    Realm realm;
    ImageUtils imageUtils;
    /*form views*/
    CircleImageView avtar;
    EditText name, desig, number, email;
    TextView save;
    TextView close;
    int[] color_back;
    public static RealmResults<model> data;
    public static RealmQuery<model>  query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //realm=Realm.getDefaultInstance();
        //initiating oblect of application class
        RealmApp app= (RealmApp) getApplication();
        //query=realm.where(model.class);
        query=app.getQuery();

        imageUtils=new ImageUtils();

        color_back=new int[]{R.drawable.eone, R.drawable.etwo, R.drawable.ethree, R.drawable.efour, R.drawable.efive, R.drawable.esix, R.drawable.eseven};

        recyclerView=findViewById(R.id.rec);
        burger=findViewById(R.id.burger);
        search=findViewById(R.id.search);


        //setLayout manager to recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkpermission();
        }







        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddForm();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home.this,search.class));

            }
        });








        data=query.findAll();
         /*
         * here we checking the data abse is there some contact which is having uid 1,2 and 3.
         * if these uid is existing the init-contact() will not execute otherwise it will execute*/
        int isResult=query.equalTo("uid","1").or()
                .equalTo("uid","2").or()
                .equalTo("uid","3").findAll().size();
        if(isResult==0){
            inital_contact();
            Toast.makeText(this, ""+isResult, Toast.LENGTH_SHORT).show();
        }



        //declaring adapter
        final Rcadapter rcadapter=new Rcadapter(home.this,data,color_back);

        recyclerView.setAdapter(rcadapter);

        data.addChangeListener(new RealmChangeListener<RealmResults<model>>() {
            @Override
            public void onChange(RealmResults<model> models) {


                //setupimg data with the adapter .
                rcadapter.notifyDataSetChanged();

            }
        });



    }


  public void showAddForm(){

      final Dialog ad=new Dialog(home.this);
      ad.setContentView(R.layout.add_con);
      ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      name=ad.findViewById(R.id.name);
      desig=ad.findViewById(R.id.desig);
      number=ad.findViewById(R.id.number);
      email=ad.findViewById(R.id.email);
      avtar=ad.findViewById(R.id.avtar);
      close=ad.findViewById(R.id.close);
      save=ad.findViewById(R.id.save);

      ad.show();

      avtar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              pickIMage();
          }
      });
      close.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              ad.dismiss();
          }
      });

      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if( !name.getText().toString().isEmpty()

                      ){
                  insert();
                  ad.dismiss();
              }

          }
      });



  }





  void insert(){
        final byte[] avt=imageUtils.image2byteArray(avtar);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model m=realm.createObject(model.class);
                m.setUid(UUID.randomUUID().toString());
                m.setName(name.getText().toString());
                m.setDesig(desig.getText().toString());
                m.setNumber(number.getText().toString());
                m.setEmail(email.getText().toString());
                m.setAvtar(avt);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(home.this, "inserted", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(home.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

  }

  List<model> getData(){
        List<model> data=new ArrayList<>();
        data.clear();
       data.addAll(realm.where(model.class).findAll());

     return data;
     //end of getdata
  }








    void pickIMage(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(Intent.createChooser(intent,"select image"),1002);


        //end of pickImage
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1002){
            try {
                Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                avtar.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkpermission(){
        /*
         * making function for checking the permission
         *
         *
         *
         * */

        if(
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            String per[]={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE};
            requestPermissions(per,1000);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000  && grantResults.length>0
                && grantResults[0]== PackageManager.PERMISSION_GRANTED
                && grantResults[1]== PackageManager.PERMISSION_GRANTED
                && grantResults[2]== PackageManager.PERMISSION_GRANTED
                ){
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "permission is not granted", Toast.LENGTH_SHORT).show();
        }


    }




    void inital_contact(){
      List<model> init_contact=new ArrayList<>();
      byte[] hitesh=imageUtils.image2byteArray(BitmapFactory.decodeResource(getResources(),R.drawable.hitesh));
      byte[] saksham=imageUtils.image2byteArray(BitmapFactory.decodeResource(getResources(),R.drawable.saksham));
      byte[] nitesh=imageUtils.image2byteArray(BitmapFactory.decodeResource(getResources(),R.drawable.nitesh));
      init_contact.add(new model(
              "1",
              "Hitesh",
              "founder",
              "7976241756",
              "hitesh@hiteshcoudhary.com",
              hitesh));
        init_contact.add(new model(
                "2",
                "Sakksham",
                "Co-founder",
                "7976241756",
                "saksham@learncodeonline.in",
                saksham));
        init_contact.add(new model(
                "3",
                "Nitesh",
                "Android App Devloper",
                "8560905479",
                "njnitesh82@gmail.com",
                nitesh));

        for(final model e:init_contact){

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                  model g=realm.createObject(model.class);
                  g.setUid(e.getUid());
                  g.setName(e.getName());
                  g.setDesig(e.getDesig());
                  g.setNumber(e.getNumber());
                  g.setEmail(e.getEmail());
                  g.setAvtar(e.getAvtar());
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(home.this, ""+e.getName()+" is added", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {

                }
            });


        }
    //end of initial_contact
    }


    @Override
    protected void onPause() {
        super.onPause();
        data.removeAllChangeListeners();

    }
}
