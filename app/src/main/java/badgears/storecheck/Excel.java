package badgears.storecheck;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import badgears.storecheck.Dao.DaoMain;
import badgears.storecheck.Modelos.MCategoria;
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
    public Excel(Context contexto) {
        super(contexto);
        ctx = contexto;
    }
    private static WritableCellFormat getCellFormat(Colour colour) throws WriteException {
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 16);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
       // cellFormat.setBackground(colour, pattern);
        return cellFormat;
    }
    public void exportToExcel() throws IOException, WriteException{
        Cursor cursor = db.rawQuery("select Descricao, Id, Categoria from produtos", null);
        Cursor prodto = db.rawQuery("select * from produtos order by Categoria",null);
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
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String title = cursor.getString(cursor.getColumnIndex("Categoria"));
                            //        String desc = cursor.getString(cursor.getColumnIndex("Categoria"));

                            int i = cursor.getPosition() + 1;
                            MCategoria m = new MCategoria(i, title);
                            Categorias.add(m);
                            sheet.addCell(new Label(i, 0, title, getCellFormat(Colour.GREEN))); // Escrevemos as categorias nas colunas
                            // sheet.addCell(new Label(1, i, desc));
                        } while (cursor.moveToNext());
                    }
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
                        String title = prodto.getString(prodto.getColumnIndex("Descricao"));
                        String desc = prodto.getString(prodto.getColumnIndex("Categoria"));

                        int i = prodto.getPosition() + 1;
                        int ii = 0;
                        Toast.makeText(ctx, "i " + i, Toast.LENGTH_SHORT).show();
                        for(int x=0;x<Categorias.size();x++){
                            if(desc.equals(Categorias.get(x).getDescricao())){
                                ii++;
                               // Toast.makeText(ctx, "Posição vai ser: " + Categorias.get(x).getId() + " para " + Categorias.get(x).getDescricao(), Toast.LENGTH_SHORT).show();
                                sheet.addCell(new Label(Categorias.get(x).getId(), i, title));
                            }
                            else{

                            }
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
