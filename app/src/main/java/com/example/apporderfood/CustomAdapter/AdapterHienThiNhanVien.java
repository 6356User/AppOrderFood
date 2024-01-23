package com.example.apporderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apporderfood.DTO.NhanVienDTO;
import com.example.apporderfood.R;

import java.util.List;

public class AdapterHienThiNhanVien extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVienDTO> nhanVienDTOList;
    ViewHolderNhanVien viewHolderNhanVien;

    public AdapterHienThiNhanVien(Context context, int layout, List<NhanVienDTO> nhanVienDTOList){
        this.context = context;
        this.layout = layout;
        this.nhanVienDTOList = nhanVienDTOList;
    }

    @Override
    public int getCount() {
        return nhanVienDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return nhanVienDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return nhanVienDTOList.get(i).getMANV();
    }

    public class ViewHolderNhanVien{
        ImageView imHinhNhanVien;
        TextView txtTenNhanVien, txtCCCD;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1 = view;

        if(view1 == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1 = inflater.inflate(this.layout, viewGroup,false);

            viewHolderNhanVien.imHinhNhanVien = view1.findViewById(R.id.imHinhNhanVien);
            viewHolderNhanVien.txtTenNhanVien = view1.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtCCCD = view1.findViewById(R.id.txtCCCD);

            view1.setTag(viewHolderNhanVien);
        }else{
            viewHolderNhanVien = (ViewHolderNhanVien) view1.getTag();
        }

        NhanVienDTO nhanVienDTO = nhanVienDTOList.get(i);

        viewHolderNhanVien.txtTenNhanVien.setText(nhanVienDTO.getTENDN());
        viewHolderNhanVien.txtCCCD.setText(String.valueOf(nhanVienDTO.getCCCD()));

        return view1;
    }
}
