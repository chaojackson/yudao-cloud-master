package cn.iocoder.yudao.framework.mq.core.bus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.Resource;

/**
 * 基于 Spring Cloud Bus 实现的 Producer 抽象类
 *   学习 SpringCloudBus 案例：
 *       I:\1.Java\0.java笔记\32.框架\2.SpringSecurity\SpringBoot-Labs-master\labx-20\labx-20-sca-bus-rocketmq-demo-listener
 *       I:\1.Java\0.java笔记\32.框架\2.SpringSecurity\SpringBoot-Labs-master\labx-20\labx-20-sca-bus-rocketmq-demo-publisher
 * @author zyc
 */
public abstract class AbstractBusProducer {

    @Resource
    protected ApplicationEventPublisher applicationEventPublisher;

    @Resource
    protected ServiceMatcher serviceMatcher;

    @Value("{spring.application.name}")
    protected String applicationName;

    /**
     * 和 Spring 事件机制一样，通过 ApplicationEventPublisher 的 #publishEvent(event) 方法，
     * 直接发送事件到 Spring Cloud Bus 消息总线
     *
     * RemoteApplicationEvent event的属性参数
     *   (Object source, String originService, String destinationService)
     *
     *   originService
     *                 通过 ServiceMatcher#getServiceId() 方法，获得服务编号
     *   destinationService
     *                   目标服务。该属性的格式是 {服务名}:{服务实例编号}
     *                   我们传入 null 值。RemoteApplicationEvent 会自动转换成 **，表示广播给所有监听该消息的实例
     *                   举个例子：
     *                          如果想要广播给所有服务的所有实例，则设置为 **:**。
     *                          如果想要广播给 users 服务的所有实例，则设置为 users:**。
     *                          如如果想要广播给 users 服务的指定实例，则设置为 users:bc6d27d7-dc0f-4386-81fc-0b3363263a15
     *
     **/
    protected void publishEvent(RemoteApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * @return 只广播给自己服务的实例
     */
    protected String selfDestinationService() {
        return applicationName + ":**";
    }

    protected String getBusId() {
        return serviceMatcher.getBusId();
    }

}
