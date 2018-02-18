#Rave Java Plugin V1

### What this does
This library allows you to integrate easily with *rave by flutterwave* payment gateway.

**Note:** *This is library is still under development and update will be added as often as we can*

### Dependencies
The following are the dependencies required to use this library

*   org-apache-commons-logging.jar
*   httpcore-4.4.6.jar
*   org.json.jar
*   httpclient-4.5.3.jar


###Before you use
1.  Sign up at [rave by Flutterwave](https://rave.flutterwave.com) to get the required integration parameters
2.  Download the plugin and build as jar
3.  Add the Dependencies to your project
4.  Build and Run your application.


#### Sample Card Charge

```java
RavePaymentHelper helper = new RavePaymentHelper(<secret key here>,
                    <private key here>, <environment>);
            
RaveCard raveCard = new RaveCard();
raveCard.setAmount(<amount>);
raveCard.setAuthModel(< preferred auth model>);
raveCard.setCardNo(< card number>);
raveCard.setCvv(<card cvv here>);
raveCard.setEmail(<customer email here>);
raveCard.setExpiryMonth(< card expiry month>);
raveCard.setExpiryYear(< card expiry year>);
raveCard.setIp(< customer's ip address>);
raveCard.setPin(< pin if the auth model selected is PIN>);

raveCard.setTransactionReference(<transaction reference>);
            
JSONObject response = helper.chargeCard(raveCard);

```
