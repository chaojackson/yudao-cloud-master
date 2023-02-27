package cn.iocoder.yudao.module.system.controller.admin.test;

import cn.iocoder.yudao.module.system.mq.message.sms.SmsTemplateRefreshMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName 使用SpringCloudbus 监听rocketMq的消息推送
 * @Description
 * @Author zyc
 * @Date 2023/2/23 14:16
 */
@Slf4j
@Component
public class SmsSendListener implements ApplicationListener<SmsTemplateRefreshMessage> {

    @Override
    public void onApplicationEvent(SmsTemplateRefreshMessage event) {
        log.info("SpringCloudBus 接收到消息 ==============>>>{}",event.getSmsSendMessage());
    }
}
