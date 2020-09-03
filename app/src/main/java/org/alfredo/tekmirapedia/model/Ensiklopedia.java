package org.alfredo.tekmirapedia.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ensiklopedia implements Parcelable {
    private int idEnsiklopedia;
    private String istilahIndo, istilahInggris, uraian;

    public Ensiklopedia() {
    }

    protected Ensiklopedia(Parcel in) {
        idEnsiklopedia = in.readInt();
        istilahIndo = in.readString();
        istilahInggris = in.readString();
        uraian = in.readString();
    }

    public static final Creator<Ensiklopedia> CREATOR = new Creator<Ensiklopedia>() {
        @Override
        public Ensiklopedia createFromParcel(Parcel in) {
            return new Ensiklopedia(in);
        }

        @Override
        public Ensiklopedia[] newArray(int size) {
            return new Ensiklopedia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idEnsiklopedia);
        parcel.writeString(istilahIndo);
        parcel.writeString(istilahInggris);
        parcel.writeString(uraian);
    }

    public int getIdEnsiklopedia() {
        return idEnsiklopedia;
    }

    public void setIdEnsiklopedia(int idEnsiklopedia) {
        this.idEnsiklopedia = idEnsiklopedia;
    }

    public String getIstilahIndo() {
        return istilahIndo;
    }

    public void setIstilahIndo(String istilahIndo) {
        this.istilahIndo = istilahIndo;
    }

    public String getIstilahInggris() {
        return istilahInggris;
    }

    public void setIstilahInggris(String istilahInggris) {
        this.istilahInggris = istilahInggris;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }
}
