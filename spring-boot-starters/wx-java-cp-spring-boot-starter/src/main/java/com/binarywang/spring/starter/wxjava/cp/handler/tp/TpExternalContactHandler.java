package com.binarywang.spring.starter.wxjava.cp.handler.tp;

import com.binarywang.spring.starter.wxjava.cp.handler.AbstractTpHandler;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.bean.message.WxCpTpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 外部联系人事件
 * @author huangxiaoming
 */
@Component
public class TpExternalContactHandler extends AbstractTpHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpTpXmlMessage wxMessage, Map<String, Object> context, WxCpTpService cpService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        logger.info("收到添加外部联系人事件，内容：{}" + wxMessage.getAllFieldsMap());

        return null;
    }
}
