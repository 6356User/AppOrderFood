package com.example.apporderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apporderfood.DAO.LoaiMonAnDAO;
import com.example.apporderfood.DTO.LoaiMonAnDTO;
import com.example.apporderfood.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;

public class AdapterHienThiLoaiMonAnThucDon extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    LoaiMonAnDAO loaiMonAnDAO;
    ViewHolderHienThiLoaiThucDon viewHolderHienThiLoaiThucDon;

    public AdapterHienThiLoaiMonAnThucDon(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context=context;
        this.layout=layout;
        this.loaiMonAnDTOList=loaiMonAnDTOList;
        loaiMonAnDAO = new LoaiMonAnDAO(context);
    }

    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiMonAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return loaiMonAnDTOList.get(i).getMaLoai();
    }

    public class ViewHolderHienThiLoaiThucDon{
        ImageView imgHinhLoaiThucDon;
        TextView txtTenLoaiThucDon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if(view1==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiLoaiThucDon = new ViewHolderHienThiLoaiThucDon();
            view1 = inflater.inflate(this.layout, viewGroup, false);

            viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon = view1.findViewById(R.id.imgHienThiMonAn);
            viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon = view1.findViewById(R.id.txtTenLoaiThucDon);

            view1.setTag(viewHolderHienThiLoaiThucDon);
        }else {
            viewHolderHienThiLoaiThucDon = (ViewHolderHienThiLoaiThucDon) view1.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);
        int maloai = loaiMonAnDTO.getMaLoai();
        String hinhanh = loaiMonAnDAO.LayHinhLoaiMonAn(maloai);

        Uri uri = Uri.parse(hinhanh);
        viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon.setText(loaiMonAnDTO.getTenLoai());
        //viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon.setImageURI(uri);
        Picasso.get().load(uri).into(viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon);

        return view1;
    }
}
