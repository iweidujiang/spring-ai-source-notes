# Spring AI Source Notes

Spring AI 源码阅读笔记与可运行样例。文章会同步放在 `docs/articles/`，方便检索与复现。




## 技术栈

- Java 21
- Spring Boot **4.0.2**（正式版，避免 Boot SNAPSHOT）
- Spring AI **2.0.1**（Maven Central）
- 默认模型：**Ollama**（本地、无需云厂商 Key）

阅读官方 `main`（常为 `2.1.0-SNAPSHOT` + Boot `4.2.0-SNAPSHOT`）时，请用 `tooling/maven-settings-spring-ai.xml`，见环境文档。

## 快速开始

1. 安装并启动 [Ollama](https://ollama.com/)，拉取模型：

```powershell
ollama pull llama3.2
```

2. 用 JDK 21 运行本工程：

```powershell
$env:JAVA_HOME="D:\Java\jdk-21.0.4"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
cd D:\a-github-project\spring-ai-source-notes
mvn spring-boot:run
```

3. 调用：

```text
GET http://localhost:8080/chat?q=用一句话介绍%20ChatClient
```

对 `ChatClientCallController` 里的 `.call()` 下断点，即可沿 Advisor → `ChatModel` 阅读源码。

## 仓库结构

```text
docs/                 阅读指南与博文
tooling/              读官方 spring-ai 用的 Maven settings
src/main/java/...     按主题扩展的最小样例（先从 chatclient 开始）
```


## License

MIT License
