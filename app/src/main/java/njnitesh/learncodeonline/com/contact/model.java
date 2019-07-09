package njnitesh.learncodeonline.com.contact;

import io.realm.RealmObject;

public class model extends RealmObject {
    String uid="";
    String  name="";
    String desig="";
    String number="";
    String email="";
    byte[] avtar ;

    public model(){

    }

    public model(String uid, String name, String desig, String number, String email, byte[] avtar) {
        this.uid = uid;
        this.name = name;
        this.desig = desig;
        this.number = number;
        this.email = email;
        this.avtar = avtar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvtar() {
        return avtar;
    }

    public void setAvtar(byte[] avtar) {
        this.avtar = avtar;
    }
}
