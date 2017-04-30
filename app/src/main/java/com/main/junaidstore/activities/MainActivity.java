package com.main.junaidstore.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.main.junaidstore.R;
import com.main.junaidstore.adapters.CategoriesAdapter;
import com.main.junaidstore.adapters.MainGridAdapter;
import com.main.junaidstore.interfaces.AsyncCallback;
import com.main.junaidstore.libraries.GeneralFunctions;
import com.main.junaidstore.libraries.NetworkInterface;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements AsyncCallback {

    @BindView(R.id.activity_main) RelativeLayout main_activity_view;
    @BindView(R.id.main_image_grid) GridView image_grid;
    @BindView(R.id.main_header_tags) LinearLayout main_header_tags;
    @BindView(R.id.main_header_date) Spinner main_header_date;
    @BindView(R.id.main_header_category) Spinner main_header_category;


    private final String GET_POSTS_ALL_CATEGORIES = "All";
    private final String GET_POSTS_FIRST_TIME = "firstTime";

    public static final int CODE_POST_CATEGORIES = 291;
    public static final int CODE_POST_DATES = 292;
    public static final int CODE_POSTS = 293;
    public NetworkInterface networkInterface;

    public boolean initialDataLoadedCat = false;
    public boolean initialDataLoadedDates = false;

    public List<com.main.junaidstore.models.Categories> categoriesList;
    public List<com.main.junaidstore.models.Posts> datesList;
    public List<com.main.junaidstore.models.Posts> postsList;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        networkInterface = new NetworkInterface(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("loading more records...");
        progress.show();
        main_header_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(initialDataLoadedCat && initialDataLoadedDates){
                    loadBasedOnNewCategories();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        main_header_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(initialDataLoadedCat && initialDataLoadedDates){
                    loadBasedOnNewCategories();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(GeneralFunctions.getSessionValue(this,getResources().getString(R.string.userid)).equals("1")){
            createDrawer();
        }

    }

    private void loadBasedOnNewCategories(){

        progress.show();

        String categoryid = GET_POSTS_ALL_CATEGORIES;
        for(int i=0;i<this.categoriesList.size();i++){
            if(this.categoriesList.get(i).getCategory().equals(main_header_category.getSelectedItem().toString())){
                categoryid = this.categoriesList.get(i).getID();
            }
        }

        MainActivity.this.networkInterface.getPosts(GeneralFunctions.getSessionValue(MainActivity.this,getResources().getString(R.string.userid)),
                GeneralFunctions.getSessionValue(MainActivity.this,getResources().getString(R.string.access_token)),
                categoryid,
                main_header_date.getSelectedItem().toString(),
                CODE_POSTS);
    }



    @Override
    protected void onResume(){
        super.onResume();

        initialDataLoadedCat = false;
        initialDataLoadedDates = false;

        this.networkInterface.getCategories(GeneralFunctions.getSessionValue(this,getResources().getString(R.string.userid)),
                GeneralFunctions.getSessionValue(this,getResources().getString(R.string.access_token)),
                CODE_POST_CATEGORIES);

        this.networkInterface.getPostDates(GeneralFunctions.getSessionValue(this,getResources().getString(R.string.userid)),
                GeneralFunctions.getSessionValue(this,getResources().getString(R.string.access_token)),
                CODE_POST_DATES);

        this.networkInterface.getPosts(GeneralFunctions.getSessionValue(this,getResources().getString(R.string.userid)),
                GeneralFunctions.getSessionValue(this,getResources().getString(R.string.access_token)),
                GET_POSTS_FIRST_TIME,
                "",
                CODE_POSTS);

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
        Drawer result = new DrawerBuilder()
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
                        else if(position == -1 ){

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Logout")
                                    .setMessage("Are you sure you want to logout ?")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            GeneralFunctions.addSessionValue(MainActivity.this,getResources().getString(R.string.access_token),"");
                                            GeneralFunctions.addSessionValue(MainActivity.this,getResources().getString(R.string.usertype),"");
                                            GeneralFunctions.addSessionValue(MainActivity.this,getResources().getString(R.string.userid),"");
                                            Intent intent = new Intent(MainActivity.this,login.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                            startActivity(intent);
                                        }})
                                    .setNegativeButton(android.R.string.no, null).show();
                        }
                        return false;
                    }
                })
        .build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName("Logout"));
    }


    @Override
    public void AsyncCallback(int resultCode, Parcelable rf) {
        com.main.junaidstore.models.Response response = Parcels.unwrap(rf);

        if(CODE_POST_CATEGORIES == resultCode){
            categoriesList = response.getCategories();
            populateCategoriesDropdown(categoriesList);
            initialDataLoadedCat = true;
        }
        else if(CODE_POST_DATES == resultCode){
            datesList = response.getPosts();
            populateDatesDropdown(datesList);
            initialDataLoadedDates = true;
        }
        else if(CODE_POSTS == resultCode){
            this.postsList = response.getPosts();

            if(this.postsList.size() == 0){
                Toast.makeText(getApplicationContext(),"No Records in this category",Toast.LENGTH_SHORT).show();
            }

            image_grid.setAdapter(new MainGridAdapter(getApplicationContext(),this.postsList,this));
        }
        progress.dismiss();

    }

    private void populateCategoriesDropdown(List<com.main.junaidstore.models.Categories> categories){

        List<String> str = new ArrayList<>();

        str.add("All");
        for(com.main.junaidstore.models.Categories cat: categoriesList){
            str.add(cat.getCategory());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, str);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_header_category.setAdapter(adapter);
    }

    private void populateDatesDropdown(List<com.main.junaidstore.models.Posts> posts){

        List<String> str = new ArrayList<>();

        for(int i=posts.size() -1 ;i>=0;i--){

            str.add(posts.get(i).getCreatedAt());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, str);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        main_header_date.setAdapter(adapter);
    }
}
