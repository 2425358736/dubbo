### [先附上github地址](https://github.com/2425358736/dubbo)

#### 创建一个springBoot项目， 并在springBoot中创建三个工程
##### 1. dubbo_consumer（服务消费者）
##### 2. dubbo_provider（服务提供者）
##### 3. dubbo_api（api接口工程，存放统一接口）

#### 目录结构如下

```bash
├─dubbo_api ## api接口工程，存放统一接口
│  └─src
│      └─main
│          └─java
│              └─com
│                  └─api
├─dubbo_consumer ## 服务消费者
│  └─src
│      └─main
│          ├─java
│          │  └─com
│          │      └─consumer
│          │          └─controller
│          └─resources
└─dubbo_provider ## 服务提供者
    └─src
        └─main
            ├─java
            │  └─com
            │      └─provider
            │          └─service
            │              └─impl
            └─resources

```

### 父级pom.xml如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>dubbo</groupId>
    <artifactId>dubbo</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>dubbo_consumer</module>
        <module>dubbo_provider</module>
        <module>dubbo_api</module>
    </modules>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <zookeeper.version>3.4.13</zookeeper.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--引入dubbo环境-->
        <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>0.2.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```
### ==千万不要自作聪明的在引入zookeeper的开发包了==

### 子工程中引入dubbo_api模块

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>dubbo</artifactId>
        <groupId>dubbo</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>dubbo_service</artifactId>

    <dependencies>
        <dependency>
            <groupId>dubbo</groupId>
            <artifactId>dubbo_api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>dubbo</artifactId>
        <groupId>dubbo</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>dubbo_client</artifactId>

    <dependencies>
        <dependency>
            <groupId>dubbo</groupId>
            <artifactId>dubbo_api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 消费者（dubbo_consumer） yml配置

```yml
spring:
  application:
    name: dubbo_consumer
server:
  port: 8181
#指定当前服务的名字（同样的服务名字相同，不要和别的服务同名）
dubbo:
  application:
    name: dubbo_consumer
  protocol:
    name: dubbo
    port: 20880
#指定注册中心的位置
  registry:
    address: zookeeper://zookeeper服务的ip:2181
    check: false
  monitor:
    protocol: register
  consumer:
    check:  false
    timeout: 3000
```

### 服务提供者（dubbo_provider ） yml配置

```yml
spring:
  application:
    name: dubbo_provider
server:
  port: 8080
#指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名）
dubbo:
  application:
    name: dubbo_provider
  protocol:
    name: dubbo
    port: 20880
#指定注册中心的位置
  registry:
    address: zookeeper://zookeeper服务的ip:2181
    check: false
  monitor:
    protocol: register

```

#### 服务提供者和服务消费者的启动类上增加@EnableDubbo注解 来启用dubbo服务

```java
package com.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author lzq
 * @date 2018/10/25
 */
@SpringBootApplication
@EnableDubbo
public class ProviderApplication implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }


    @Override
    public void run(String... strings) {
        logger.info("服务器已起动");
    }

}

```

#### 服务提供者实现类使用com.alibaba.dubbo.config.annotation.Service 的 @Service注解。


```java
package com.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.api.ProviderService;

/**
 * @author lzq
 * @date 2018/10/25
 */
@Service(version = "1.0.0")
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String sayHello() {
        return "你好 dubbo";
    }
}


```
#### 消费者调用接口使用 @Reference 来订阅接口

```java
package com.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.ProviderService;

/**
 * @author lzq
 * @date 2018/10/25
 */
@RestController
public class ConsumerController {
    /**
     * 订阅消费的接口
     */
    @Reference(version = "1.0.0")
    private ProviderService providerService;

    @RequestMapping("/sayHello")
    String sayHello() {
        return providerService.sayHello();
    }
}

```


# zookeeper 安装
#### 1. 拉取zookeeper镜像

```
docker pull zookeeper 
// 或者
docker pull zookeeper:3.4.14
```
#### 2. 运行镜像创建zookeeper容器

```
mkdir /mnt/zookeeper
mkdir /mnt/zookeeper/conf
mkdir /mnt/zookeeper/data
vim /mnt/zookeeper/conf/zoo.cfg

docker run -v /mnt/zookeeper/conf:/zookeeper-3.4.14/conf -v /mnt/zookeeper/data:/zookeeper-3.4.14/data -v /etc/localtime:/etc/localtime  -d -p 2181:2181 --name my-zookeeper --restart always zookeeper:3.4.14 
```
##### zoo.cfg

```bash
tickTime=1000
initLimit=10
syncLimit=5
dataDir=/data
dataLogDir=/data/log
clientPort=2181
```