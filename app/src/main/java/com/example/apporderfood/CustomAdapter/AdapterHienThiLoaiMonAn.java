package com.example.apporderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apporderfood.DTO.LoaiMonAnDTO;
import com.example.apporderfood.R;

import java.util.List;

public class AdapterHienThiLoaiMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOS){
        this.context=context;
        this.layout=layout;
        this.loaiMonAnDTOS=loaiMonAnDTOS;
    }

    @Override
    public int getCount() {
        return loaiMonAnDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiMonAnDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return loaiMonAnDTOS.get(i).getMaLoai();
    }

    public class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view1 = convertView;
        if(view1 == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            view1 = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent, false);
            viewHolderLoaiMonAn.txtTenLoai = view1.findViewById(R.id.txtTenLoai);

            view1.setTag(viewHolderLoaiMonAn);
        }else{
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view1.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOS.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if(view1 == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1 = inflater.inflate(R.layout.custom_layout_spinloaithucdon, viewGroup, false);

            viewHolderLoaiMonAn.txtTenLoai = view1.findViewById(R.id.txtTenLoai);

            view1.setTag(viewHolderLoaiMonAn);
        }else{
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view1.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOS.get(i);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view1;
    }

    public int GetPostitionOfMaLoai(int maloai){
        int postition = -1;
        for (int i=0; i<loaiMonAnDTOS.size(); i++){
            if (loaiMonAnDTOS.get(i).getMaLoai() == maloai){
                postition = i;
            }
        }
        return postition;
    }
}
