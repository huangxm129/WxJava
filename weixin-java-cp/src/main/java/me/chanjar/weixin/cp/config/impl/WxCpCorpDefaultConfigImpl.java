package me.chanjar.weixin.cp.config.impl;

import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.cp.bean.WxCpProviderToken;
import me.chanjar.weixin.cp.config.WxCpCorpConfigStorage;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.File;
import java.io.Serializable;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化.
 *
 * @author someone
 */
public class WxCpCorpDefaultConfigImpl implements WxCpCorpConfigStorage, Serializable {
  private static final long serialVersionUID = 6678780920621872824L;

  private volatile String corpId;
  private volatile String providerSecret;

  private volatile String token;
  private volatile String aesKey;

  private volatile String oauth2redirectUri;

  private volatile String httpProxyHost;
  private volatile int httpProxyPort;
  private volatile String httpProxyUsername;
  private volatile String httpProxyPassword;

  private volatile File tmpDirFile;

  private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;

  private volatile String baseApiUrl;

  private volatile String providerToken;

  private volatile long providerTokenExpiresTime;





  @Override
  public void setBaseApiUrl(String baseUrl) {
    this.baseApiUrl = baseUrl;
  }

  @Override
  public String getApiUrl(String path) {
    if (baseApiUrl == null) {
      baseApiUrl = "https://qyapi.weixin.qq.com";
    }
    return baseApiUrl + path;
  }

  @Override
  public String getCorpId() {
    return this.corpId;
  }

  @Override
  public String getProviderSecret() {
    return this.providerSecret;
  }

  public void setProviderSecret(String providerSecret) {
    this.providerSecret = providerSecret;
  }

  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }


  @Override
  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String getAesKey() {
    return this.aesKey;
  }

  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

  public void setOauth2redirectUri(String oauth2redirectUri) {
    this.oauth2redirectUri = oauth2redirectUri;
  }

  @Override
  public String getHttpProxyHost() {
    return this.httpProxyHost;
  }

  public void setHttpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
  }

  @Override
  public int getHttpProxyPort() {
    return this.httpProxyPort;
  }

  public void setHttpProxyPort(int httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
  }

  @Override
  public String getHttpProxyUsername() {
    return this.httpProxyUsername;
  }

  public void setHttpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
  }

  @Override
  public String getHttpProxyPassword() {
    return this.httpProxyPassword;
  }

  public void setHttpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
  }

  @Override
  public String toString() {
    return WxCpGsonBuilder.create().toJson(this);
  }

  @Override
  public File getTmpDirFile() {
    return this.tmpDirFile;
  }

  public void setTmpDirFile(File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }

  @Override
  public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
    return this.apacheHttpClientBuilder;
  }

  @Override
  public boolean autoRefreshToken() {
    return true;
  }

  @Override
  public String getProviderToken() {
    return this.providerToken;
  }

  @Override
  public boolean isProviderTokenExpired() {
    return System.currentTimeMillis() > this.providerTokenExpiresTime;
  }

  @Override
  public void expireProviderToken() {
    this.providerTokenExpiresTime = 0;
  }

  @Override
  public void updateProviderToken(WxCpProviderToken wxCpProviderToken) {
    updateProviderToken(wxCpProviderToken.getProviderAccessToken(),wxCpProviderToken.getExpiresIn());
  }

  @Override
  public void updateProviderToken(String providerAccessToken, int expiresIn) {
    this.providerToken = providerAccessToken;
    this.providerTokenExpiresTime = expiresIn;
  }

  public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
    this.apacheHttpClientBuilder = apacheHttpClientBuilder;
  }
}
