package aplications.villadiego.datainformation;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Parcelable, Serializable {

    private String doc;
    private String name;
    private int stratum;
    private double salary;
    private String edu;

    public User(String doc, String name, int stratum, double salary, String edu) {
        this.doc = doc;
        this.name = name;
        this.stratum = stratum;
        this.salary = salary;
        this.edu = edu;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStratum() {
        return stratum;
    }

    public void setStratum(int stratum) {
        this.stratum = stratum;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    protected User(Parcel in){
        doc = in.readString();
        name = in.readString();
        stratum = in.readInt();
        salary = in.readDouble();
        edu = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(doc);
        dest.writeString(name);
        dest.writeInt(stratum);
        dest.writeDouble(salary);
        dest.writeString(edu);
    }

    @Override
    public String toString() {
        return "User{" +
                "doc='" + doc + '\'' +
                ", name='" + name + '\'' +
                ", stratum=" + stratum +
                ", salary=" + salary +
                ", edu='" + edu + '\'' +
                '}';
    }
}
