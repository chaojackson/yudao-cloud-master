package cn.iocoder.yudao.module.system.controller.admin.test;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserUpdateReqVO;
import cn.iocoder.yudao.module.system.mq.producer.sms.SmsProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.util.ArrayList;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @ClassName TestController
 * @Description
 * @Author zyc
 * @Date 2023/2/23 10:47
 */
@Api(tags = "测试后台管理系统的api")
@RestController
@RequestMapping("/system/test")
public class TestController {

    @Autowired
    private SmsProducer smsProducer;


    @GetMapping("testSendRocketMq")
    @ApiOperation("测试发送RocketMq信息")
    public CommonResult<Boolean> testSendRocketMq() {

        ArrayList<KeyValue<String, Object>> list=new ArrayList();
        list.add(new KeyValue<>("code", "1234"));
        list.add(new KeyValue<>("op", "login"));
        smsProducer.sendSmsTemplateRefreshMessage1(RandomUtil.randomLong(0, Long.MAX_VALUE), "15922405819",
                1789270883L, "2312372371",
                list);
        return success(true);
    }


}
