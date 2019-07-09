package njnitesh.learncodeonline.com.contact;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class RealmApp extends Application{
    Realm realm;
    RealmQuery<model> query;
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm=Realm.getDefaultInstance();
    }


    public RealmQuery<model> getQuery(){
        return query=realm.where(model.class);
    }
}
