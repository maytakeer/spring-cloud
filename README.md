# spring-cloud
  ## spring cloud 简介
   spring cloud 为开发人员提供了快速构建分布式系统的一些工具，包括配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等。它运行环境简单，可以在开发人员的电脑上跑。spring cloud是基于springboot的。
    ### Spring-Cloud-f-demo（服务的注册和发现eureka） 
  采用Eureka作为服务注册与发现的组件，eureka-server注册服务中心，service-hi服务提供者
    ### Spring-Cloud-f-ribbon（服务消费者rest+ribbon）
  ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。Feign默认集成了ribbon。  
  启动eureka-server 工程；启动service-hi工程，它的端口为8762；将service-hi的配置文件的端口改为8763,并启动，  
  这时你会发现：service-hi在eureka-server注册了2个实例，这就相当于一个小的集群。  
  启动service-ribbon，多次访问http://localhost:8764/hi?name=forezp 浏览器打印的端口为8763、8762交替。  
  我们通过调用restTemplate.getForObject(“http://SERVICE-HI/hi?name=”+name,String.class) 方法时，已经做了负载均衡，访问了不同的端口的服务实例。
  * 一个服务注册中心，eureka server,端口为8761
  * service-hi工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册
  * sercvice-ribbon端口为8764,向服务注册中心注册
  * 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口
    ### Spring-Cloud-f-feign（服务消费者feign）
  Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。  
  简而言之：
  * Feign 采用的是基于接口的注解
  * Feign 整合了ribbon，具有负载均衡的能力
  * 整合了Hystrix，具有熔断的能力
    ### Spring-Cloud-f-hystrix(断路器hystrix)
  * 启动：service-ribbon 工程，当我们访问http://localhost:8764/hi?name=forezp 浏览器会显示：hi forezp,i am from port:8762.  
    此时关闭 service-hi 工程，当我们再访问http://localhost:8764/hi?name=forezp 浏览器会显示：hi ,forezp,eorry,error!
  * 启动:servcie-feign工程，浏览器打开http://localhost:8765/hi?name=forezp 浏览器会显示：hi forezp,i am from port:8762  
    此时关闭 service-hi 工程，当我们再访问http://localhost:8765/hi?name=forezp 浏览器会显示：sorry forezp
    ### Spring-Cloud-f-zuul（路由网关zuul）
  Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，比如／api/user转发到到user服务，/api/shop转发到到shop服务。zuul默认和Ribbon结合实现了负载均衡的功能。  
  依次运行这五个工程;打开浏览器访问：http://localhost:8769/api-a/hi?name=forezp ;浏览器显示：  
  hi forezp,i am from port:8762  
  打开浏览器访问：http://localhost:8769/api-b/hi?name=forezp ;浏览器显示：  
  hi forezp,i am from port:8762  
    #### 服务过滤
  * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
    + pre：路由之前
    + routing：路由之时
    + post： 路由之后
    + error：发送错误调用
  * filterOrder：过滤的顺序
  * shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
  * run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
    ### Spring-Cloud-f-config(分布式配置中心)
      #### 简介
  在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client。
      #### config-server
        ##### 配置文件
  * spring.cloud.config.server.git.uri：配置git仓库地址
  * spring.cloud.config.server.git.searchPaths：配置仓库路径
  * spring.cloud.config.label：配置仓库的分支
  * spring.cloud.config.server.git.username：访问git仓库的用户名
  * spring.cloud.config.server.git.password：访问git仓库的用户密码  
  如果Git仓库为公开仓库，可以不填写用户名和密码，如果是私有仓库需要填写  
  这里的git仓库地址是https://github.com/forezp/SpringcloudConfig/
        ##### 测试
  启动程序：访问http://localhost:8888/foo/dev  
  
  页面显示：  
  {"name":"foo","profiles":["dev"],"label":"master",
"version":"792ffc77c03f4b138d28e89b576900ac5e01a44b","state":null,"propertySources":[]}  
        ##### http请求地址和资源文件映射如下
  * /{application}/{profile}[/{label}]
  * /{application}-{profile}.yml
  * /{label}/{application}-{profile}.yml
  * /{application}-{profile}.properties
  * /{label}/{application}-{profile}.properties
      #### config client  
        ##### 配置文件
  * spring.cloud.config.label 指明远程仓库的分支

  * spring.cloud.config.profile

    + dev开发环境配置文件
    + test测试环境
    + pro正式环境
  * spring.cloud.config.uri= http://localhost:8888/  指明配置服务中心的网址。  
        ##### 测试
  打开网址访问：http://localhost:8881/hi，网页显示：  

  foo version 2    

  这就说明，config-client从config-server获取了foo的属性，而config-server是从git仓库读取的
  
      #### 说明
  1：客户端的spring.application.name配置config-clent是和Git服务器上面的文件名相对应的，如果你的客户端是其他名字就报错找不到参数。Git上面是有这个config-client-dev的配置文件的所以是config-clent。  
  2：客户端加载到的配置文件的配置项会覆盖本项目已有配置，比如客户端你自己配置的端口是8881，但是如果读取到config-clent-dev这个配置文件中也有配置端口为8882，那么此时客户端访问的地址应该是8882。

  

  


