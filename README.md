# 云原生雪花改进型分布式id服务



## 1，概述

​	



## 2，用法



### 2.1，jar程序

```sh
java -jar app.jar
```

备注：使用jdk21



### 2.2，Docker

```sh
docker run -t
```





### 2.3，K8s

#### 	2.3.1，Deployment.yaml部署



#### 	2.3.2，Stateful.yaml部署



#### 	2.3.3，Helm部署





## 3，底层逻辑

1. k8s-stateful部署时，运行程序的主机名称格式为(xxx-n)，其中n为1~m的不重复数值，m<=n，n为节点个数；因此程序使用主机名称的节点后缀设置为workId
2. 



## 4，证书





## 5，参考链接

1. [Seata改进型雪花分布式ID算法-java实现](https://www.cnblogs.com/Mufasa/p/16090888.html)
2. [java算法(4)---静态内部类实现雪花算法](https://www.cnblogs.com/qdhxhz/p/11372658.html)
3. [Seata基于改良版雪花算法的分布式UUID生成器分析](https://seata.io/zh-cn/blog/seata-analysis-UUID-generator.html)
4. [关于新版雪花算法的答疑](https://seata.io/zh-cn/blog/seata-snowflake-explain.html)
5. [**Leaf** 美团点评分布式ID生成系统](https://www.oschina.net/p/mt-leaf)











