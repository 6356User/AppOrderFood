package com.example.apporderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apporderfood.DTO.LoaiMonAnDTO;
import com.example.apporderfood.DTO.MonAnDTO;
import com.example.apporderfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter {

     Context context;
     int layout;
     List<MonAnDTO> monAnDTOS;
    ViewHolderHienThiDanhSachMonAn viewholder;

    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> monAnDTOS){
        this.context = context;
        this.layout = layout;
        this.monAnDTOS = monAnDTOS;
    }

    @Override
    public int getCount() {
        return monAnDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return monAnDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return monAnDTOS.get(i).getMaMonAn();
    }

    public class ViewHolderHienThiDanhSachMonAn{
        ImageView imHinhMonAn;
        TextView txtTenMonAn, txtGiaTien;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if(view1 == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewholder = new ViewHolderHienThiDanhSachMonAn();
            view1 = layoutInflater.inflate(this.layout, viewGroup, false);

            viewholder.imHinhMonAn = view1.findViewById(R.id.imHienThiDSMonAn);
            viewholder.txtTenMonAn = view1.findViewById(R.id.txtTenDSMonAn);
            viewholder.txtGiaTien = view1.findViewById(R.id.txtGiaTienDSMonAn);

            view1.setTag(viewholder);
        }else{
            viewholder = (ViewHolderHienThiDanhSachMonAn) view1.getTag();
        }

        MonAnDTO monAnDTO = monAnDTOS.get(i);
        String hinhanh = monAnDTO.getHinhAnh().toString();
        if(hinhanh == null || hinhanh.equals("")){
            Picasso.get().load(Uri.parse(String.valueOf(R.drawable.backgroundheader1))).into(viewholder.imHinhMonAn);
        }else{
            Uri uri = Uri.parse(hinhanh);
            Picasso.get().load(uri).into(viewholder.imHinhMonAn);
        }

        viewholder.txtTenMonAn.setText(monAnDTO.getTenMonAn());
        viewholder.txtGiaTien.setText(context.getResources().getString(R.string.mucgia) + monAnDTO.getGiaTien());

        return view1;
    }
}
