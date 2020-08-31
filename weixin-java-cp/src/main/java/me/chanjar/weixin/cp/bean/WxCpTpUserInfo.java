package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;

/**
 * 获取访问用户身份
 * @author huangxiaoming
 */
@Data
public class WxCpTpUserInfo implements Serializable {

  private static final long serialVersionUID = -5028321625140879571L;
  @SerializedName("corpid")
  private String corpId;

  @SerializedName("userid")
  private String userId;

  @SerializedName("name")
  private String name;

  @SerializedName("gender")
  private String gender;

  @SerializedName("avatar")
  private String avatar;

  @SerializedName("qr_code")
  private String qrCode;

  public static WxCpTpUserInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpUserInfo.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
