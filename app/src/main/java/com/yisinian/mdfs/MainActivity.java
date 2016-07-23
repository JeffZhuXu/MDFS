package com.yisinian.mdfs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yisinian.mdfs.activity.AnotherAty;
import com.yisinian.mdfs.activity.Slider;
import com.yisinian.mdfs.activity.Test;
import com.yisinian.mdfs.service.PrintPerSecond;





public class MainActivity extends AppCompatActivity implements ServiceConnection, View.OnClickListener {

    Intent intent;


    public TextView textView;
    private ImageView iv;
    private EditText editText;
    private PrintPerSecond.Binder binder =null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.mainTextView);

        editText = (EditText) findViewById(R.id.data);
        findViewById(R.id.nextAty).setOnClickListener(this);
        findViewById(R.id.startServicebutton).setOnClickListener(this);
        findViewById(R.id.endServiceButton).setOnClickListener(this);
        findViewById(R.id.boundServicebutton).setOnClickListener(this);
        findViewById(R.id.unBoundbutton).setOnClickListener(this);
        findViewById(R.id.syncbutton).setOnClickListener(this);
        findViewById(R.id.fragmentbutton).setOnClickListener(this);
        findViewById(R.id.startSliderbutton).setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        textView.setText(data.getStringExtra("data"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    //服务被绑定之后执行
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (PrintPerSecond.Binder)service;
        binder.getService().setCallback(new PrintPerSecond.Callback() {
            @Override
            public void onDataChanged(String data) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("data",data);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        Log.i("MDFS","服务绑定连接成功");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(msg.getData().getString("data"));
        }
    };

    @Override
    //服务被销毁之后执行
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case  R.id.nextAty:
                Intent m = new Intent(this,AnotherAty.class);
                startActivity(m);
                break;
            case R.id.startServicebutton:
                Intent i = new Intent(MainActivity.this,PrintPerSecond.class);
                i.putExtra("data",editText.getText().toString());
                startService(i);
                break;
            case R.id.endServiceButton:
                Intent j = new Intent(MainActivity.this,PrintPerSecond.class);
                stopService(j);
                break;
            case R.id.boundServicebutton:
                Intent k = new Intent(MainActivity.this,PrintPerSecond.class);
                bindService(k,MainActivity.this,Context.BIND_AUTO_CREATE);
                Log.i("MDFS","绑定度服务");
                break;
            case R.id.unBoundbutton:
                unbindService(MainActivity.this);
                Log.i("MDFS", "解绑服务");
                break;
            case R.id.syncbutton:
                if(binder!=null){
                    binder.setData(editText.getText().toString());
                    Log.i("MDFS","向绑定服务中传递参数");
                }
                break;
            case R.id.fragmentbutton:
                Intent w = new Intent(MainActivity.this, Test.class);
                startActivity(w);
                Log.i("MDFS", "打开一个带有fragment的activity");
                break;
            case R.id.startSliderbutton:
                Intent e = new Intent(MainActivity.this, Slider.class);
                startActivity(e);
                break;

        }

    }
}
