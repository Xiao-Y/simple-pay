##[当面付](https://docs.open.alipay.com/194/)

##业务功能
扫码支付，指用户打开支付宝钱包中的“扫一扫”功能，扫描商家展示在某收银场景下的二维码并进行支付的模式。该模式适用于线下实体店支付、面对面支付等场景

##扫码支付使用步骤：
收银员在商家收银系统操作生成支付宝订单，并生成二维码；
用户登录支付宝钱包，点击首页“付钱-扫码付”或直接点击“扫一扫”，进入扫码界面；
用户扫收银员提供的二维码，核对金额，确认支付；
用户付款后商家收银系统会拿到支付成功或者失败的结果。


####1.使用方法：引用对应的 pom
````xml
<!--   支付宝生成二维码用于支付     -->
<dependency>
    <groupId>com.billow</groupId>
    <artifactId>simple-pay-alipay-scan-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
````

####2.添加配置文件
```properties
###### 支付宝配置 #####
alipay:
  scan:
    appId: xxxx
    privateKey: xxxx
    aliPayPublicKey: xxxx
    notifyUrl: xxx
    returnUrl: xxx
    gatewayUrl: https://openapi.alipaydev.com/gateway.do # 沙箱
#    gatewayUrl: https://openapi.alipay.com/gateway.do # 生产
#    charset: UTF-8 #编码格式,默认：UTF-8
#    signType: RSA2 #商户生成签名字符串所使用的签名算法类型,默认：RSA2
```
