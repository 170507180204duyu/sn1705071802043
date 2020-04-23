package cn.edu.sdwu.android02.classroom.sn170507180204;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService2 extends Service {
    private Random random;//声明成员
    private MyBinder myBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        random=new Random();
        myBinder=new MyBinder();
    }

    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //返回值，会返回给调用者，以后调用者与服务进行交互，都会使用此返回值
        // TODO: Return the communication channel to the service.
        //如果绑定的话就返回myBinder
        //调用者就会拿到MyBinder类的实例
        Log.i(MyService2.class.toString(),"onBind");
        return myBinder;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        //解绑
        Log.i(MyService2.class.toString(),"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //当所有调用者都解绑后，销毁
        Log.i(MyService2.class.toString(),"onDestroy");
        super.onDestroy();
    }

    //内部类
    //Binder类实现了IBinder接口
    public class MyBinder extends Binder{
        public MyService2 getRandomService(){
            //得到service实例
            return MyService2.this;
        }

    }
    //对外提供服务的方法
    //实际与调用者进行交互的方法
    public int genRandom(){
        return random.nextInt();
    }
}
