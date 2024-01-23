package com.example.apporderfood.FragmentApp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.apporderfood.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, iNgay, iThang, iNam);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        EditText edNgaySinh = getActivity().findViewById(R.id.edtNgaySinhDK);
        String sNgaySinh = i2 + "/" + (i1+1) + "/" + i;
        edNgaySinh.setText(sNgaySinh);
    }
}
