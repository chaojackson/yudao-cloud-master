package cn.iocoder.yudao.module.system.mq.producer.sms;

import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.framework.mq.core.bus.AbstractBusProducer;
import cn.iocoder.yudao.module.system.mq.message.sms.SmsChannelRefreshMessage;
import cn.iocoder.yudao.module.system.mq.message.sms.SmsSendMessage;
import cn.iocoder.yudao.module.system.mq.message.sms.SmsTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sms 短信相关消息的 Producer
 *   核心是使用 StreamBridge 发送 SmsSendMessage 消息。
 * @author zyc
 * @date 2021/3/9 16:35
 */
@Slf4j
@Component
public class SmsProducer extends AbstractBusProducer {

    @Resource
    private StreamBridge streamBridge;

    /**
     * 发送 只广播给自己服务的实例  消息
     */
    public void sendSmsChannelRefreshMessage() {
        publishEvent(new SmsChannelRefreshMessage(this, getBusId(), selfDestinationService()));
    }

    /**
     * 发送 {@link SmsTemplateRefreshMessage} 消息
     */
    public void sendSmsTemplateRefreshMessage() {
        /**
         * 和 Spring 事件机制一样，通过 ApplicationEventPublisher 的 #publishEvent(event) 方法，
         * 直接发送事件到 Spring Cloud Bus 消息总线
         **/
        publishEvent(new SmsTemplateRefreshMessage(this, getBusId(), selfDestinationService()));
    }


    public void sendSmsTemplateRefreshMessage1(Long logId, String mobile, Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        /**
         * 和 Spring 事件机制一样，通过 ApplicationEventPublisher 的 #publishEvent(event) 方法，
         * 直接发送事件到 Spring Cloud Bus 消息总线
         **/
        log.info("SpringCloudBus 发送消息===============================>>>>>>");
        SmsSendMessage message = new SmsSendMessage().setLogId(logId).setMobile(mobile);
        message.setChannelId(channelId).setApiTemplateId(apiTemplateId).setTemplateParams(templateParams);
        publishEvent(new SmsTemplateRefreshMessage(this, getBusId(), selfDestinationService(),message));


    }

    /**
     * 发送 {@link SmsSendMessage} 消息
     *
     * @param logId 短信日志编号
     * @param mobile 手机号
     * @param channelId 渠道编号
     * @param apiTemplateId 短信模板编号
     * @param templateParams 短信模板参数
     */
    public void sendSmsSendMessage(Long logId, String mobile, Long channelId, String apiTemplateId, List<KeyValue<String, Object>> templateParams) {
        SmsSendMessage message = new SmsSendMessage().setLogId(logId).setMobile(mobile);
        message.setChannelId(channelId).setApiTemplateId(apiTemplateId).setTemplateParams(templateParams);
        streamBridge.send("smsSend-out-0", message);
    }

}
