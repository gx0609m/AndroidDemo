package com.example.administrator.androiddemo.rx.rxbus;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by gx on 2018/6/11 0011
 */

public class RxBus {
    private static RxBus mInstance;
    private Subject _bus;

    private RxBus() {
        // ReplaySubject在订阅者订阅时，会发送所有的数据给订阅者，无论它们是何时订阅的
        // PublishSubject只会给在订阅者订阅的时间点之后的数据发送给观察者
        // BehaviorSubject在订阅者订阅时，会发送其最近发送的数据（如果此时还没有收到任何数据，它会发送一个默认值）
        // AsyncSubject只在原Observable事件序列完成后，发送最后一个数据，后续如果还有订阅者继续订阅该Subject, 则可以直接接收到最后一个值
        _bus = new SerializedSubject<>(BehaviorSubject.create());
        // 这里由于我们是先发送再订阅的，如果使用PublishSubject就会接收不到
    }

    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    public void send(Object o) {
        _bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
        // return bus.ofType(eventType);

        //        ofType = filter + cast
        //        return bus.filter(new Func1<Object, Boolean>() {
        //            @Override
        //            public Boolean call(Object o) {
        //                return eventType.isInstance(o);
        //            }
        //        }) .cast(eventType);
        return _bus;
    }
    // 也可以用无参的toObservable()方法
    // public Observable<Object> toObservable() {
    //        return _bus;
    // }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}
