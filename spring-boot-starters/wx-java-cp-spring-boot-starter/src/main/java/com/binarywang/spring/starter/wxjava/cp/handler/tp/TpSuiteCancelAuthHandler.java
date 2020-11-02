package com.binarywang.spring.starter.wxjava.cp.handler.tp;

import com.binarywang.spring.starter.wxjava.cp.handler.AbstractTpHandler;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.bean.message.WxCpTpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@Component
public class TpSuiteCancelAuthHandler extends AbstractTpHandler {


  @Override
  public WxCpXmlOutMessage handle(WxCpTpXmlMessage wxMessage, Map<String, Object> context, WxCpTpService service,
                                  WxSessionManager sessionManager) {

    logger.info("\n收到应用取消授权事件，内容：" + wxMessage.getAllFieldsMap());
    return null;
  }

}
