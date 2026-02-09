# MedNote APK 下载指南

由于您的系统缺少Android SDK和JDK 17，无法直接在本地构建APK。以下是几种获取APK的方法：

## 推荐方法：使用GitHub Actions自动构建（免费）

### 步骤1：创建GitHub仓库
1. 访问 https://github.com/new
2. 创建一个新的公开仓库（例如：MedNote）
3. 不要初始化README、.gitignore或LICENSE

### 步骤2：推送代码到GitHub
在项目目录打开命令行，运行：
```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/你的用户名/MedNote.git
git push -u origin main
```

### 步骤3：触发构建
1. 访问你的GitHub仓库
2. 点击 "Actions" 标签
3. 选择 "Build APK" workflow
4. 点击 "Run workflow" 按钮
5. 选择 "main" 分支，点击 "Run workflow"

### 步骤4：下载APK
1. 等待构建完成（约5-10分钟）
2. 在Actions页面找到成功的构建
3. 滚动到底部，找到 "Artifacts" 部分
4. 点击 "MedNote-APK" 下载APK文件

## 替代方法：使用在线构建平台

### 1. GitLab CI（免费）
- 将项目推送到GitLab
- 项目会自动构建APK
- 在CI/CD页面下载APK

### 2. Bitbucket Pipelines（免费）
- 将项目推送到Bitbucket
- 配置pipelines
- 自动构建并下载APK

### 3. AppVeyor（免费开源项目）
- 支持Windows构建
- 自动上传APK

## 临时方案：使用Web应用

如果您急需使用，我可以帮您创建一个Web版本的应用，可以在手机浏览器中直接使用。

## 安装APK到手机

### 方法1：直接安装
1. 将APK文件传输到手机（通过USB、微信、QQ等）
2. 在手机文件管理器中找到APK文件
3. 点击安装
4. 允许安装未知来源应用

### 方法2：通过ADB安装
```bash
adb install app-release.apk
```

### 华为/荣耀手机注意事项
1. 进入设置 -> 安全和隐私 -> 允许安装未知来源应用
2. 可能需要关闭"纯净模式"或"安全守护"
3. 安装后建议在设置中信任该应用

## 常见问题

### Q: 构建失败怎么办？
A: 检查代码是否完整提交，确保所有文件都已推送到GitHub。

### Q: APK无法安装？
A: 
- 检查手机是否允许安装未知来源应用
- 确保APK文件完整下载
- 尝试重新下载APK

### Q: 应用闪退？
A: 
- 确保手机Android版本 >= 7.0
- 清除应用数据后重试
- 查看手机存储空间是否充足

## 联系支持

如果遇到问题，请提供：
- 错误信息截图
- 手机型号和Android版本
- 构建日志（如果使用GitHub Actions）
