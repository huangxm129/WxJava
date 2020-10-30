package me.chanjar.weixin.cp.bean.message;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 被动回复消息.
 * https://work.weixin.qq.com/api/doc#12975
 *
 * @author huangxiaoming
 */
@XStreamAlias("xml")
@Data
public class WxCpTpXmlOutMessage extends WxCpXmlOutMessage{


}
