package badgears.storecheck;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import badgears.storecheck.Dao.DaoMain;
import badgears.storecheck.Modelos.MCategoria;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.Modelos.MItensRelatorio;
import jxl.CellFeatures;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 * Created by Tulio on 02/10/2016.
 */
public class Excel extends DaoMain {
    public Context ctx;
    public int TotalSim;
    public int TotalNao;
    public ArrayList<String> Opc = new ArrayList<String>();
    public ArrayList<String> Pr1 = new ArrayList<String>();
    public ArrayList<String> Pr2 = new ArrayList<String>();
    public ArrayList<Integer> QtdsNao = new ArrayList<Integer>();
    public ArrayList<Integer> QtdsSim = new ArrayList<Integer>();
    private ArrayList<MItensRelatorio> Itens = new ArrayList<MItensRelatorio>();
    public ArrayList<Integer> IdsUsados = new ArrayList<Integer>();
    public int Index=0;
    public  String IdTeste = "";
    public MCategoria ProdutoRelatorio;

    public Excel(Context contexto) {
        super(contexto);
        ctx = contexto;
    }
    private static WritableCellFormat getCellFormat(Colour colour,Colour colourFont, int FontSize) throws WriteException {
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, FontSize);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFont.setColour(colourFont);
        cellFormat.setBackground(colour);
        return cellFormat;
    }

    public void exportToExcel(String datarelatorio,boolean exibirTotais) throws IOException, WriteException{
        Cursor cursor = db.rawQuery("select Descricao, Id from produtos order by Descricao", null);
        //Query pra pegar  resto
        Cursor prodto = db.rawQuery("select p.*, (select c.Nome from cotacao c  where c.Id=p.IdCotacao) as Nome,  " +
                "(select c.Id from cotacao c  where c.Id=p.IdCotacao )  as IdCotaMaster, " +
                "(select count(*) from cotacao cc where cc.Data='" + datarelatorio + "') as QuantidadeC, cc.Nome as NomeCliente, " +
                "cota.Obs as Obs, cota.data as DataCotacao, cc.Cidade as Cidade, cc.Segmentacao as Segmentacao, cc.Telefone as TelefoneCliente " +
                "from produtoscotacao p inner join cotacao cota on cota.Id=p.IDCotacao " +
                "inner join clientes cc on cc.Id=cota.IDCliente " +
                "where  p.IdCotacao in (select co.Id from cotacao co  where co.Data='" + datarelatorio +"')" +
                " order by (select Descricao from produtos pp where pp.Id=p.Idproduto )"
                ,null);

        final String fileName = "StoreCheck.xls";
        //Salva o arquivo
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath());
        //Se o local que eu indiquei acima não existir, cria(Mas usei a raiz, então não vai ter problema).
        if(!directory.isDirectory()){
            directory.mkdirs();
        }
        //Crio o arquivo "File" com o caminho pego acima.
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("pt", "BR"));
        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            WritableSheet sheet = workbook.createSheet("StoreCheck MOBILE V1.0", 0);
            WritableSheet sheetPrecos = workbook.createSheet("Preços", 1);

            try {
                int i = 1;
                int z=1;
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex("Descricao"));
                        sheet.setColumnView(z,25);
                        sheetPrecos.setColumnView(i,25); //alterei a linha de baixo de 3 para 4
                        sheet.addCell(new Label(z+4, 0, title,getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17))); // Escrevemos os produtos nas colunas
                        sheetPrecos.addCell(new Label(i+3, 0, title + " - Meu preço",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,10)));
                        i++;
                        sheetPrecos.setColumnView(i,25);
                        sheetPrecos.addCell(new Label(i+3, 0, title + " - Encontrado",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,10)));
                        i++;
                        z++;
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }


            try {
                IdsUsados.add(999);
                int Linha = 0;
                if (prodto.moveToFirst()) do {
                    String NomeCliente = prodto.getString(prodto.getColumnIndex("NomeCliente"));
                    String IdCotacao = prodto.getString(prodto.getColumnIndex("IdCotacao"));
                    String Obs = prodto.getString(prodto.getColumnIndex("Obs"));
                    String DataCotacao = prodto.getString(prodto.getColumnIndex("DataCotacao"));
                    String TelefoneCliente = prodto.getString(prodto.getColumnIndex("TelefoneCliente"));
                    String Segmentacao = prodto.getString(prodto.getColumnIndex("Segmentacao"));
                    String Cidade = prodto.getString(prodto.getColumnIndex("Cidade"));
                    String title = prodto.getString(prodto.getColumnIndex("Nome"));
                    int QuantidadeCliente = prodto.getInt(prodto.getColumnIndex("QuantidadeC"));



                    for(int i=Index;i<IdsUsados.size();i++){ //Verificar se já não fiz desse Id
                        Index = i;
                        if(IdsUsados.contains(Integer.valueOf(IdCotacao))){
                          //  Toast.makeText(ctx, "Tem o id " + Integer.valueOf(IdCotacao), Toast.LENGTH_SHORT).show();
                            //Todo: Resolver esse loop aqui depois
                        }
                        else{
                            Linha++;
                            PegarItens(Integer.valueOf(IdCotacao),title,NomeCliente,datarelatorio);
                            IdsUsados.add(Integer.valueOf(IdCotacao));
                            int retira=1;
                            int retira2=1;
                            DecimalFormat formatar = new DecimalFormat("###");
                            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                            dfs.setDecimalSeparator(',');
                            formatar.setDecimalFormatSymbols(dfs);
                            for(int z=0;z<84;z++){
                                if(ProdutoRelatorio.getId() == Integer.valueOf(IdCotacao)){
                                    if(z<=41) {
                                        if (ProdutoRelatorio.getOpcoes().get(0).getOpcoes().get(z).equals("1")) {
                                            sheet.addCell(new Label(z + 5, Linha, "Sim", getCellFormat(Colour.GREEN, Colour.WHITE, 10)));
                                            TotalSim++;
                                        } else {
                                            TotalNao++;
                                            sheet.addCell(new Label(z + 5, Linha, "Não", getCellFormat(Colour.RED, Colour.WHITE, 10)));
                                        }
                                    }
                                    if(z % 2 == 0) {
                                        String pr1;
                                        if (z > 1) {
                                             pr1 = ProdutoRelatorio.getOpcoes().get(0).getPreco1().get(z - retira2);
                                            retira2++;
                                        }else{
                                         pr1 = ProdutoRelatorio.getOpcoes().get(0).getPreco1().get(z);
                                    }
                                        sheetPrecos.addCell(new Label(z + 5, Linha, pr1.replace(".",","), getCellFormat(Colour.GREEN, Colour.WHITE, 10)));
                                    }else {
                                        String pr2 = ProdutoRelatorio.getOpcoes().get(0).getPreco2().get(z-retira);
                                        retira++;
                                        sheetPrecos.addCell(new Label(z + 5, Linha, pr2.replace(".",","), getCellFormat(Colour.GREEN, Colour.WHITE, 10)));
                                    }
                                    sheet.setColumnView(4,35);
                                    //Cliente   Cliente - Cidade - telefone - data - segmentação
                                    sheet.addCell(new Label(0, 0, "Clientes:",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheetPrecos.addCell(new Label(0, 0, "Clientes:",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheet.addCell(new Label(0, Linha, ProdutoRelatorio.getCliente(),getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    sheetPrecos.addCell(new Label(0, Linha, ProdutoRelatorio.getCliente(),getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    //Desenhar Cidade
                                    sheet.addCell(new Label(1, 0, "Cidade",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheetPrecos.addCell(new Label(1, 0, "Cidade",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheet.addCell(new Label(1, Linha, Cidade,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    sheetPrecos.addCell(new Label(1, Linha, Cidade,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    //Telefone
                                    sheet.addCell(new Label(2, 0, "Telefone:",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheetPrecos.addCell(new Label(2, 0, "Telefone:",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheet.addCell(new Label(2, Linha, TelefoneCliente,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    sheetPrecos.addCell(new Label(2, Linha, TelefoneCliente,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    //Desenhar data
                                    sheet.addCell(new Label(3, 0, "Data",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheetPrecos.addCell(new Label(3, 0, "Data",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheet.addCell(new Label(3, Linha, DataCotacao,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    sheetPrecos.addCell(new Label(3, Linha, DataCotacao,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    //Desenhar Segmentacao
                                    sheet.addCell(new Label(4, 0, "Segmento",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheetPrecos.addCell(new Label(4, 0, "Segmentacao",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheet.addCell(new Label(4, Linha, Segmentacao,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    sheetPrecos.addCell(new Label(4, Linha, Segmentacao,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    //Desenhar Obs
                                    sheet.addCell(new Label(47, 0, "Observação",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheetPrecos.addCell(new Label(88, 0, "Observação",getCellFormat(Colour.DARK_BLUE,Colour.WHITE,17)));
                                    sheet.addCell(new Label(47, Linha, Obs,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    sheetPrecos.addCell(new Label(88, Linha, Obs,getCellFormat(Colour.BLUE_GREY,Colour.WHITE,10)));
                                    //Caso marque a opção
                                    if(exibirTotais){
                                        if(z<=27) {
                                    sheet.addCell(new Label(0, QuantidadeCliente+2, "Total clientes visitados: ",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    sheet.addCell(new Label(0, QuantidadeCliente+3, "Total clientes não atendido com os produtos: ",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    sheet.addCell(new Label(0, QuantidadeCliente+4, "% clientes sem os produtos: ",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    sheet.addCell(new Label(0, QuantidadeCliente+5, "Total clientes atendido com os produtos: ",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    sheet.addCell(new Label(0, QuantidadeCliente+6, "% clientes com os produtos: ",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
///////////////////////////////////////Desenho os resultados/////////////////////////////////////////////////////////////////
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+2, "" + QuantidadeCliente,getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+3, "" + ProdutoRelatorio.getOpcoes().get(0).getQuantidadesNao().get(z),getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    int teste = Integer.valueOf(ProdutoRelatorio.getOpcoes().get(0).getQuantidadesNao().get(z));
                                    int Calcular = Integer.valueOf(teste / QuantidadeCliente)*100;
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+4, String.valueOf(Calcular) + "%",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+5, "" + ProdutoRelatorio.getOpcoes().get(0).getQuantidadesSim().get(z),getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                    teste = Integer.valueOf(ProdutoRelatorio.getOpcoes().get(0).getQuantidadesSim().get(z));
                                    Calcular = Integer.valueOf(teste / QuantidadeCliente)*100;
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+6, String.valueOf(Calcular) + "%",getCellFormat(Colour.ICE_BLUE,Colour.WHITE,12)));
                                }}}

                            }
                        }






                    }






                } while (prodto.moveToNext());

                //closing cursor
                prodto.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PegarItens(int idMaster,String Descricao,String Cliente,String DataRelatorio){
        Cursor cursor = db.rawQuery("select Descricao, Id from produtos order by Descricao", null);
        //Query pra pegar  resto
        Cursor prodto = db.rawQuery("select p.*, (select c.Nome from cotacao c  where c.Id=p.IdCotacao) as Nome,  " +
                        "(select c.Id from cotacao c  where c.Id=p.IdCotacao )  as IdCotaMaster, " +
                        "coalesce((select count(*) as Qtd from produtoscotacao pr inner join cotacao cota on cota.Id=pr.IdCotacao " +
                        "where pr.Cotar = 0 and pr.Idproduto = p.Idproduto and cota.Data='" + DataRelatorio + "'), 0) as QtdNao, " +
                        "coalesce((select count(*) as Qtd from produtoscotacao pr inner join cotacao cota on cota.Id=pr.IdCotacao " +
                        "where pr.Cotar = 1 and pr.Idproduto = p.Idproduto and cota.Data='" + DataRelatorio + "'), 0) as QtdSim " +
                        "from produtoscotacao p where  p.IdCotacao in (select co.Id from cotacao co  where co.Data='" + DataRelatorio +
                        "')"
                ,null);
        try {
            Opc.clear();
            Pr1.clear();
            Pr2.clear();
            if (prodto.moveToFirst()) {
                do {
                    String SimNao = prodto.getString(prodto.getColumnIndex("Cotar"));
                    String Preco1 =prodto.getString(prodto.getColumnIndex("Preco1"));
                    String Preco2 =prodto.getString(prodto.getColumnIndex("Preco2"));
                    String IdCotacao = prodto.getString(prodto.getColumnIndex("IdCotacao"));
                    int qtdNao = prodto.getInt(prodto.getColumnIndex("QtdNao"));
                    int qtdSim = prodto.getInt(prodto.getColumnIndex("QtdSim"));

                    if(Integer.valueOf(IdCotacao)==idMaster){
                        Opc.add(SimNao);
                        QtdsNao.add(qtdNao);
                        QtdsSim.add(qtdSim);
                        Pr1.add(Preco1);
                        Pr2.add(Preco2);
                    }

                    int i = prodto.getPosition() + 1;

                } while (prodto.moveToNext());
            }
            //Aqui printa o relatorio

            MItensRelatorio item = new MItensRelatorio(idMaster,idMaster,Opc,QtdsSim,QtdsNao,Pr1,Pr2);
            Itens.clear();
            Itens.add(item);
          //  MCategoria m = new MCategoria(idMaster,Descricao,Itens,Cliente);
            ProdutoRelatorio = new MCategoria(idMaster,Descricao,Itens,Cliente);

            //closing cursor
            prodto.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////
    public int Contador(){
        //Query pra pegar  resto)
        int retorno = 0;
        Cursor prodtso = db.rawQuery("select count(*) as Qtd from produtoscotacao p where p.Cotar = 0"
                ,null);
        try {

            //sheet.addCell(label); // column and row
            //sheet.addCell(label2);
           // Opc.clear();
            // Qtds.clear();
            if (prodtso.moveToFirst()) {
                do {
                    int qtd = prodtso.getInt(prodtso.getColumnIndex("Qtd"));
                       // Qtds.add(qtd);
                    retorno = qtd;


                    int i = prodtso.getPosition() + 1;

                } while (prodtso.moveToNext());
            }

            //closing cursor
            prodtso.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return retorno;
    }
}
