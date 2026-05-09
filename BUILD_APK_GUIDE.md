# APK构建指南
## 方法一：使用Android Studio（最简单）
1. 下载安装 Android Studio
2. 打开 Android Studio
3. 选择 "Open Project"，打开 `loan_tracker_app` 文件夹
4. Android Studio会自动同步项目
5. 连接Android手机，开启USB调试模式
6. 点击菜单栏的 "Run" → "Run 'app'"
7. 选择你的手机设备，APP会自动安装到手机
8. 如果要生成APK文件，点击 "Build" → "Build Bundle(s) / APK(s)" → "Build APK(s)"
## 方法二：命令行构建（需要Android SDK）
1. 确保已安装Android SDK和Gradle
2. 进入项目目录：cd loan_tracker_app
3. 运行：gradle assembleDebug
4. APK文件会生成在：app/build/outputs/apk/debug/app-debug.apk
## 方法三：使用在线构建服务
1. 将项目上传到Github
2. 使用Github Actions配置Android构建
3. 自动生成APK文件下载
## 注意事项
- 如果没有Android SDK环境，建议使用Android Studio
- 生成的APK文件可以直接安装到Android手机
- 第一次安装需要允许"未知来源应用"安装
