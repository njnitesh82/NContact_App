package njnitesh.learncodeonline.com.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
   int position;
   ImageView pback,
             pcall,
             pmsg,
             pwhats;
   TextView  pname,
             pdesig,
             pnumber,
             pemail;

   CircleImageView pavtar;

   ImageUtils imageUtils;
    private RealmApp app;

    private  String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        imageUtils=new ImageUtils();

        app= (RealmApp) getApplication();

        //position=getIntent().getExtras().getInt("selection",0);

        //recieving intent and their data.
        uid=getIntent().getExtras().getString("uid");
        model m=app.getQuery().equalTo("uid",uid).findFirst();

        //finding views
        id_init();

        //seting data withview
        set_data(m);

        //setuping clicklisner
        click_listners();







    }


    void id_init(){
        pback=findViewById(R.id.pback);
        pavtar=findViewById(R.id.pavtar);
        pcall=findViewById(R.id.pcall);
        pmsg=findViewById(R.id.pmsg);
        pwhats=findViewById(R.id.pwhats);
        pname=findViewById(R.id.pname);
        pdesig=findViewById(R.id.pdesig);
        pnumber=findViewById(R.id.pnumber);
        pemail=findViewById(R.id.pemail);

        //end of id_init
    }


    void click_listners(){
        pcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+ PhoneNumberUtils.formatNumber(pnumber.getText().toString().trim())));
                if (ActivityCompat.checkSelfPermission(Profile.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    //Log.d(TAG, "onClick: clicked");
                    startActivity(call);
                }
                else{
                    Toast.makeText(Profile.this, "failed", Toast.LENGTH_SHORT).show();
                }




            }
        });
        pmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        pwhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Profile.super.onBackPressed();
            }
        });

        //end of click_listner
    }


    void set_data(model sd){
        String name=sd.getName();
        String desig=sd.getDesig();
        String number=sd.getNumber();
        String email=sd.getEmail();
        byte[] av=sd.getAvtar();

        if(!name.isEmpty()){ pname.setText(name);}

        if(!desig.isEmpty()){pdesig.setText(desig);}else pdesig.setText("");

        if(!number.isEmpty()){pnumber.setText(number);}else pnumber.setText("");

        if(!email.isEmpty()){pemail.setText(email);}else{pemail.setVisibility(View.GONE);
                                                         findViewById(R.id.sec2).setVisibility(View.GONE);}

        if(av.length>0 && av!=null){
            try{
            Bitmap bm=imageUtils.byteArray2image(av);
            pavtar.setImageBitmap(bm);}catch (Exception e){
                Toast.makeText(this, "error with image", Toast.LENGTH_SHORT).show();
            }
        }
   //end of set_data
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
