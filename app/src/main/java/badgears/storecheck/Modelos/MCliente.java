package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 25/09/2016.
 */
public class MCliente implements Parcelable, Comparable<MCliente> {
    protected int id;
    protected String Nome;
    protected String CPFCNPJ;
    protected String Cidade;

    protected MCliente(Parcel in) {
        id = in.readInt();
        Nome = in.readString();
        CPFCNPJ = in.readString();
        Cidade = in.readString();
    }

    public static final Creator<MCliente> CREATOR = new Creator<MCliente>() {
        @Override
        public MCliente createFromParcel(Parcel in) {
            return new MCliente(in);
        }

        @Override
        public MCliente[] newArray(int size) {
            return new MCliente[size];
        }
    };

    @Override
    public int compareTo(MCliente mCliente) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Nome);
        parcel.writeString(CPFCNPJ);
        parcel.writeString(Cidade);
    }
}
