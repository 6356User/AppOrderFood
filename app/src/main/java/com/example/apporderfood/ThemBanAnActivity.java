package com.example.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText edtThemTenBanAn;
    Button btnDongYThemBanAn;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);
        setTitle(R.string.thembanan);

        edtThemTenBanAn = findViewById(R.id.edtThemTenBanAn);
        btnDongYThemBanAn = findViewById(R.id.btnDongYThemBanAn);

        banAnDAO = new BanAnDAO(ThemBanAnActivity.this);
        btnDongYThemBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sTenBanAn = edtThemTenBanAn.getText().toString();
        if(sTenBanAn.trim() !=null && !sTenBanAn.trim().equals("")){
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("kiemtraketquathem", kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
