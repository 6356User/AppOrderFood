package com.example.apporderfood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apporderfood.CustomAdapter.AdapterHienThiNhanVien;
import com.example.apporderfood.DAO.NhanVienDAO;
import com.example.apporderfood.DTO.NhanVienDTO;
import com.example.apporderfood.DangKyActivity;
import com.example.apporderfood.R;
import com.example.apporderfood.TrangChuActivity;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {

    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOS;
    ListView listViewNhanVien;

    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        listViewNhanVien = view.findViewById(R.id.listViewNhanVien);
        nhanVienDAO = new NhanVienDAO(getActivity());

        HienThiDanhSachNhanVien();

        if (maquyen == 1){
            registerForContextMenu(listViewNhanVien);
        }

        return view;
    }

    private void HienThiDanhSachNhanVien() {
        nhanVienDTOS = nhanVienDAO.LayDanhSachNhanVien();

        AdapterHienThiNhanVien adapterHienThiNhanVien = new AdapterHienThiNhanVien(getActivity(), R.layout.custom_layout_hienthinhanvien, nhanVienDTOS);
        listViewNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen==1){
            MenuItem itemThemNhanVien = menu.add(1, R.id.itemThemNhanVien, 1, R.string.themnhanvien);
            itemThemNhanVien.setIcon(R.drawable.nhanvien);
            itemThemNhanVien.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.itemThemNhanVien){
            Intent intent = new Intent(getActivity(),  DangKyActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manhanvien = nhanVienDTOS.get(vitri).getMANV();

        if(id == R.id.itemSua){

            Intent iDangKy = new Intent(getActivity(),DangKyActivity.class);
            iDangKy.putExtra("manv",manhanvien);
            startActivity(iDangKy);
        } else if (id == R.id.itemXoa) {

            boolean kiemtra = nhanVienDAO.XoaNhanVien(manhanvien);
            if (kiemtra){
                HienThiDanhSachNhanVien();
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
            }else{ Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show(); }
        }

        return super.onContextItemSelected(item);
    }
}
