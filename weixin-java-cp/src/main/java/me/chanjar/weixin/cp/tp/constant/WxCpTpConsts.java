package me.chanjar.weixin.cp.tp.constant;

import lombok.experimental.UtilityClass;

/**
 * <pre>
 * 企业微信第三方应用常量
 * Created by xiaoming huang on 2020/10/16.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@UtilityClass
public class WxCpTpConsts {
  /**
   * 企业微信端推送过来的事件类型.
   * 参考文档：https://work.weixin.qq.com/api/doc/10982
   */
  @UtilityClass
  public static class InfoType {
    /**
     * 授权成功通知.
     */
    public static final String CREATE_AUTH = "create_auth";

    /**
     * 变更授权通知
     */
    public static final String CHANGE_AUTH = "change_auth";

    /**
     * 取消授权通知
     */
    public static final String CANCEL_AUTH = "cancel_auth";

    /**
     * 成员事件通知
     */
    public static final String CHANGE_CONTACT = "change_contact";

    /**
     * 推送suite_ticket
     */
    public static final String SUITE_TICKET = "suite_ticket";

    /**
     * 外部联系人事件
     */
    public static final String CHANGE_EXTERNAL_CONTACT = "change_external_contact";

  }
}
