# spring-cloud
## spring cloud 简介
  spring cloud 为开发人员提供了快速构建分布式系统的一些工具，包括配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等。它运行环境简单，可以在开发人员的电脑上跑。spring cloud是基于springboot的。
  1. Spring-Cloud-f-demo
  采用Eureka作为服务注册与发现的组件，eureka-server注册服务中心，service-hi服务提供者
  2. Spring-Cloud-f-ribbon
  ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。Feign默认集成了ribbon。
  启动eureka-server 工程；启动service-hi工程，它的端口为8762；将service-hi的配置文件的端口改为8763,并启动，这时你会发现：service-hi在eureka-server注册了2个实例，这就相当于一个小的集群。启动service-ribbon，多次访问http://localhost:8764/hi?name=forezp，浏览器打印的端口为8763、8762交替。我们通过调用restTemplate.getForObject(“http://SERVICE-HI/hi?name=”+name,String.class)方法时，已经做了负载均衡，访问了不同的端口的服务实例。
  此时的架构
  ![https://img-blog.csdnimg.cn/20181229160939874]
