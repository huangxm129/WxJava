package com.binarywang.spring.starter.wxjava.cp.handler;

import me.chanjar.weixin.cp.tp.message.WxCpTpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public abstract class AbstractTpHandler implements WxCpTpMessageHandler {

  protected Logger logger = LoggerFactory.getLogger(getClass());

}
