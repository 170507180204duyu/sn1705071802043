package cn.edu.sdwu.android02.classroom.sn170507180204;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.view.View;
import android.widget.Toast;

public class Ch12Activity1 extends AppCompatActivity {
    private ServiceConnection serviceConnection;//定义一下服务连接
    private MyService2 myService2;
    private boolean bindSucc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch12_1);
        bindSucc=false;
        //实现接口
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //当调用者与服务建立起连接后，会自动调用本方法
                //第二个参数，是service中onBind方法的返回值
                MyService2.MyBinder myBinder=(MyService2.MyBinder) iBinder;
                //得到服务
                myService2=myBinder.getRandomService();
                bindSucc=true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //当调用者与服务断开连接后，会自动调用本方法
                bindSucc=false;
            }
        };
    }
    //界面可见时绑定
    @Override
    protected void onStart() {
        Intent intent=new Intent(this,MyService2.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);//常量当服务不存在的话会帮我们自动创建
        super.onStart();
    }
    //界面不可见时解绑
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    public void startService(View view){
        //使用intent启动服务
        Intent intent=new Intent(this,MyService.class);
        //使用startService以启动方式使用服务
        startService(intent);
    }
    public void stopService(View view){
        //停止服务
        Intent intent=new Intent(this,MyService.class);
        stopService(intent);
    }
    public void getRandom(View view){
        if(bindSucc){
           int ran=myService2.genRandom();
            Toast.makeText(this,ran+"",Toast.LENGTH_SHORT).show();
        }
    }
}
