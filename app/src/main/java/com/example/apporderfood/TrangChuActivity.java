package com.example.apporderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apporderfood.FragmentApp.HienThiBanAnFragment;
import com.example.apporderfood.FragmentApp.HienThiNhanVienFragment;
import com.example.apporderfood.FragmentApp.HienThiThucDonFragment;
import com.google.android.material.navigation.NavigationView;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTenNhanVienNavigation;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        /*Anh xa View*/
        InitialView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.mo, R.string.dong ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        txtTenNhanVienNavigation.setText(tendn);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        fragmentTransaction.replace(R.id.content, hienThiBanAnFragment);
        fragmentTransaction.commit();
    }

    private void InitialView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView_trangchu);
        toolbar = findViewById(R.id.toolbar);

        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        txtTenNhanVienNavigation = view.findViewById(R.id.txtTenNhanVienNavigation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.itTrangChu){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
            fragmentTransaction.replace(R.id.content, hienThiBanAnFragment);
            fragmentTransaction.commit();

            item.setChecked(true);
            drawerLayout.closeDrawers();
        }else if(id == R.id.itThucDon){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
            fragmentTransaction.replace(R.id.content, hienThiThucDonFragment);
            fragmentTransaction.commit();

            item.setChecked(true);
            drawerLayout.closeDrawers();
        } else if (id == R.id.itNhanVien) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
            fragmentTransaction.replace(R.id.content, hienThiNhanVienFragment);
            fragmentTransaction.commit();

            item.setChecked(true);
            drawerLayout.closeDrawers();
        }
        return false;
    }
}
