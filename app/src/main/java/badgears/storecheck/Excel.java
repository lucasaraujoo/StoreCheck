package badgears.storecheck;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import badgears.storecheck.Dao.DaoMain;
import badgears.storecheck.Modelos.MCategoria;
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
    public ArrayList<MCategoria> Categorias = new ArrayList<MCategoria>();
    public ArrayList<String> Opc = new ArrayList<String>();
    private ArrayList<MItensRelatorio> Itens = new ArrayList<MItensRelatorio>();
    public int Linha;
    public  String IdTeste = "";

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
                "(select c.Id from cotacao c  where c.Id=p.IdCotacao )  as IdCotaMaster " +
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
            WritableSheet sheet = workbook.createSheet("MyShoppingList", 0);
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

               // sheet.addCell(label); // column and row
                //sheet.addCell(label2);
                if (prodto.moveToFirst()) {
                    do {
                        String title = prodto.getString(prodto.getColumnIndex("Nome"));
                        String SimNao = prodto.getString(prodto.getColumnIndex("Cotar"));
                        String IdCotacao = prodto.getString(prodto.getColumnIndex("IdCotacao"));
                        String IDCotacaoItens = prodto.getString(prodto.getColumnIndex("IdCotaMaster"));

if(IdCotacao.intern() != IdTeste.intern()) { //Verifica se não já printei essa cotacao
    IdTeste= IdCotacao;
    int i = prodto.getPosition() + 1;
    int ii = prodto.getPosition();
    MItensRelatorio ItensConfiguar;
    for (int z = 0; z <= 27; z++) { // Pego as opções

        Opc.add(SimNao);
    }
    ItensConfiguar = new MItensRelatorio(i,Integer.valueOf(IdCotacao),Opc);
    Itens.add(ItensConfiguar);
    MCategoria m = new MCategoria(i, title, Itens, title); // Deixo o cliente vazio por enquanto
    Categorias.add(m);
    ArrayList<MItensRelatorio> o = new ArrayList<MItensRelatorio>();

    //o.add(Categorias.get(i).getOpcoes());
    // Toast.makeText(ctx, "Teste " + o.get(i-1), Toast.LENGTH_SHORT).show();

    // Toast.makeText(ctx, "Posição vai ser: " + Categorias.get(x).getId() + " para " + Categorias.get(x).getDescricao(), Toast.LENGTH_SHORT).show();
    int Index = 0;
    for (int x = 0; x < Opc.size(); x++) {

        if(IdCotacao.intern() != IdTeste.intern()) {
            Index++;
        }
            if (Categorias.get(Index).getOpcoes().get(Index).getOpcoes().get(x) == "1") {        //o.get(i - 1)
            sheet.addCell(new Label(x + 1, i, "Sim", getCellFormat(Colour.GREEN)));
        } else {
            sheet.addCell(new Label(x + 1, i, "Não", getCellFormat(Colour.RED)));
        }
        Log.i("Relatorio", "Id do cliente: " + title + " Id master cotacao: " + IDCotacaoItens + " Id cotacaodet "
                + IdCotacao + " Saindo: " + Categorias.get(Index).getOpcoes().get(Index).getOpcoes().get(x) +
        "Index usado: " + x);
        sheet.addCell(new Label(0, i, m.getCliente()));
    }
    o.clear();


}
                       // sheet.addCell(new Label(1, i, desc));
                    } while (prodto.moveToNext());
                }
                //closing cursor
                prodto.close();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
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
}
