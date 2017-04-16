package com.main.junaidstore.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.main.junaidstore.R;
import com.main.junaidstore.adapters.MainGridAdapter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.activity_main) RelativeLayout main_activity_view;
    @BindView(R.id.main_image_grid) GridView image_grid;
    @BindView(R.id.main_header_tags) LinearLayout main_header_tags;
    @BindView(R.id.main_header_date) Spinner main_header_date;
    @BindView(R.id.main_header_category) Spinner main_header_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //main_header_tags.setVisibility(View.GONE);
        image_grid.setAdapter(new MainGridAdapter(getApplicationContext()));
        createDrawer();
        //startActivity(new Intent(MainActivity.this,Categories.class));
    }


    private  void createDrawer(){

        ////if you want to update the items at a later time it is recommended to keep it in a variable
        SecondaryDrawerItem item1 = new SecondaryDrawerItem ().withIdentifier(1).withName(R.string.add_item).withSelectable(true);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.category).withSelectable(true);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cover)
                .addProfiles(
                        new ProfileDrawerItem().withName(getResources().getString(R.string.app_name)).withIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.app_cover))
                )
                .build();
        //Now create your drawer and pass the AccountHeader.Result
        new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(position == 3){
                            startActivity(new Intent(MainActivity.this,Categories.class));
                        }
                        else if(position == 1){
                            startActivity(new Intent(MainActivity.this,AddNewItem.class));
                        }
                        return false;
                    }
                })
        .build();
    }


}
