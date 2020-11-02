# 使用说明
1. 在自己的Spring Boot项目里，引入maven依赖
```xml
    <dependency>
        <groupId>com.github.binarywang</groupId>
        <artifactId>wx-java-cp-spring-boot-starter</artifactId>
        <version>${version}</version>
    </dependency>
 ```
2. 添加配置(application.yml)
```yml
wechat:
  cp:
    suite:
      corpId: 
      providerSecret: 
      token: 
      aesKey: 
      suites: # 企业第三方标准应用服务中的SuiteID
        - suiteId: 
          suiteSecret: 
          token: 
          aesKey: 
          redirectUri: 
    agent:
      corpId: 
      appConfigs:
        - agentId: 
          secret: 
          token: 
          aesKey: 
```







