package org.alfredo.tekmirapedia.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Kamus implements Parcelable {
    private int idKamus;
    private String indo, inggris, uraian;

    public Kamus() {
    }


    protected Kamus(Parcel in) {
        idKamus = in.readInt();
        indo = in.readString();
        inggris = in.readString();
        uraian = in.readString();
    }

    public static final Creator<Kamus> CREATOR = new Creator<Kamus>() {
        @Override
        public Kamus createFromParcel(Parcel in) {
            return new Kamus(in);
        }

        @Override
        public Kamus[] newArray(int size) {
            return new Kamus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idKamus);
        parcel.writeString(indo);
        parcel.writeString(inggris);
        parcel.writeString(uraian);
    }

    public int getIdKamus() {
        return idKamus;
    }

    public void setIdKamus(int idKamus) {
        this.idKamus = idKamus;
    }

    public String getIndo() {
        return indo;
    }

    public void setIndo(String indo) {
        this.indo = indo;
    }

    public String getInggris() {
        return inggris;
    }

    public void setInggris(String inggris) {
        this.inggris = inggris;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }
}
