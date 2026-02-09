# 构建APK指南

由于您的系统缺少Android SDK和JDK 17，需要先安装这些工具才能构建APK。

## 方法一：安装Android SDK和JDK（推荐）

### 1. 安装JDK 17
下载并安装JDK 17：
- 官方下载：https://adoptium.net/temurin/releases/?version=17
- 选择 Windows x64 版本
- 安装后配置环境变量 JAVA_HOME

### 2. 安装Android SDK命令行工具
下载Android命令行工具：
- 官方下载：https://developer.android.com/studio#command-tools
- 下载 "Command line tools only"
- 解压到：C:\Android\sdk\cmdline-tools\latest

### 3. 配置环境变量
在系统环境变量中添加：
```
ANDROID_HOME=C:\Android\sdk
JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot
PATH=%ANDROID_HOME%\cmdline-tools\latest\bin;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\build-tools\34.0.0;%JAVA_HOME%\bin;%PATH%
```

### 4. 接受许可证并安装必要组件
打开命令行，运行：
```bash
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
sdkmanager --licenses
```

### 5. 构建APK
```bash
cd c:\Users\admin\Documents\trae_projects\MedNote
gradlew.bat assembleRelease
```

APK文件位置：`app\build\outputs\apk\release\app-release.apk`

## 方法二：使用GitHub Actions自动构建

1. 将项目推送到GitHub
2. 在项目根目录创建 `.github/workflows/build.yml`：
```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    - name: Build with Gradle
      run: ./gradlew assembleRelease
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-release
        path: app/build/outputs/apk/release/app-release.apk
```

3. 推送代码后，在GitHub Actions页面点击"Run workflow"
4. 构建完成后下载APK

## 方法三：使用在线构建服务

推荐使用以下免费服务：
- GitHub Actions（免费，推荐）
- GitLab CI（免费）
- Bitbucket Pipelines（免费）
- AppVeyor（免费开源项目）

## 方法四：使用预编译的APK（临时方案）

如果您急需使用，我可以帮您：
1. 创建一个简化版的应用（使用Web技术）
2. 或者提供详细的安装指南

## 快速测试

如果您想快速测试应用功能，可以：
1. 使用Android模拟器（需要安装Android Studio）
2. 使用云手机服务（如Redfinger、蓝叠云手机等）

## 注意事项

- 构建APK需要至少2GB的磁盘空间
- 首次构建需要下载依赖，可能需要较长时间
- 确保网络连接正常，用于下载Gradle依赖
