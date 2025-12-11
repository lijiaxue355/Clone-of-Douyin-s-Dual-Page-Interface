# Douyin Android 项目

这是一个专注于“高性能短视频体验”的 Android 示例项目。核心目标是实现外流与内流之间的顺滑衔接与实时交互更新，同时避免不必要的视图重建与黑屏闪烁。

项目以 ViewPager2 + RecyclerView 为基础，结合 DiffUtil + 稳定 ID + copy‑on‑write 的刷新策略，只更新变更项、不重建 Fragment；播放层采用 Media3 ExoPlayer，支持当前项与下一项的队列预加载；数据通过 Retrofit 获取，并可灵活切换到真实服务端。

亮点：
- 只更新不重建：内外流统一最小刷新策略，避免 Fragment 重建
- 播放稳定：Media3 + 预加载，减少切换时的卡顿与空窗
- 数值健壮：点赞/收藏/评论更新与“万”单位解析显示一致
- 架构清晰：ViewModel 驱动状态，UI 通过数据观察实现轻耦合

## 功能概览
- 首页 Feed 列表与 Tab 切换
- 外流列表点击进入内流垂直视频页
- 视频播放与队列预加载（当前+下一条）
- 点赞/取消点赞、收藏/取消收藏、评论新增
- 下拉刷新与数据重载

## 技术栈
- UI：`ViewPager2`、`RecyclerView`、DataBinding
- 刷新：`DiffUtil` 最小刷新（稳定 ID）
- 播放：`Media3 ExoPlayer`
- 网络：`Retrofit + Gson`
- 状态：`LiveData + ViewModel`

## 目录与核心模块
- `view/fragment/HomeFragment`：首页容器，承载 Tab 与 ViewPager2
- `view/fragment/RecyFragment`：首页 Feed 列表，绑定 `HomeRecyclerAdapter`
- `view/fragment/InterFragment`：外流容器，内部为垂直视频 `ViewPager2`
- `view/fragment/InterVp2Fragment`：单个视频的播放与交互（点赞/收藏/评论）
- `view/adapts/HomeRecyclerAdapter`：首页列表适配器（稳定 ID）
- `view/adapts/InterVp2Adapter`：内流适配器（稳定 ID，避免重建 Fragment）
- `view/adapts/CommentRecyclerAdapter`：评论列表适配器
- `view/adapts/SUpdataVideoDiffCallBack`：视频项 DiffUtil 比较
- `viewmodel/HomeViewModel`：列表状态、交互更新（copy-on-write 触发 DiffUtil）
- `data/repository/Repository`：数据来源与刷新接口（Retrofit）
- `Utils`：点赞数值解析与格式化（支持“万”）

## 快速开始
### 环境要求
- Android Studio（Koala 或更新）
- JDK 与 Gradle 版本按项目配置（使用 Gradle Wrapper）
- Android SDK 已安装

### 构建与运行
- 命令行：
  - Windows：`gradlew.bat assembleDebug`
  - macOS/Linux：`./gradlew assembleDebug`
- IDE：导入项目后，直接运行 `app` 模块

## 配置说明
- 接口地址：`data/api/RetrofitClient.java` 的 `URL` 常量为默认 Mock 地址；实际环境请替换为你的服务端域名
- 刷新接口：`Repository.getMutVideoList()` 与 `getRefushMutVideoList()` 分别用于初次加载与刷新
- 视频缓存：`HomeViewModel.getSimpleCheched(Context)` 使用 Media3 `SimpleCache`，默认缓存目录为 `cache/video_cache`

## 刷新与性能策略
- 列表最小刷新：
  - 外流 `InterVp2Adapter` 启用稳定 ID（`getItemId/containsItem`），避免数据更新重建已有 Fragment
  - `HomeViewModel` 在点赞/收藏/评论更新时复制列表与视频对象（copy-on-write），向 LiveData 提交“新实例”，确保 DiffUtil 能识别变化
- 内流局部更新：
  - `InterVp2Fragment` 观察列表，仅在点赞/收藏或评论数量变化时更新绑定或评论列表，避免播放器视图重绘引发闪屏

## 常见问题
- 首页不显示：确认 `RecyFragment` 已设置 `LayoutManager` 且调用了 `setAdapter`
- 点赞后闪屏：确认稳定 ID 已启用且没有在数据变更时 new 碎片；仅做局部绑定更新
- 点赞数值异常：`Utils.parseCount/formatCount` 仅解析数字与“万”；服务端字段应为如 `"123"` 或 `"1.2万"`
- 刷新不生效：确保 `Repository` 请求成功返回数据并更新到 `MutableLiveData`

## 开发指南
- 分支约定：建议使用 `feat/`、`fix/`、`refactor/` 前缀
- 提交信息：简洁描述改动与影响范围
- 代码风格：遵循项目已有 DataBinding、命名与空安全（`Objects.equals`）习惯

## 许可
- 请根据你的项目实际选择许可协议（如 MIT/Apache-2.0），并在此更新说明
