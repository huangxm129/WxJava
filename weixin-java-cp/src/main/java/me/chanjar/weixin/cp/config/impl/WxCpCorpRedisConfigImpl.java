package me.chanjar.weixin.cp.config.impl;

import lombok.NonNull;
import me.chanjar.weixin.common.redis.RedissonWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import me.chanjar.weixin.cp.bean.WxCpProviderToken;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;


public class WxCpCorpRedisConfigImpl extends WxCpCorpDefaultConfigImpl {

    protected final static String LOCK_KEY = "wechat_cp_corp_lock:";
    protected final static String CP_CORP_PROVIDER_ACCESS_TOKEN_KEY = "wechat_cp_corp_provider_access_token_key:";


    /**
     * redis 存储的 key 的前缀，可为空
     */
    protected String keyPrefix;
    protected String providerAccessTokenKey;
    protected String lockKey;

    private final WxRedisOps redisOps;


    public WxCpCorpRedisConfigImpl(WxRedisOps redisOps) {
        this.redisOps = redisOps;
    }


    public WxCpCorpRedisConfigImpl(@NonNull RedissonClient redissonClient, String keyPrefix) {
        this(new RedissonWxRedisOps(redissonClient), keyPrefix);
    }

    public WxCpCorpRedisConfigImpl(@NonNull RedissonClient redissonClient) {
        this(redissonClient, null);
    }

    private WxCpCorpRedisConfigImpl(@NonNull WxRedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }


    @Override
    public void setCorpId(String corpId) {
        super.setCorpId(corpId);
        String ukey = getCorpId();
        String prefix = StringUtils.isBlank(keyPrefix) ? "" : (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
        lockKey = prefix + LOCK_KEY.concat(ukey);
        providerAccessTokenKey = prefix + CP_CORP_PROVIDER_ACCESS_TOKEN_KEY.concat(ukey);
    }

    @Override
    public String getProviderToken() {


        return redisOps.getValue(this.providerAccessTokenKey);
    }

    @Override
    public boolean isProviderTokenExpired() {
        Long expire = redisOps.getExpire(this.providerAccessTokenKey);
        return expire == null || expire < 2;
    }

    @Override
    public void expireProviderToken() {
        redisOps.expire(this.providerAccessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateProviderToken(WxCpProviderToken wxCpProviderToken) {
        this.updateProviderToken(wxCpProviderToken.getProviderAccessToken(),wxCpProviderToken.getExpiresIn());
    }

    @Override
    public void updateProviderToken(String providerAccessToken, int expiresIn) {
        redisOps.setValue(this.providerAccessTokenKey, providerAccessToken, expiresIn, TimeUnit.SECONDS);

    }

}
