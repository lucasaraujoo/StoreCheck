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
    private static WritableCellFormat getCellFormat(Colour colour) throws WriteException {
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 16);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setBackground(colour);
        return cellFormat;
    }

    public void exportToExcel() throws IOException, WriteException{
        Cursor cursor = db.rawQuery("select Descricao, Id from produtos order by Descricao", null);
        //Query pra pegar  resto
        Cursor prodto = db.rawQuery("select p.*, (select c.Nome from cotacao c  where c.Id=p.IdCotacao) as Nome,  " +
                "(select c.Id from cotacao c  where c.Id=p.IdCotacao )  as IdCotaMaster, " +
                "(select count(*) from cotacao cc) as QuantidadeC " +
                "from produtoscotacao p order by (select Descricao from produtos pp where pp.Id=p.Idproduto )"
                ,null);

        /*
         Cursor prodto = db.rawQuery("select * from produtoscotacao p INNER JOIN cotacao c on " +
                " c.Id = p.IdCotacao  where c.Nome='Testar' order by p.Idproduto",null);
         */
        final String fileName = "TodoList.xls";


        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath());


        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;
        //Label label_name = new Label(0, 0, "value", getCellFormat(Colour.GREEN, Pattern.GRAY_25));
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("StoreCheck MOBILE V1.0", 0);
         //   Label label = new Label(0, 0, "Descrição", getCellFormat(Colour.GREEN, Pattern.GRAY_25));
           // Label label2 = new Label(1, 0, "Categoria", getCellFormat(Colour.BLUE, Pattern.GRAY_50));


            try {

                //sheet.addCell(label); // column and row
                //sheet.addCell(label2);
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex("Descricao"));
                //        String desc = cursor.getString(cursor.getColumnIndex("Categoria"));

                        int i = cursor.getPosition() + 1;
                       // Opc.add("Sim");

                        sheet.addCell(new Label(i, 0, title,getCellFormat(Colour.AQUA))); // Escrevemos as categorias nas colunas
                       // sheet.addCell(new Label(1, i, desc));
                    } while (cursor.moveToNext());
                }
                //closing cursor
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
                    String SimNao = prodto.getString(prodto.getColumnIndex("Cotar"));
                    String IdCotacao = prodto.getString(prodto.getColumnIndex("IdCotacao"));
                    String IDCotacaoItens = prodto.getString(prodto.getColumnIndex("IdCotaMaster"));
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
                            PegarItens(Integer.valueOf(IdCotacao),title,title);
                            IdsUsados.add(Integer.valueOf(IdCotacao));

                            for(int z=0;z<ProdutoRelatorio.getOpcoes().get(0).getOpcoes().size();z++){
                                if(ProdutoRelatorio.getId() == Integer.valueOf(IdCotacao)){
                                    if(ProdutoRelatorio.getOpcoes().get(0).getOpcoes().get(z).equals("1")) {
                                        sheet.addCell(new Label(z + 1, Linha, ProdutoRelatorio.getOpcoes().get(0).getOpcoes().get(z), getCellFormat(Colour.GREEN)));
                                        TotalSim++;
                                    }
                                    else{
                                        TotalNao++;
                                        sheet.addCell(new Label(z + 1, Linha, ProdutoRelatorio.getOpcoes().get(0).getOpcoes().get(z), getCellFormat(Colour.RED)));
                                    }
                                    sheet.addCell(new Label(0, Linha, ProdutoRelatorio.getCliente()));
                                    sheet.addCell(new Label(0, QuantidadeCliente+2, "Total clientes visitados: "));
                                    sheet.addCell(new Label(0, QuantidadeCliente+3, "Total clientes não atendido com os produtos: "));
                                    sheet.addCell(new Label(0, QuantidadeCliente+4, "% clientes sem os produtos: "));
                                    sheet.addCell(new Label(0, QuantidadeCliente+5, "Total clientes atendido com os produtos: "));
                                    sheet.addCell(new Label(0, QuantidadeCliente+6, "% clientes com os produtos: "));
///////////////////////////////////////Desenho os resultados/////////////////////////////////////////////////////////////////
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+2, "" + QuantidadeCliente));
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+3, "" + ProdutoRelatorio.getOpcoes().get(0).getQuantidadesNao().get(z)));
                                    DecimalFormat formatar = new DecimalFormat("#.##");
                                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                    dfs.setDecimalSeparator('.');
                                    formatar.setDecimalFormatSymbols(dfs);
                                    double teste = Double.valueOf(ProdutoRelatorio.getOpcoes().get(0).getQuantidadesNao().get(z));
                                    double Calcular = Double.valueOf(formatar.format(teste / QuantidadeCliente));
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+4, String.valueOf(Calcular) + "%"));
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+5, "" + ProdutoRelatorio.getOpcoes().get(0).getQuantidadesSim().get(z)));
                                    teste = Double.valueOf(ProdutoRelatorio.getOpcoes().get(0).getQuantidadesSim().get(z));
                                    Calcular = Double.valueOf(formatar.format(teste / QuantidadeCliente));
                                    sheet.addCell(new Label(z+1, QuantidadeCliente+6, String.valueOf(Calcular) + "%"));
                                }

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

    public void PegarItens(int idMaster,String Descricao,String Cliente){
        Cursor cursor = db.rawQuery("select Descricao, Id from produtos order by Descricao", null);
        //Query pra pegar  resto
        Cursor prodto = db.rawQuery("select p.*, (select c.Nome from cotacao c  where c.Id=p.IdCotacao) as Nome,  " +
                        "(select c.Id from cotacao c  where c.Id=p.IdCotacao )  as IdCotaMaster, " +
                        "coalesce((select count(*) as Qtd from produtoscotacao pr where pr.Cotar = 0 and pr.Idproduto = p.Idproduto), 0) as QtdNao, " +
                        "coalesce((select count(*) as Qtd from produtoscotacao pr where pr.Cotar = 1 and pr.Idproduto = p.Idproduto), 0) as QtdSim " +
                        "from produtoscotacao p order by (select Descricao from produtos pp where pp.Id=p.Idproduto )"
                ,null);
        try {
            Opc.clear();
            if (prodto.moveToFirst()) {
                do {
                    String SimNao = prodto.getString(prodto.getColumnIndex("Cotar"));
                    String IdCotacao = prodto.getString(prodto.getColumnIndex("IdCotacao"));
                    int qtdNao = prodto.getInt(prodto.getColumnIndex("QtdNao"));
                    int qtdSim = prodto.getInt(prodto.getColumnIndex("QtdSim"));

                    if(Integer.valueOf(IdCotacao)==idMaster){
                        Opc.add(SimNao);
                        QtdsNao.add(qtdNao);
                        QtdsSim.add(qtdSim);
                    }

                    int i = prodto.getPosition() + 1;

                } while (prodto.moveToNext());
            }
            //Aqui printa o relatorio

            MItensRelatorio item = new MItensRelatorio(idMaster,idMaster,Opc,QtdsSim,QtdsNao);
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
