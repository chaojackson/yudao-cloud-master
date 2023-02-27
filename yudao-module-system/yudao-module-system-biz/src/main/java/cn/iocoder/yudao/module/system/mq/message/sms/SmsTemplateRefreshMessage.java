package cn.iocoder.yudao.module.system.mq.message.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 短信模板的数据刷新 Message
 *
 * @author zyc
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsTemplateRefreshMessage extends RemoteApplicationEvent {

    private  SmsSendMessage smsSendMessage;

    public SmsTemplateRefreshMessage() {
    }

    /**
     * @description: SmsTemplateRefreshMessage
     * @author: zyc
     * @date: 2023/2/22 15:34
     * @param: originService
     *           通过 ServiceMatcher#getServiceId() 方法，获得服务编号
     * @param: destinationService
     *           我们传入 null 值。RemoteApplicationEvent 会自动转换成 **，表示广播给所有监听该消息的实例
     */
    public SmsTemplateRefreshMessage(Object source, String originService, String destinationService) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
    }

    public SmsTemplateRefreshMessage(Object source, String originService, String destinationService,SmsSendMessage smsSendMessage) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.smsSendMessage=smsSendMessage;
    }

}
