package com.binarywang.spring.starter.wxjava.cp.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Data
@ConfigurationProperties(prefix = "wechat.cp.suite")
public class WxCpTpProperties {
    /**
     * 设置微信企业号的corpId
     */
    private String corpId;

    /**
     * 设置微信企业的Secret
     */
    private String secret;

    /**
     * ProviderSecret
     * */
    private String providerSecret;


    /**
     * 设置微信企业号的token
     */
    private String token;

    /**
     * 设置微信企业号的EncodingAESKey
     */
    private String aesKey;

    /**
     * 系统默认的SuiteId
     */
    private String defaultSuiteId;

    /**
     * 是否提醒管理员
     * */
    private Boolean reminderAdmin;

    @Value(value = "30")
    private Integer suiteDefaultMember;

    private List<SuiteConfig> suites;

    @Getter
    @Setter
    public static class SuiteConfig implements Serializable {

        /**
         * 设置微信企业号的suiteId
         */
        private String suiteId;

        /**
         * 设置微信企业应用的Secret
         */
        private String suiteSecret;

        /**
         * 设置微信企业号的token
         */
        private String token;

        /**
         * 设置微信企业号的EncodingAESKey
         */
        private String aesKey;

        /**
         * 设置微信企业的授权回调URL
         */
        private String redirectUri;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

