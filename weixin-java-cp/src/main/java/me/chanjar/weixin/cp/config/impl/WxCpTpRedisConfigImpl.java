package me.chanjar.weixin.cp.config.impl;

import lombok.NonNull;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.redis.RedissonWxRedisOps;
import me.chanjar.weixin.common.redis.WxRedisOps;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class WxCpTpRedisConfigImpl extends WxCpTpDefaultConfigImpl {

    protected final static String LOCK_KEY = "wechat_cp_tp_lock:";
    protected final static String CP_SUITE_ACCESS_TOKEN_KEY = "wechat_cp_tp_suite_access_token_key:";
    protected final static String CP_SUITE_TICKET_KEY = "wechat_cp_tp_suite_ticket_key:";
    protected final static String CP_SUITE_JSAPI_TICKET_KEY = "wechat_cp_tp_suite_jsapi_ticket_key:";

    /**
     * redis 存储的 key 的前缀，可为空
     */
    protected String keyPrefix;
    protected String suiteAccessTokenKey;
    protected String suiteTicketKey;
    protected String agentJsapiTicketKey;
    protected String lockKey;

    private final WxRedisOps redisOps;


    public WxCpTpRedisConfigImpl(WxRedisOps redisOps) {
        this.redisOps = redisOps;
    }


    public WxCpTpRedisConfigImpl(@NonNull RedissonClient redissonClient, String keyPrefix) {
        this(new RedissonWxRedisOps(redissonClient), keyPrefix);
    }

    public WxCpTpRedisConfigImpl(@NonNull RedissonClient redissonClient) {
        this(redissonClient, null);
    }

    private WxCpTpRedisConfigImpl(@NonNull WxRedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }

    @Override
    public void setSuiteId(String suiteId) {
        super.setSuiteId(suiteId);
        String ukey = getCorpId().concat(":").concat(String.valueOf(suiteId));
        String prefix = StringUtils.isBlank(keyPrefix) ? "" : (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
        lockKey = prefix + LOCK_KEY.concat(ukey);
        suiteAccessTokenKey = prefix + CP_SUITE_ACCESS_TOKEN_KEY.concat(ukey);
        suiteTicketKey = prefix + CP_SUITE_TICKET_KEY.concat(ukey);
        agentJsapiTicketKey = prefix + CP_SUITE_JSAPI_TICKET_KEY.concat(ukey);
    }

    @Override
    public String getSuiteAccessToken() {
        return redisOps.getValue(this.suiteAccessTokenKey);
    }

    @Override
    public void setSuiteAccessToken(String suiteAccessToken) {
        redisOps.setValue(this.suiteAccessTokenKey,suiteAccessToken,9000, TimeUnit.SECONDS);
    }

    @Override
    public boolean isSuiteAccessTokenExpired() {
        Long expire = redisOps.getExpire(this.suiteAccessTokenKey);
        return expire == null || expire < 2;
    }

    @Override
    public void expireSuiteAccessToken() {
        redisOps.expire(this.suiteAccessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public synchronized void updateSuiteAccessToken(WxAccessToken suiteAccessToken) {
        redisOps.setValue(this.suiteAccessTokenKey, suiteAccessToken.getAccessToken(), suiteAccessToken.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public synchronized void updateSuiteAccessToken(String suiteAccessToken, int expiresInSeconds) {
        redisOps.setValue(this.suiteAccessTokenKey,suiteAccessToken,expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String getSuiteTicket() {
        return redisOps.getValue(this.suiteTicketKey);
    }

    @Override
    public void setSuiteTicket(String suiteTicket) {
        redisOps.setValue(this.suiteTicketKey,suiteTicket,9000, TimeUnit.SECONDS);
    }

    @Override
    public long getSuiteTicketExpiresTime() {
        Long expire = redisOps.getExpire(this.suiteTicketKey);
        return expire;
    }

    @Override
    public void setSuiteTicketExpiresTime(long suiteTicketExpiresTime) {
        redisOps.expire(this.suiteAccessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public boolean isSuiteTicketExpired() {
        Long expire = redisOps.getExpire(this.suiteTicketKey);
        return expire == null || expire < 2;
    }

    @Override
    public synchronized void updateSuiteTicket(String suiteTicket, int expiresInSeconds) {
        redisOps.setValue(this.suiteTicketKey,suiteTicket,expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireSuiteTicket() {
        redisOps.expire(this.suiteAccessTokenKey, 0, TimeUnit.SECONDS);
    }
}
