package com.example.apporderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apporderfood.DAO.BanAnDAO;
import com.example.apporderfood.DAO.GoiMonDAO;
import com.example.apporderfood.DTO.BanAnDTO;
import com.example.apporderfood.DTO.GoiMonDTO;
import com.example.apporderfood.FragmentApp.HienThiThucDonFragment;
import com.example.apporderfood.R;
import com.example.apporderfood.ThanhToanActivity;
import com.example.apporderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {

    Context context;
    int layout;
    List<BanAnDTO> banAnDTOS;
    ViewHolderBanAn viewHolderBanAn;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOS){
        this.context = context;
        this.layout = layout;
        this.banAnDTOS = banAnDTOS;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return banAnDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return banAnDTOS.get(i).getMABAN();
    }

    public class ViewHolderBanAn{
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnButton;
        TextView txtTenBanAn;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if(view1 == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view1 = inflater.inflate(this.layout, viewGroup, false);
            viewHolderBanAn.imgBanAn = view1.findViewById(R.id.imgBanAn);
            viewHolderBanAn.imgGoiMon = view1.findViewById(R.id.imgGoiMon);
            viewHolderBanAn.imgThanhToan = view1.findViewById(R.id.imgThanhToan);
            viewHolderBanAn.imgAnButton = view1.findViewById(R.id.imgAnButton);
            viewHolderBanAn.txtTenBanAn = view1.findViewById(R.id.txtTenBanAn);

            view1.setTag(viewHolderBanAn);
        }else{
            viewHolderBanAn = (ViewHolderBanAn) view1.getTag();
        }

        if(banAnDTOS.get(i).isDuocChon()){
            HienThiButton();
        }else{
            AnButton();
        }

        BanAnDTO banAnDTO = banAnDTOS.get(i);

        KiemTraTinhTrangBan(banAnDTO);
        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTENBAN());
        viewHolderBanAn.imgBanAn.setTag(i);

        viewHolderBanAn.imgBanAn.setOnClickListener(this);
        viewHolderBanAn.imgGoiMon.setOnClickListener(this);
        viewHolderBanAn.imgThanhToan.setOnClickListener(this);
        viewHolderBanAn.imgAnButton.setOnClickListener(this);

        notifyDataSetChanged();
        return view1;
    }

    private void KiemTraTinhTrangBan(BanAnDTO baDTO){
        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(baDTO.getMABAN());
        if(kttinhtrang.equals("true")){
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        }else{
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }
    }


    private void HienThiButton(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)view.getParent()).getTag();
        int vitri1 = (int) viewHolderBanAn.imgBanAn.getTag();
        int maban = banAnDTOS.get(vitri1).getMABAN();

        if(id == R.id.imgBanAn){
//            int vitri = (int) view.getTag();
            if(!banAnDTOS.get(vitri1).isDuocChon()){
                HienThiButton();
                banAnDTOS.get(vitri1).setDuocChon(true);
            }
        }else if(id == R.id.imgGoiMon){
            Intent layITrangChu = ((TrangChuActivity)context).getIntent();
            int manhanvien = layITrangChu.getIntExtra("manv", 0);

            String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
            if(tinhtrang.equals("false")){
                //code them bang goi mon va cap nhat lai tinh trang ban
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                String ngaygoi = dateFormat.format(calendar.getTime());

                GoiMonDTO goiMonDTO = new GoiMonDTO();
                goiMonDTO.setMaBan(maban);
                goiMonDTO.setMaNV(manhanvien);
                goiMonDTO.setNgayGoi(ngaygoi);
                goiMonDTO.setTinhTrang("false");

                long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                banAnDAO.CapNhatLaiTinhTrangBan(maban, "true");
                if(kiemtra == 0) Toast.makeText(context,context.getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
            }
            //KiemTraTinhTrangBan(banAnDTOS.get(vitri1));

            FragmentTransaction tranThucDonTransaction = fragmentManager.beginTransaction();
            HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();

            Bundle bDuLieuThucDon = new Bundle();
            bDuLieuThucDon.putInt("maban",maban);

            hienThiThucDonFragment.setArguments(bDuLieuThucDon);

            tranThucDonTransaction.replace(R.id.content,hienThiThucDonFragment).addToBackStack("hienthibanan");
            tranThucDonTransaction.commit();
        }else if(id == R.id.imgThanhToan){
            Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
            iThanhToan.putExtra("maban",maban);
            context.startActivity(iThanhToan);
        }else if(id == R.id.imgAnButton){
            AnButton();
            banAnDTOS.get(vitri1).setDuocChon(false);
        }

    }
}
