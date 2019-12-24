# security
自定义注解权限控制

#
框架采用springboot2.2.0+mybatisPlus+redis+lombok完成基础架构，权限采用拦截器实现，拦截所有的接口，自定义注解跳过拦截，当使用注解后接口可以访问，
但是不能够获取到当前登录用户，会报错。
#
mybatisplus自动生成代码我是写在另外一个项目，生成代码后，虽然需要手动copy代码，但是可以避免代码的覆盖，引起不必要的bug。
#
redis采用redisTemplate完成，RedisUtil工具类中有三个方法是我手动写的，可以删除掉，用在其他项目中，采用静态注入方式，不需要在使用时@Autowired，非常方便
#
这个基础框架 是我自己写着玩儿的，欢迎大家提出一些bug

