package com.example.apporderfood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apporderfood.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.apporderfood.DAO.LoaiMonAnDAO;
import com.example.apporderfood.DTO.LoaiMonAnDTO;
import com.example.apporderfood.R;
import com.example.apporderfood.ThemThucDonActivity;
import com.example.apporderfood.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {

    GridView gvHienThiThucDon;
    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    int maban;

    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        setHasOptionsMenu(true);
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        fragmentManager = getActivity().getSupportFragmentManager();

        gvHienThiThucDon = view.findViewById(R.id.gvHienThiThucDon);
        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        HienThiDanhSachThucDon();

        Bundle bundle = getArguments();
        if(bundle != null){
            maban = bundle.getInt("maban");
        }

        gvHienThiThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int maloai = loaiMonAnDTOList.get(i).getMaLoai();

                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai", maloai);
                bundle.putInt("maban", maban);
                hienThiDanhSachMonAnFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthiloaithucdon");

                transaction.commit();
            }
        });

        return view;
    }

    private void HienThiDanhSachThucDon(){
        loaiMonAnDTOList = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAnThucDon = new AdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hienloaimonan, loaiMonAnDTOList);
        gvHienThiThucDon.setAdapter(adapterHienThiLoaiMonAnThucDon);
        adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        HienThiDanhSachThucDon();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen == 1) {
            MenuItem itemThemBanAn = menu.add(1, R.id.itemThemThucDon, 1, R.string.themthucdon);
            itemThemBanAn.setIcon(R.drawable.logodangnhap);
            itemThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemThemThucDon){
            Intent intentThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
            startActivity(intentThemThucDon);
        }

        return super.onOptionsItemSelected(item);
    }
}
