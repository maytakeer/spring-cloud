# spring-cloud
## spring cloud 简介
  spring cloud 为开发人员提供了快速构建分布式系统的一些工具，包括配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等。它运行环境简单，可以在开发人员的电脑上跑。spring cloud是基于springboot的。
  ### 1. Spring-Cloud-f-demo
  采用Eureka作为服务注册与发现的组件，eureka-server注册服务中心，service-hi服务提供者
  ### 2. Spring-Cloud-f-ribbon
  ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。Feign默认集成了ribbon。
  启动eureka-server 工程；启动service-hi工程，它的端口为8762；将service-hi的配置文件的端口改为8763,并启动，这时你会发现：service-hi在eureka-server注册了2个实例，这就相当于一个小的集群。启动service-ribbon，多次访问http://localhost:8764/hi?name=forezp 浏览器打印的端口为8763、8762交替。我们通过调用restTemplate.getForObject(“http://SERVICE-HI/hi?name=”+name,String.class)方法时，已经做了负载均衡，访问了不同的端口的服务实例。
  * 一个服务注册中心，eureka server,端口为8761
  * service-hi工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册
  * sercvice-ribbon端口为8764,向服务注册中心注册
  * 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口
  ### 3. Spring-Cloud-f-feign
  Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。
  简而言之：
  * Feign 采用的是基于接口的注解
  * Feign 整合了ribbon，具有负载均衡的能力
  * 整合了Hystrix，具有熔断的能力
  ### 4. Spring-Cloud-f-hystrix
  * 启动：service-ribbon 工程，当我们访问http://localhost:8764/hi?name=forezp 浏览器会显示：hi forezp,i am from port:8762.
    此时关闭 service-hi 工程，当我们再访问http://localhost:8764/hi?name=forezp 浏览器会显示：hi ,forezp,eorry,error!
  * 启动四servcie-feign工程，浏览器打开http://localhost:8765/hi?name=forezp 浏览器会显示：hi forezp,i am from port:8762
     此时关闭 service-hi 工程，当我们再访问http://localhost:8765/hi?name=forezp 浏览器会显示：sorry forezp
