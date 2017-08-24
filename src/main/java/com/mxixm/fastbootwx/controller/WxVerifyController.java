package com.mxixm.fastbootwx.controller;

import com.mxixm.fastbootwx.config.invoker.WxVerifyProperties;
import com.mxixm.fastbootwx.util.CryptUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FastBootWeixin  /
 * 之后可以改造为endPoint方式
 *
 * @author Guangshan
 * @summary FastBootWeixin  WXVerifyController
 * @Copyright (c) 2017, Guangshan Group All Rights Reserved
 * @since 2017/7/16 23:37
 */
public class WxVerifyController {

    private WxVerifyProperties wxVerifyProperties;

    public WxVerifyController(WxVerifyProperties wxVerifyProperties) {
        this.wxVerifyProperties = wxVerifyProperties;
    }

    @ResponseBody
    public String verify(@RequestParam(value = "signature", required = true) String signature,
                         @RequestParam(value = "timestamp", required = true) String timestamp,
                         @RequestParam(value = "nonce", required = true) String nonce,
                         @RequestParam(value = "echostr", required = true) String echostr) {
        String rawString = Stream.of(wxVerifyProperties.getToken(), timestamp, nonce).sorted().collect(Collectors.joining());
        if (signature.equals(CryptUtils.encryptSHA1(rawString))) {
            return echostr;
        }
        return null;
    }

}
