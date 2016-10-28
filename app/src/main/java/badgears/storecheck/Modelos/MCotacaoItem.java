package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 02/10/2016.
 */
public class MCotacaoItem implements Parcelable, Comparable<MCotacaoItem> {
    protected int Id;
    protected MProduto oProduto;
    protected String Preco1;
    protected String Preco2;
    protected Boolean bCotar;

    public MCotacaoItem(MProduto oProduto) {
        this.oProduto = oProduto;
        this.bCotar   = false;
        this.Preco1  = "0,00";
        this.Preco2  = "0,00";
    }

    protected MCotacaoItem(Parcel in) {
        Id       = in.readInt();
        oProduto = in.readParcelable(MProduto.class.getClassLoader());
        Preco1      = in.readString();
        Preco2      = in.readString();
        bCotar   = in.readByte() != 0;
    }

    public static final Creator<MCotacaoItem> CREATOR = new Creator<MCotacaoItem>() {
        @Override
        public MCotacaoItem createFromParcel(Parcel in) {
            return new MCotacaoItem(in);
        }

        @Override
        public MCotacaoItem[] newArray(int size) {
            return new MCotacaoItem[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Boolean getbCotar() {
        return bCotar;
    }

    public void setbCotar(Boolean bCotar) {
        this.bCotar = bCotar;
    }

    public MProduto getoProduto() {
        return oProduto;
    }

    public void setoProduto(MProduto oProduto) {
        this.oProduto = oProduto;
    }

    public String getPreco1() {
        return Preco1;
    }

    public void setPreco1(String preco1) {
        Preco1 = preco1;
    }

    public String getPreco2() {
        return Preco2;
    }

    public void setPreco2(String preco2) {
        Preco2 = preco2;
    }

    @Override
    public int compareTo(MCotacaoItem mCotacaoItem) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeParcelable(oProduto, i);
        parcel.writeString(Preco1);
        parcel.writeString(Preco2);
        parcel.writeByte((byte) (bCotar ? 1 : 0));
    }
}
