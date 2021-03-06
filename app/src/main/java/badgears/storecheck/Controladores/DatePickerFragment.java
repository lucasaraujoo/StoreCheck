package badgears.storecheck.Controladores;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import badgears.storecheck.R;

/**
 * Created by lucas on 18/10/16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        EditText edDataRelatorio = (EditText) getActivity().findViewById(R.id.etDataRelatorio);
        String mes = String.valueOf(view.getMonth() + 1);
        if(view.getMonth()<9){
            mes= 0 + mes;
        }
        else{
            mes = mes;
        }
        edDataRelatorio.setText(view.getDayOfMonth() + "/" + mes + "/" + view.getYear());
        //Toast.makeText(getActivity().getApplicationContext(), "DAta: " + view.getDayOfMonth() + "/" + view.getMonth() + "/" + view.getYear(), Toast.LENGTH_SHORT).show();
    }


}