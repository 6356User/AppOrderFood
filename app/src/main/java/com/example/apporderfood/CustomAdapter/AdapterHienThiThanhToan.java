package com.example.apporderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apporderfood.DTO.ThanhToanDTO;
import com.example.apporderfood.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ThanhToanDTO> thanhToanDTOList;
    ViewHolderThanhToan viewHolderThanhToan;

    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOList) {
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOList = thanhToanDTOList;
    }

    @Override
    public int getCount() {
        return thanhToanDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return thanhToanDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolderThanhToan{
        TextView txtTenMonAnThanhToan, txtSoLuongThanhToan, txtGiaTienThanhToan;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;

        if(view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view1 = inflater.inflate(this.layout, viewGroup, false);

            viewHolderThanhToan.txtTenMonAnThanhToan = view1.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolderThanhToan.txtSoLuongThanhToan = view1.findViewById(R.id.txtSoLuongThanhToan);
            viewHolderThanhToan.txtGiaTienThanhToan = view1.findViewById(R.id.txtGiaTienThanhToan);

            view1.setTag(viewHolderThanhToan);

        }else{ viewHolderThanhToan = (ViewHolderThanhToan) view1.getTag(); }

        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(i);
        viewHolderThanhToan.txtTenMonAnThanhToan.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuongThanhToan.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTienThanhToan.setText(String.valueOf(thanhToanDTO.getGiaTien()));

        return view1;
    }
}
