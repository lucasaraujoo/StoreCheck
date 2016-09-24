package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 23/09/2016.
 */
public class MCotacao implements Parcelable, Comparable<MCotacao> {
    protected String Nome;
    protected int IDCliente;



    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Nome);
        dest.writeInt(this.IDCliente);
    }

    public MCotacao() {
    }

    protected MCotacao(Parcel in) {
        this.Nome = in.readString();
        this.IDCliente = in.readInt();
    }

    public static final Creator<MCotacao> CREATOR = new Creator<MCotacao>() {
        @Override
        public MCotacao createFromParcel(Parcel source) {
            return new MCotacao(source);
        }

        @Override
        public MCotacao[] newArray(int size) {
            return new MCotacao[size];
        }
    };



    @Override
    public int compareTo(MCotacao mCotacao) {
        return 0;
    }
}
