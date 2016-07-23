package com.yisinian.mdfs.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.fragment.Blocks;
import com.yisinian.mdfs.fragment.Files;
import com.yisinian.mdfs.fragment.HomePage;

import com.yisinian.mdfs.fragment.NodeMessageFragment;
import com.yisinian.mdfs.fragment.Setting;

import com.yisinian.mdfs.http.MDFSHttp;
import com.yisinian.mdfs.table.NodeMessage;


public class Slider extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Button saveTextButton;
    private Button openTextButton;
    private EditText fileContentEditText;
    private String fileName = "xiaohaidao";
    private ImageView nodeLogo;


    //侧边栏headerview
    private View header;
    private TextView nodeName;
    private TextView nodeBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        getSupportFragmentManager().beginTransaction().replace(R.id.home, new HomePage()).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.inflateHeaderView(R.layout.nav_header_slider);
        nodeName= (TextView) header.findViewById(R.id.nodeNameBrief);
        nodeBrief=(TextView) header.findViewById(R.id.nodeTypeBrief);
        nodeName.setText(NodeMessage.getPhoneId(this));
        nodeBrief.setText(android.os.Build.MODEL);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slider, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nodeMessage) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.home, new NodeMessageFragment()).commit();      //查看节点信息界面
        } else if (id == R.id.nodeFiles) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home, new Files()).commit();      //查看文件信息界面
        } else if (id == R.id.nodeBlocks) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home, new Blocks()).commit();      //查看分块信息界面
        } else if (id == R.id.nodeSetting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home, new Setting()).commit();      //查看节点设置界面
        } else if (id == R.id.nav_share) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home, new HomePage()).commit();      //返回主界面
        } else if (id == R.id.nav_send) {
            //暂时为空
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    //主activity被杀死过后，向服务器发送请求，通知服务器节点下线
    @Override
    protected void onDestroy() {
        MDFSHttp.nodeLogout(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
