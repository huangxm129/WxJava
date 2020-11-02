package com.binarywang.spring.starter.wxjava.cp.config;

import com.binarywang.spring.starter.wxjava.cp.handler.tp.*;
import com.binarywang.spring.starter.wxjava.cp.properties.WxCpTpProperties;
import com.google.common.collect.Maps;
import lombok.val;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpCorpServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceOnTpImpl;
import me.chanjar.weixin.cp.config.impl.WxCpCorpRedisConfigImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.config.impl.WxCpTpRedisConfigImpl;
import me.chanjar.weixin.cp.tp.constant.WxCpTpConsts;
import me.chanjar.weixin.cp.tp.message.WxCpTpMessageRouter;
import me.chanjar.weixin.cp.tp.service.WxCpCorpService;
import me.chanjar.weixin.cp.tp.service.WxCpTpService;
import me.chanjar.weixin.cp.tp.service.impl.WxCpTpServiceImpl;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@EnableConfigurationProperties(WxCpTpProperties.class)
public class WxCpTpConfiguration {

    private final TpLogHandler logHandler;
    private final TpExternalContactHandler tpExternalContactHandler;
    private final TpUserHandler tpUserHandler;
    private final TpNullHandler nullHandler;

    //应用事件
    private final TpSuiteCancelAuthHandler tpSuiteCancelAuthHandler;
    private final TpSuiteChangeAuthHandler tpSuiteChangeAuthHandler;
    private final TpSuiteCreateAuthHandler tpSuiteCreateAuthHandler;

    private WxCpTpProperties properties;
    private static WxCpCorpService wxCpCorpService;
    private final RedissonClient redisson;
    private static Map<String, WxCpTpMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxCpTpService> cpTpServices = Maps.newHashMap();

    public WxCpTpConfiguration(TpLogHandler logHandler, TpExternalContactHandler tpExternalContactHandler, TpUserHandler tpUserHandler, TpNullHandler nullHandler, TpSuiteCancelAuthHandler tpSuiteCancelAuthHandler, TpSuiteChangeAuthHandler tpSuiteChangeAuthHandler, TpSuiteCreateAuthHandler tpSuiteCreateAuthHandler, WxCpTpProperties properties, RedissonClient redisson) {
        this.logHandler = logHandler;
        this.tpExternalContactHandler = tpExternalContactHandler;
        this.tpUserHandler = tpUserHandler;
        this.nullHandler = nullHandler;
        this.tpSuiteCancelAuthHandler = tpSuiteCancelAuthHandler;
        this.tpSuiteChangeAuthHandler = tpSuiteChangeAuthHandler;
        this.tpSuiteCreateAuthHandler = tpSuiteCreateAuthHandler;
        this.properties = properties;
        this.redisson = redisson;
    }


    public static WxCpTpService getCpTpService(String suiteId) {

        return cpTpServices.get(suiteId);
    }

    public static WxCpCorpService getCpCorpService(){

        return wxCpCorpService;
    }

    public static WxCpService getCpService(String suiteId, String corpId, String corpSecret){

        WxCpTpService wxCpTpService = cpTpServices.get(suiteId);
        WxCpService wxCpService = new WxCpServiceOnTpImpl(wxCpTpService);
        val configStorage = new WxCpDefaultConfigImpl();
        configStorage.setCorpId(corpId);
        configStorage.setCorpSecret(corpSecret);
        wxCpService.setWxCpConfigStorage(configStorage);

        return wxCpService;
    }



    public static Map<String, WxCpTpMessageRouter> getRouters() {
        return routers;
    }


    @PostConstruct
    public void initServices() {

        val corpConfigStorage = new WxCpCorpRedisConfigImpl(redisson);
        corpConfigStorage.setCorpId(properties.getCorpId());
        corpConfigStorage.setProviderSecret(properties.getProviderSecret());

        wxCpCorpService = new WxCpCorpServiceImpl();
        wxCpCorpService.setWxCpCorpConfigStorage(corpConfigStorage);

        cpTpServices = this.properties.getSuites().stream().map(a -> {
            val configStorage = new WxCpTpRedisConfigImpl(redisson);
            configStorage.setCorpId(this.properties.getCorpId());
            configStorage.setSuiteId(a.getSuiteId());
            configStorage.setSuiteSecret(a.getSuiteSecret());
            configStorage.setToken(a.getToken());
            configStorage.setAesKey(a.getAesKey());
            configStorage.setOauth2redirectUri(a.getRedirectUri());
            val service = new WxCpTpServiceImpl();
            service.setWxCpTpConfigStorage(configStorage);

            routers.put(a.getSuiteId(), this.newRouter(service));
            return service;
        }).collect(Collectors.toMap(service -> service.getWxCpTpConfigStorage().getSuiteId(), a -> a));

    }

    private WxCpTpMessageRouter newRouter(WxCpTpService wxCpTpService) {
        final val newRouter = new WxCpTpMessageRouter(wxCpTpService);

        newRouter.rule().handler(this.logHandler).next();

        //创建授权事件
        newRouter.rule().async(false).infoType(WxCpTpConsts.InfoType.CREATE_AUTH).handler(this.tpSuiteCreateAuthHandler).end();

        //取消授权事件
        newRouter.rule().async(false).infoType(WxCpTpConsts.InfoType.CANCEL_AUTH).handler(this.tpSuiteCancelAuthHandler).end();

        //变更授权事件
        newRouter.rule().async(false).infoType(WxCpTpConsts.InfoType.CHANGE_AUTH).handler(this.tpSuiteChangeAuthHandler).end();

        //外部联系人相关事件
        newRouter.rule().async(false).infoType(WxCpTpConsts.InfoType.CHANGE_EXTERNAL_CONTACT).handler(this.tpExternalContactHandler).end();

        //内部人员相关事件
        newRouter.rule().async(false).infoType(WxCpTpConsts.InfoType.CHANGE_CONTACT).handler(this.tpUserHandler).end();
        ;

        return newRouter;
    }


}
