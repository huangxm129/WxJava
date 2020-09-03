package me.chanjar.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 获取登录用户信息
 * @author huangxiaoming
 */
@Data
public class WxCpTpLoginInfo extends WxCpBaseResp {

  private static final long serialVersionUID = -5028321625140879571L;

  @SerializedName("usertype")
  private Integer userType;

  @SerializedName("user_info")
  private UserInfo userInfo;

  @SerializedName("corp_info")
  private CorpInfo corpInfo;

  @SerializedName("agent")
  private List<Agent> agents;

  @SerializedName("auth_info")
  private AuthInfo authInfo;

  @Getter
  @Setter
  public static class UserInfo {
    @SerializedName("userid")
    private String userid;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;
  }

  @Getter
  @Setter
  public static class CorpInfo {
    @SerializedName("corpid")
    private String corpId;
  }

  @Getter
  @Setter
  public static class Agent {

    @SerializedName("agentid")
    private Integer agentId;

    @SerializedName("auth_type")
    private Integer authType;
  }

  @Getter
  @Setter
  public static class AuthInfo {

    @SerializedName("department")
    private List<department> departments;

    @Getter
    @Setter
    public static class department {

      @SerializedName("id")
      private Integer id;

      @SerializedName("writable")
      private Boolean writable;

    }
  }

  public static WxCpTpLoginInfo fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpLoginInfo.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
