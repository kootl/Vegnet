package com.sunstar.vegnet.kootl.comm.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by louisgeek on 2016/12/5.
 *
 * 1、Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，要避免该问题，
 * 需要将 Subject转换为一个 SerializedSubject
 ，RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
 2、PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者。
 3、ofType操作符只发射指定类型的数据，其内部就是filter+cast  （filter操作符可以使你提供一个指定的测试数据项，只有通过测试的数据才会被“发射”。
 cast操作符可以将一个Observable转换成指定类型的Observable。）

 */
@Deprecated
public class RxBus {
    private static volatile RxBus mInstance;

    /* 私有构造方法，防止被实例化 */
    private RxBus() {
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

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */
    private final Subject<Object, Object> mBus = new SerializedSubject<>(PublishSubject.create());


    public void post(Object o) {
        mBus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

}
