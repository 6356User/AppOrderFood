package com.example.apporderfood.FragmentApp;

import android.app.Activity;
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
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apporderfood.CustomAdapter.AdapterHienThiBanAn;
import com.example.apporderfood.DAO.BanAnDAO;
import com.example.apporderfood.DTO.BanAnDTO;
import com.example.apporderfood.R;
import com.example.apporderfood.SuaBanAnActivity;
import com.example.apporderfood.ThemBanAnActivity;
import com.example.apporderfood.TrangChuActivity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {

    public static int REQUEST_CODE_THEM = 111;
    public static int RESQUEST_CODE_SUA = 16;
    GridView gvHienThiBanAn;
    List<BanAnDTO> banAnDTOList;
    BanAnDAO banAnDAO;
    AdapterHienThiBanAn adapterHienThiBanAn;

    int maquyen = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.banan);

        gvHienThiBanAn = view.findViewById(R.id.gvHienThiBanAn);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        banAnDAO = new BanAnDAO(getActivity());

        HienThiDanhSachBanAn();
        if (maquyen==1) {
            registerForContextMenu(gvHienThiBanAn);
        }

        return view; 
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = adapterContextMenuInfo.position;
        int maban = banAnDTOList.get(vitri).getMABAN();

        if(id == R.id.itemSua){

            Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
            intent.putExtra("maban",maban);
            startActivityForResult(intent,RESQUEST_CODE_SUA);

        }else if(id == R.id.itemXoa){
            boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
            if(kiemtra){
                HienThiDanhSachBanAn();
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban,Toast.LENGTH_SHORT).show();
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen ==1){
            MenuItem itemThemBanAn = menu.add(1, R.id.itemThemBanAn, 1, R.string.thembanan);
            itemThemBanAn.setIcon(R.drawable.thembanan);
            itemThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itemThemBanAn){
            Intent intentThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
            startActivityForResult(intentThemBanAn, REQUEST_CODE_THEM);
        }

        return true;
    }

    private void HienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.LayDanhSachBanAn();
        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(), R.layout.custom_layout_hienthibanan, banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_THEM ){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtraketquathem", false);
                if (kiemtra){
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                    HienThiDanhSachBanAn();
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == RESQUEST_CODE_SUA){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtra",false);
                HienThiDanhSachBanAn();
                if(kiemtra){
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
