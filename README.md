# ShuKong
shukong，a reading app  Material Design + MVP + RxJava + Retrofit + Dagger2 + Realm + Glide.


书控，一款阅读类App，基于Material Design + MVP + RxJava + Retrofit + Dagger2 + Realm + Glide


做这款App主要是报以学习的目的，所以项目还在测试中，也会有很多不足，谢谢指正。用的都是一些现在比较热门的技术知识和框架。
本项目仅做学习交流使用，API数据内容所有权归原作公司所有，请勿用于其他用途



阅读内容包括：
[知乎日报](https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90)：汇集知乎上的热门话题和新鲜事。
美拍视频：美拍视频的开发基础接口，海量视频。
干货集中营：每天都会提供很多的技术干货。
花瓣网API：花瓣网, 设计师寻找灵感的天堂!图片素材领导者。
另外还有一个获取图片网址。这个网址可以指定获取图片大小，随机获取，和模糊或灰度的摄影网址，我用作APP闪屏页。
    
    
  

使用RxJava配合Retrofit2做网络请求
使用RxUtil对线程操作和网络请求结果处理做了封装
使用RxPresenter对订阅的生命周期做管理
使用RxBus来方便组件间的通信
使用RxJava其他操作符来做延时、轮询、转化、筛选等操作
使用okhttp3对网络返回内容做缓存，还有日志、超时重连、头部消息的配置
使用Material Design控件和动画
使用MVP架构整个项目，对应于model、ui、presenter三个包
使用Dagger2将M层注入P层，将P层注入V层，无需new，直接调用对象
使用Realm做阅读记录和收藏记录的增、删、查、改使用Glide做图片的处理和加载
使用Fragmentation简化Fragment的操作和懒加载
使用RecyclerView实现下拉刷新、上拉加载、侧滑删除、长按拖曳
使用x5WebView做阅览页，比原生WebView体验更佳

Dagger2配合MVP的架构来自Hot，另外还参考了很多大神的类似作品.
iconfont 寻找icon素材
技术点：rxjava,Retrofit, MVP, OkHttp, ShareSDK分享,Glide图片加载, 友盟统计.换肤技术





