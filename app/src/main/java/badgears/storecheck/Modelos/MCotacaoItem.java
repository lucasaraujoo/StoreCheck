package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 02/10/2016.
 */
public class MCotacaoItem implements Parcelable, Comparable<MCotacaoItem> {
    protected int Id;
    protected MProduto oProduto;
    protected double Preco1;
    protected double Preco2;
    protected Boolean bCotar;

    public MCotacaoItem(MProduto oProduto) {
        this.oProduto = oProduto;
        this.bCotar   = true;
        this.Preco1  = 0.00;
        this.Preco2  = 0.00;
    }

    protected MCotacaoItem(Parcel in) {
        oProduto = in.readParcelable(MProduto.class.getClassLoader());
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

    public double getPreco1() {
        return Preco1;
    }

    public void setPreco1(Float preco1) {
        Preco1 = preco1;
    }

    public double getPreco2() {
        return Preco2;
    }

    public void setPreco2(Float preco2) {
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
        parcel.writeParcelable(oProduto, i);
    }
}
