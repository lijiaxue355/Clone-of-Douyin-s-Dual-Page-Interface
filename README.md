# 抖音推荐页内外流仿写

## 1. 项目介绍

本项目为训练营作业，核心目标是 **实现抖音推荐页“内外流”交互**，采用了**MVVM**架构，使用了**DataBinding** 进行视图绑定，**ViewModel**保存数据，**LiveData**实现动态更新，**Retrofit2** 进行网络请求，**Glide**进行图片加载，**ExoPlayer**作为视频播放器。



## 2. 项目架构

以下是项目架构图以及对应描述：

```
com.example.douyin
│
├── data                     # 数据层：retrofit2 + 模型类 + Repository
│   ├── model                # API 数据模型定义（Video、Comments 等）
│   ├── api                  # Retrofit 接口定义、请求封装
│   └── repository           # 数据仓库，统一管理网络数据来源
│
├── view                     # 视图层：Fragment、Adapter
│   ├── adapters             # 所有适配器
│   └── fragments            # 所有页面 Fragment（推荐页、内流页等）
│   
│
├── viewmodel                # ViewModel 层
│   └── HomeViewModel        # 共用ViewModel
│
└── utils                # 工具类
├── mainactivity         # 主 UI 容器 Activity
└── VideoBindingAdapter  # BindingAdapter
```



## 3. 视频展示

[百度网盘 - 视频播放](https://pan.baidu.com/pfile/video?path=%2F网络视频%2Fdouyinfangxie1.mp4&theme=light&view_from=personal_file&from=home)

