# 云原生雪花改进型分布式id服务

## 1，概述

 一个基于雪花改进型分布式id基础服务

demo地址：[http://uid.activeclub.site/web/v1/uuid/get](http://dev.activeclub.site/uid/web/v1/uuid/get)

![image-20231113163601177](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/2023/image-20231113163601177.png)

### 1.1，入参样例：

请求路径：GET http://{url}:{port}/web/uuid/get

入参说明：

| 类目          | 字段名               | 说明                                 |
| ------------- | -------------------- | ------------------------------------ |
| url           | 对外暴露的IP或者域名 | 本地启动一般为127.0.0.1或者localhost |
| port          | 对外暴露的端口信息   |                                      |
| /web/uuid/get | 获取唯一id的请求路径 |                                      |

### 1.2，反参样例：

```json
{
    "code":"0",
    "msg":"1",
    "data":15969077833633829
}
```

反参说明：

| 字段   | 字段名          | 类型         | 意义               |
|------|--------------|------------|------------------|
| code | 标识码          | string     | 0表示反参正常          |
| msg  | 运行节点的workId  | string     | 这个id是由那个工作容器节点产生 |
| data | 全局唯一的id（长整型） | integer 64 | 全局唯一的id          |

## 2，用法

### 2.1，jar程序

```sh
java -jar app.jar
```

备注：使用jdk21

测试：直接在浏览器请求 http://127.0.0.1:8080/web/uuid/get

### 2.2，Docker

docker.io镜像

```sh
# jvm模式运行
docker run -it -d -p 8080:8080 activeclub/uid-service:v1.0.0-jvm-2023-11-12
# native模式运行
docker run -it -d -p 8080:8080 activeclub/uid-service:v1.0.0-native-2023-11-12
```

阿里云mirror镜像：

```sh
# jvm模式运行
docker run -it -d -p 8080:8080 registry.cn-shanghai.aliyuncs.com/activeclub/uid-service:v1.0.0-jvm-2023-11-12
# native模式运行
docker run -it -d -p 8080:8080 registry.cn-shanghai.aliyuncs.com/activeclub/uid-service:v1.0.0-native-2023-11-12
```

测试：直接在浏览器请求 http://127.0.0.1:8080/web/uuid/get

### 2.3，K8s

Stateful.yaml部署

```yaml
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: uid-service
spec:
  serviceName: uid-service
  replicas: 3
  selector:
    matchLabels:
      app: uid-service
  template:
    metadata:
      labels:
        app: uid-service
    spec:
      containers:
        - name: uidserver
          imagePullPolicy: IfNotPresent
          image:  activeclub/uid-service:v1.0.0-native-2023-11-12
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8080
            initialDelaySeconds: 5
            periodSeconds: 10
          readinessProbe:
            httpGet:
              path: /actuator/health/readiness
              port: 8080
            initialDelaySeconds: 5
            periodSeconds: 10

---
apiVersion: v1
kind: Service
metadata:
  name: uid-service
spec:
  selector:
    app: uid-service
  ports:
    - name: uid-port
      port: 8080
      targetPort: 8080
      protocol: TCP
```

备注：

1，修改replicas，可以调整节点个数

2，设置健康检查，确保服务的可用性与自愈性

![image-20231113200623345](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/2023/image-20231113200623345.png)

## 3，底层逻辑

1. k8s-stateful部署时，运行程序的主机名称格式为(xxx-n)，其中n为1~m的不重复数值，m<=n，n为节点个数；因此程序使用主机名称的节点后缀设置为workId
2. 每个服务内部使用原子类AtomicLong用于确保生产id的唯一性和性能
3. 使用seata改进型雪花算法来确保每个节点产生的id唯一性
4. 底层打包使用GraalVM native-image，来确保docker容器只有160MB，其中80MB是程序运行，以及启动速度700ms

## 4，性能

| 类别               | JVM     | Native  |
|------------------|---------|---------|
| 启动耗时（s）          | 2.549   | 0.090   |
| 资源包压缩后大小（MB）     | 269.68  | 55.38   |
| 资源包大小（MB）        | 601.62  | 160.72  |
| 前1s，并发性能（QPS）    | 8441.0  | 11577.4 |
| 运行30s后，并发性能（QPS） | 15692.1 | 15240.0 |

测试请求并发线程数：16

资源包压缩后大小（MB）：

![image-20231113183607968](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/2023/image-20231113183607968.png)

资源包大小（MB）：

![image-20231113195227057](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/2023/image-20231113195227057.png)

## 5，证书

Apache License 2.0

## 6，关联知识

![image-20231115105032280](https://mufasa-blog-images.oss-cn-beijing.aliyuncs.com/2023/image-20231115105032280.png)

1. java
    1. 原子类
    2. GraalVM-native-image
    3. AOT、JIT
2. Kubernetes
    1. Stateful
    2. 健康检查探针
    3. helm
    4. docker build
3. 雪花分布式id算法
    1. 时间回溯
    2. 自增性

## 7，参考链接

1. [源代码githu地址](https://github.com/mufasa007/uid-service.git)
2. [dockerhub镜像地址](https://hub.docker.com/r/activeclub/uid-service)
3. [Seata改进型雪花分布式ID算法-java实现](https://www.cnblogs.com/Mufasa/p/16090888.html)
4. [java算法(4)---静态内部类实现雪花算法](https://www.cnblogs.com/qdhxhz/p/11372658.html)
5. [Seata基于改良版雪花算法的分布式UUID生成器分析](https://seata.io/zh-cn/blog/seata-analysis-UUID-generator.html)
6. [关于新版雪花算法的答疑](https://seata.io/zh-cn/blog/seata-snowflake-explain.html)
7. [**Leaf** 美团点评分布式ID生成系统](https://www.oschina.net/p/mt-leaf)
8. [JIT与AOT](https://blog.csdn.net/wdays83892469/article/details/126216765)
9. [JIT Performance: Ahead-Of-Time versus Just-In-Time](https://www.azul.com/blog/jit-performance-ahead-of-time-versus-just-in-time/)
10. [语义化版本 2.0.0](https://semver.org/lang/zh-CN/)
11. [配置存活、就绪和启动探针](https://kubernetes.io/zh-cn/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/)
