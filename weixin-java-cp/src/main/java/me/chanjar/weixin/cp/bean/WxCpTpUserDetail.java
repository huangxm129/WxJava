package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取访问用户敏感信息
 * @author huangxiaoming
 */
@Data
public class WxCpTpUserDetail implements Serializable {

  private static final long serialVersionUID = -5028321625140879571L;
  @SerializedName("CorpId")
  private String corpId;

  @SerializedName("UserId")
  private String userId;

  @SerializedName("DeviceId")
  private String deviceId;

  @SerializedName("user_ticket")
  private String userTicket;

  @SerializedName("expires_in")
  private String expiresIn;

  @SerializedName("open_userid")
  private String openUserId;

  public static WxCpTpUserDetail fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpUserDetail.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
