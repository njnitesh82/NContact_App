package njnitesh.learncodeonline.com.contact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class search extends AppCompatActivity {
    EditText sbox;
    ImageView sid;
    RecyclerView srec;
    int[] color_back;
    RealmApp app;
    private RealmResults<model> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        app= (RealmApp) getApplication();

        sbox=findViewById(R.id.sbox);
        sid=findViewById(R.id.sid);
        srec=findViewById(R.id.srec);
        srec.setLayoutManager(new LinearLayoutManager(this));

        color_back=new int[]{R.drawable.eone, R.drawable.etwo, R.drawable.ethree, R.drawable.efour, R.drawable.efive, R.drawable.esix, R.drawable.eseven};



        sbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final StringBuilder sb = new StringBuilder(charSequence.length());

                sb.append(charSequence);

                results=app.getQuery().beginsWith("name",sb.toString(),Case.INSENSITIVE).findAll();
                Log.d("search", "onTextChanged: text "+sb.toString()+" number "+results.size());

                srec.setAdapter(new Rcadapter(search.this,results,color_back));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
