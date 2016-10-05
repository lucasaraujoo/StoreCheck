package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 25/09/2016.
 */
public class
MProduto implements Parcelable, Comparable<MProduto> {
    protected int id;
    protected String Descricao;
    protected String Categoria;
    protected String Tipo;


    protected MProduto(Parcel in) {
        id = in.readInt();
        Descricao = in.readString();
        Categoria = in.readString();
        Tipo = in.readString();
    }

    public MProduto(int idPro, String descricao, String categoria, String tipo) {
        this.id = idPro;
        this.Descricao = descricao;
        this.Categoria = categoria;
        this.Tipo = tipo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        this.Categoria = categoria;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public static final Creator<MProduto> CREATOR = new Creator<MProduto>() {
        @Override
        public MProduto createFromParcel(Parcel in) {
            return new MProduto(in);
        }

        @Override
        public MProduto[] newArray(int size) {
            return new MProduto[size];
        }
    };

    @Override
    public int compareTo(MProduto mProduto) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Descricao);
        parcel.writeString(Categoria);
        parcel.writeString(Tipo);
    }
}
