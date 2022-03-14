import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.expensemanagement.R;

import java.util.Calendar;

public class newtrip {


    public static class DatePicker extends DialogFragment
    implements DatePickerDialog.OnDateSetListener{


        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

        }
    }
}
