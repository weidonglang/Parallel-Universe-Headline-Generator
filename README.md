# demo1

这是一个 Spring Boot 小项目，当前实现了一个「平行宇宙头条生成器」。

用户可以输入名字、宠物、物件、会议、抽象概念等内容，系统会自动判断输入类型，然后生成一条一本正经但有点离谱的平行宇宙快讯。

## 功能

- 输入任意主题生成离谱头条
- 自动识别人物、宠物、事件、物件、抽象概念等类型
- 根据类型使用不同文案模板，减少生硬套词
- 自动补充更自然的称呼，例如 `宠物` 会变成 `这只宠物`
- 自动去重标签，避免页面上出现重复 hashtag
- 提供一个简单的浏览器页面，不需要额外前端构建

## 技术栈

- Java 17
- Spring Boot 4.0.5
- Gradle
- 原生 HTML / CSS / JavaScript

## 启动项目

Windows:

```powershell
.\gradlew.bat bootRun
```

如果本机 `JAVA_HOME` 配置不正确，可以临时指定 JDK 17 后再启动：

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-17.0.18+8'
$env:Path="$env:JAVA_HOME\bin;" + $env:Path
.\gradlew.bat bootRun
```

启动后访问：

```text
http://localhost:8080/
```

## API

生成一条平行宇宙新闻：

```http
GET /api/universe-news?topic=宠物
```

示例返回：

```json
{
  "anchor": "宠物",
  "headline": "今天最离谱的好消息：这只宠物用一个动作收买全场，已被认证为可爱秩序官",
  "scoop": "目击者称，这只宠物带来的压迫感和可爱感同时在线，难以反抗。",
  "quote": "“它只是路过一下，结果大家都开始想给它颁奖。”",
  "action": "今日任务：认真夸这只宠物三句，避免错过建立友好关系的窗口期。",
  "tags": ["毛茸茸权威", "轻度神话", "平行宇宙特供"]
}
```

## 测试

运行测试：

```powershell
.\gradlew.bat test
```

当前测试主要覆盖：

- 同一个输入生成结果稳定
- 空输入兜底
- 泛词不会重复主语，例如不会出现 `这只宠物这只宠物`
- `物件`、`会议` 等泛词会走正确模板
- 标签不会重复

## 项目结构

```text
src/main/java/com/example/demo
  Demo1Application.java          Spring Boot 启动类
  UniverseNews.java              新闻结果数据结构
  UniverseNewsController.java    REST API
  UniverseNewsService.java       类型识别和文案生成逻辑

src/main/resources/static
  index.html                     浏览器页面

src/test/java/com/example/demo
  UniverseNewsServiceTests.java  生成逻辑回归测试
```
