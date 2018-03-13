package com.example.administrator.androiddemo.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;

import com.example.administrator.androiddemo.Constant;
import com.example.administrator.androiddemo.MyAIDLService;
import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/8 0008
 * http://blog.csdn.net/guolin_blog/article/details/11952435
 */

public class ServiceLearningActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "ServiceLearningActivity";

    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unBindService;
    private Button bindFrontService;
    private Button unBindFrontService;
    private Button remoteService;
    private Button unbindRemoteService;
    private Button startRemoteService;
    private Button stopRemoteService;
    private Button bindRemoteServiceWithAIDL;
    private Button unBindRemoteServiceWithAIDL;
    private Button startIntentService;

    private MyService.MyBinder myBinder;

    private MyFrontService.MyFrontBinder myFrontBinder;

    private MyRemoteService.MyRemoteBinder myRemoteBinder;

    private MyAIDLService myAIDLService;

    /**
     * 构建ServiceConnection实例【注意：此时activity和service并未发生关联】
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /**
             * 向下转型得到MyBinder实例，有了这个实例，Activity和Service之间的关系就变得非常紧密了，
             * 现在我们可以在Activity中根据具体的场景来调用MyBinder中的任何public方法，即实现了Activity指挥Service干什么Service就去干什么的功能
             */
            myBinder = (MyService.MyBinder) service;
            // 调用MyBinder中的方法
            myBinder.startDoSomething();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    // 用于前台service
    private ServiceConnection frontServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myFrontBinder = (MyFrontService.MyFrontBinder) service;
            myFrontBinder.doSomething();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    // 用于远程Service (binder为远程service中定义的binder)
    // 此方式对使用bindService方式启动Service来说，会导致应用崩溃，所以如果不用aidl,那么跨进程的service调用就只能使用startService方式
    private ServiceConnection remoteServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myRemoteBinder = (MyRemoteService.MyRemoteBinder) service;
            myRemoteBinder.doRemoteThing();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    // 用于远程Service (binder为远程aidl中生成的的Stub)【stub是Binder的子类】
    // 此方式对使用bindService方式启动Service来说，不会导致应用崩溃
    private ServiceConnection remoteAIDLServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 虽然是远程service,但是这里的代码仍然在当前activity的进程中执行

            // 首先使用MyAIDLService.Stub.asInterface()将传入的IBinder对象传换成了MyAIDLService对象
            myAIDLService = MyAIDLService.Stub.asInterface(service);
            // 调用在MyAIDLService.aidl文件中定义的所有接口方法
            try {
                int result = myAIDLService.plus(3, 5);
                String upperStr = myAIDLService.toUpperCase("hello world");
                Log.d("TAG", "result is " + result);
                Log.d("TAG", "upperStr is " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 用于接收MyIntentService发送的广播，来在主线程中进行UI处理或者其他的逻辑操作
     */
    private BroadcastReceiver uploadImgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 通过action进行区分，是否是MyIntentService发送的带有upload_img_action的广播
            if (Constant.UPLOAD_IMG_RESULT_ACTION.equals(intent.getAction())) {
                // http://blog.csdn.net/lmj623565791/article/details/47143563
                handleUploadImgResult(); // 上传之后的相关处理工作
            }
        }
    };

    /**
     * uploadImgReceiver广播注册
     */
    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.UPLOAD_IMG_RESULT_ACTION);
        registerReceiver(uploadImgReceiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_learning);

        initView();
        Log.d(TAG, "process id is " + android.os.Process.myPid());
        Log.d(TAG, "MainActivity thread id is " + Thread.currentThread().getId());

        registerBroadcastReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 通过构建Intent对象，来启动或停止Service(两种方式)
             */

            // startService方式启动Service
            case R.id.startService:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stopService:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;

            // bindService方式启动Service
            case R.id.bindService:
                /**
                 * 通过bindService()方法将Activity和Service进行绑定
                 * 第一个参数就是刚刚构建出的Intent对象；
                 *
                 * 第二个参数是前面创建出的ServiceConnection的实例，activity和service在创建关
                 * 联和解除关联时都会回调此ServiceConnection中的方法，并且在创建关
                 * 联的回调方法onServiceConnected()中，可以实现activity对service功能的控制【控制service中MyBinder类中的任何public方法】；
                 *
                 * 第三个参数是一个标志位，这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后
                 * 自动创建Service，这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行。
                 */
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unBindService:
                unbindService(serviceConnection);
                break;

            // bindService方式启动前台service
            case R.id.bindFrontService:
                Intent bindFrontService = new Intent(this, MyFrontService.class);
                bindService(bindFrontService, frontServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unBindFrontService:
                unbindService(frontServiceConnection);
                break;


            /**
             * 这里我们通过bindService、startService两种方式来分别启动 远程Service
             *
             * 但是会发现：
             *     bindService启动，程序会崩溃，
             *     startService启动，程序不会崩溃，
             * 这是由于bindService会让当前Activity和MyRemoteService建立关联，但是目前MyRemoteService
             * 是一个远程Service，Activity和Service运行在两个不同的进程当中，
             * 这时就不能再使用传统的建立关联的方式，否则程序就会崩溃
             *
             * 那如何才能让Activity与一个 远程Service 建立关联呢？
             * 这就要使用AIDL来进行跨进程通信了（IPC）
             *
             * AIDL（Android Interface Definition Language）是Android接口定义语言的意思，
             * 它可以用于让某个Service与多个应用程序组件之间进行跨进程通信，
             * 从而可以实现多个应用程序共享同一个Service的功能。
             */
            // bindService方式启动 远程Service (ServiceConnection中 未牵涉到AIDL文件)
            case R.id.remoteService:
                Intent bindRemoteService = new Intent(this, MyRemoteService.class);
                bindService(bindRemoteService, remoteServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbindRemoteService:
                unbindService(remoteServiceConnection);
                break;
            // startService方式启动 远程Service
            case R.id.startRemoteService:
                Intent startRemoteServiceIntent = new Intent(this, MyRemoteService.class);
                startService(startRemoteServiceIntent);
                break;
            case R.id.stopRemoteService:
                Intent stopRemoteServiceIntent = new Intent(this, MyRemoteService.class);
                stopService(stopRemoteServiceIntent);
                break;

            // bindService方式启动 远程Service (ServiceConnection中 牵涉到AIDL文件)
            // 会发现，当时用了AIDL时，bindService方式启动Service进行进程间通信时，不会导致应用崩溃
            case R.id.bindRemoteServiceWithAIDL:
                Intent bindRemoteServiceWithAIDL = new Intent(this, MyRemoteService.class);
                bindService(bindRemoteServiceWithAIDL, remoteAIDLServiceConnection, BIND_AUTO_CREATE);
                break;
            /**
             * 到此，我们已经成功实现跨进程通信了，在一个进程中访问到了另外一个进程中的方法，
             * 但目前的跨进程通信其实并没有什么实质上的作用，因为这只是在一个Activity里调用了同一个应用程序的Service里的方法。
             * 而跨进程通信的真正意义是为了让一个应用程序去访问另一个应用程序中的Service，以实现共享Service的功能
             *
             * 那如何才能在其它的应用程序中调用到MyRemoteService里的方法呢？
             *
             * 我们知道，如果想要让Activity与Service之间建立关联，需要调用bindService()方法，
             * 并将Intent作为参数传递进去，在Intent里指定好要绑定的Service
             * 但是在另一个应用程序中去绑定Service的时候并没有MyService这个类。
             *
             * 这时就必须使用到隐式Intent了，需要修改AndroidManifest.xml中的代码，给MyRemoteService加上一个action，
             * 具体可见：http://blog.csdn.net/guolin_blog/article/details/9797169
             * （调用远程服务的时候，不能直接使用隐式的Intent，需要在Intent里面指定package名，否则会报错。
             * 需要加上一行代码： intent.setPackage("PackageName") ）
             */
            case R.id.unBindRemoteServiceWithAIDL:
                unbindService(remoteAIDLServiceConnection);
                break;

            // startService方式启动IntentService
            case R.id.startIntentService:
                Intent startIntentServiceIntent = new Intent(this, MyIntentService.class);
                startIntentServiceIntent.setAction(Constant.UPLOAD_IMG_ACTION);
                startService(startIntentServiceIntent);
                break;
        }
    }

    private void handleUploadImgResult() {
        startIntentService.setText("Start IntentService Success");
    }

    @Override
    protected void onStop() {
        super.onStop();
        startIntentService.setText("Start IntentService");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(uploadImgReceiver);
    }

    private void initView() {
        startService = (Button) findViewById(R.id.startService);
        startService.setOnClickListener(this);

        stopService = (Button) findViewById(R.id.stopService);
        stopService.setOnClickListener(this);

        bindService = (Button) findViewById(R.id.bindService);
        bindService.setOnClickListener(this);

        unBindService = (Button) findViewById(R.id.unBindService);
        unBindService.setOnClickListener(this);

        bindFrontService = (Button) findViewById(R.id.bindFrontService);
        bindFrontService.setOnClickListener(this);

        unBindFrontService = (Button) findViewById(R.id.unBindFrontService);
        unBindFrontService.setOnClickListener(this);

        remoteService = (Button) findViewById(R.id.remoteService);
        remoteService.setOnClickListener(this);

        unbindRemoteService = (Button) findViewById(R.id.unbindRemoteService);
        unbindRemoteService.setOnClickListener(this);

        startRemoteService = (Button) findViewById(R.id.startRemoteService);
        startRemoteService.setOnClickListener(this);

        stopRemoteService = (Button) findViewById(R.id.stopRemoteService);
        stopRemoteService.setOnClickListener(this);

        bindRemoteServiceWithAIDL = (Button) findViewById(R.id.bindRemoteServiceWithAIDL);
        bindRemoteServiceWithAIDL.setOnClickListener(this);

        unBindRemoteServiceWithAIDL = (Button) findViewById(R.id.unBindRemoteServiceWithAIDL);
        unBindRemoteServiceWithAIDL.setOnClickListener(this);

        startIntentService = (Button) findViewById(R.id.startIntentService);
        startIntentService.setOnClickListener(this);
    }
}
