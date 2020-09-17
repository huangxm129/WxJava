package me.chanjar.weixin.cp.config;

import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.cp.bean.WxCpProviderToken;

import java.io.File;

/**
 * 微信客户端（第三方应用）配置存储
 *
 * @author zhenjun cai
 */
public interface WxCpCorpConfigStorage {

  /**
   * 设置企业微信服务器 baseUrl.
   * 默认值是 https://qyapi.weixin.qq.com , 如果使用默认值，则不需要调用 setBaseApiUrl
   *
   * @param baseUrl 企业微信服务器 Url
   */
  void setBaseApiUrl(String baseUrl);

  /**
   * 读取企业微信 API Url.
   * 支持私有化企业微信服务器.
   */
  String getApiUrl(String path);

  String getCorpId();

  String getProviderSecret();

  String getToken();

  String getAesKey();

  String getHttpProxyHost();

  int getHttpProxyPort();

  String getHttpProxyUsername();

  String getHttpProxyPassword();

  public File getTmpDirFile();

  /**
   * http client builder.
   *
   * @return ApacheHttpClientBuilder
   */
  ApacheHttpClientBuilder getApacheHttpClientBuilder();

  /**
   * 是否自动刷新token
   * @return .
   */
  boolean autoRefreshToken();

  /**
   * 服务商的token
   * @return
   */
  String getProviderToken();

  boolean isProviderTokenExpired();

  void expireProviderToken();

  void updateProviderToken(WxCpProviderToken wxCpProviderToken);

  void updateProviderToken(String providerAccessToken, int expiresIn);

}
