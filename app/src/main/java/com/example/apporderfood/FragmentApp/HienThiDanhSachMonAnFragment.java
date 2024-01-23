package com.example.apporderfood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.apporderfood.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.example.apporderfood.DAO.MonAnDAO;
import com.example.apporderfood.DTO.MonAnDTO;
import com.example.apporderfood.R;
import com.example.apporderfood.SoLuongActivity;
import com.example.apporderfood.ThemThucDonActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {

    GridView gridView;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOS;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maloai;
    int maban;

    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);

        gridView = view.findViewById(R.id.gvHienThiThucDon);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        monAnDAO = new MonAnDAO(getActivity());

        Bundle bundle = getArguments();
        if(bundle != null) {
            maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");

            HienThiDanhSachMonAn();

            if (maquyen==1){
                registerForContextMenu(gridView);
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(maban !=0 ){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan",monAnDTOS.get(i).getMaMonAn());

                        startActivity(iSoLuong);
                    }


                }
            });
          }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloaithucdon", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item .getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int postition = menuInfo.position;
        int mamonan = monAnDTOS.get(postition).getMaMonAn();

        if(id == R.id.itemSua){
            Intent intent = new Intent(getActivity(), ThemThucDonActivity.class);
            intent.putExtra("mamonan", mamonan);
            startActivity(intent);

        }else if(id == R.id.itemXoa){

            boolean kiemtra = monAnDAO.XoaMonAn(mamonan);
            if(kiemtra){
                HienThiDanhSachMonAn();
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
            }else{ Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show(); }
        }

        return super.onContextItemSelected(item);
    }

    private void HienThiDanhSachMonAn() {
        monAnDTOS = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);

        adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(), R.layout.custom_layout_hienthidanhsachmonan, monAnDTOS);
        gridView.setAdapter(adapterHienThiDanhSachMonAn);
        adapterHienThiDanhSachMonAn.notifyDataSetChanged();
    }
}
