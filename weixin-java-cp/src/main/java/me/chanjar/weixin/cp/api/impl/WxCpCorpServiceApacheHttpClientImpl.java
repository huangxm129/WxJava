package me.chanjar.weixin.cp.api.impl;


import com.google.gson.JsonObject;
import me.chanjar.weixin.common.enums.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.HttpType;
import me.chanjar.weixin.common.util.http.apache.ApacheHttpClientBuilder;
import me.chanjar.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import me.chanjar.weixin.common.util.json.GsonParser;
import me.chanjar.weixin.cp.config.WxCpCorpConfigStorage;
import me.chanjar.weixin.cp.constant.WxCpApiPathConsts;
import me.chanjar.weixin.cp.tp.service.impl.BaseWxCpCorpServiceImpl;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * @author someone
 */
public class WxCpCorpServiceApacheHttpClientImpl extends BaseWxCpCorpServiceImpl<CloseableHttpClient, HttpHost> {
  private CloseableHttpClient httpClient;
  private HttpHost httpProxy;

  @Override
  public CloseableHttpClient getRequestHttpClient() {
    return httpClient;
  }

  @Override
  public HttpHost getRequestHttpProxy() {
    return httpProxy;
  }

  @Override
  public HttpType getRequestType() {
    return HttpType.APACHE_HTTP;
  }


  @Override
  public String getProviderToken(boolean forceRefresh) throws WxErrorException {

    if (!this.configStorage.isProviderTokenExpired() && !forceRefresh) {
      return this.configStorage.getProviderToken();
    }

    synchronized (this.globalProviderTokenRefreshLock) {
      try {
        HttpPost httpPost = new HttpPost(configStorage.getApiUrl(WxCpApiPathConsts.Tp.GET_PROVIDER_TOKEN));
        if (this.httpProxy != null) {
          RequestConfig config = RequestConfig.custom()
            .setProxy(this.httpProxy).build();
          httpPost.setConfig(config);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("corpid", this.configStorage.getCorpId());
        jsonObject.addProperty("provider_secret", this.configStorage.getProviderSecret());
        StringEntity entity = new StringEntity(jsonObject.toString(), Consts.UTF_8);
        httpPost.setEntity(entity);

        String resultContent;
        try (CloseableHttpClient httpclient = getRequestHttpClient();
             CloseableHttpResponse response = httpclient.execute(httpPost)) {
          resultContent = new BasicResponseHandler().handleResponse(response);
        } finally {
          httpPost.releaseConnection();
        }
        WxError error = WxError.fromJson(resultContent, WxType.CP);
        if (error.getErrorCode() != 0) {
          throw new WxErrorException(error);
        }
        jsonObject = GsonParser.parse(resultContent);
        String providerAccessToken = jsonObject.get("provider_access_token").getAsString();
        Integer expiresIn = jsonObject.get("expires_in").getAsInt();
        this.configStorage.updateProviderToken(providerAccessToken,expiresIn);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return this.configStorage.getProviderToken();
  }


  @Override
  public void initHttp() {
    ApacheHttpClientBuilder apacheHttpClientBuilder = this.configStorage.getApacheHttpClientBuilder();
    if (null == apacheHttpClientBuilder) {
      apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
    }

    apacheHttpClientBuilder.httpProxyHost(this.configStorage.getHttpProxyHost())
      .httpProxyPort(this.configStorage.getHttpProxyPort())
      .httpProxyUsername(this.configStorage.getHttpProxyUsername())
      .httpProxyPassword(this.configStorage.getHttpProxyPassword());

    if (this.configStorage.getHttpProxyHost() != null && this.configStorage.getHttpProxyPort() > 0) {
      this.httpProxy = new HttpHost(this.configStorage.getHttpProxyHost(), this.configStorage.getHttpProxyPort());
    }

    this.httpClient = apacheHttpClientBuilder.build();
  }

  @Override
  public WxCpCorpConfigStorage getWxCpCorpConfigStorage() {
    return this.configStorage;
  }

}
