package com.lzm.shukong.util;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//    /**
//     * 统一返回结果处理
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<GankHttpResponse<T>, T> handleResult() {   //compose判断结果
//        return new Observable.Transformer<GankHttpResponse<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<GankHttpResponse<T>> httpResponseObservable) {
//                return httpResponseObservable.flatMap(new Func1<GankHttpResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(GankHttpResponse<T> tGankHttpResponse) {
//                        if(!tGankHttpResponse.getError()) {
//                            return createData(tGankHttpResponse.getResults());
//                        } else {
//                            return Observable.error(new ApiException("服务器返回error"));
//                        }
//                    }
//                });
//            }
//        };
//    }
//
//    /**
//     * 统一返回结果处理
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<WXHttpResponse<T>, T> handleWXResult() {   //compose判断结果
//        return new Observable.Transformer<WXHttpResponse<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<WXHttpResponse<T>> httpResponseObservable) {
//                return httpResponseObservable.flatMap(new Func1<WXHttpResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(WXHttpResponse<T> tWXHttpResponse) {
//                        if(tWXHttpResponse.getCode() == 200) {
//                            return createData(tWXHttpResponse.getNewslist());
//                        } else {
//                            return Observable.error(new ApiException("服务器返回error"));
//                        }
//                    }
//                });
//            }
//        };
//    }
//
//    /**
//     * 统一返回结果处理
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
//        return new Observable.Transformer<MyHttpResponse<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<MyHttpResponse<T>> httpResponseObservable) {
//                return httpResponseObservable.flatMap(new Func1<MyHttpResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(MyHttpResponse<T> tMyHttpResponse) {
//                        if(tMyHttpResponse.getCode() == 200) {
//                            return createData(tMyHttpResponse.getData());
//                        } else {
//                            return Observable.error(new ApiException("服务器返回error"));
//                        }
//                    }
//                });
//            }
//        };
//    }

//    /**
//     * 统一返回结果处理
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<MeipaiHttpResponse<T>, T> handleMeipaiResult() {   //compose判断结果
//        return new Observable.Transformer<MeipaiHttpResponse<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<MeipaiHttpResponse<T>> httpResponseObservable) {
//                return httpResponseObservable.flatMap(new Func1<MeipaiHttpResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(MeipaiHttpResponse<T> meipaiHttpResponse) {
//                        if(meipaiHttpResponse.getError() != null) {
//                            return createData(meipaiHttpResponse.getResults());
//                        } else {
//                            return Observable.error(new ApiException("服务器返回error"));
//                        }
//                    }
//                });
//            }
//        };
//    }

    /**
     * 生成Observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
