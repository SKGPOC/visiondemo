package com.oup.visiondemo.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer://foo?fixedRate=true&period=10000")
                .log(LoggingLevel.INFO, log, "Hello World!!!!")
                .to("https://api.coronavirus.data.gov.uk/generic/area/region?proxyAuthHost=ouparray.oup.com&proxyAuthPort=8080")
                .split().jsonpath("$").streaming()
                .split().jsonpath("$.areaCode")
                .setHeader("URI", simple("https://api.coronavirus.data.gov.uk/generic/soa/region/${body}/newCasesBySpecimenDate?httpMethod=GET&proxyAuthHost=ouparray.oup.com&proxyAuthPort=8080&throwExceptionOnFailure=true"))
                .log(LoggingLevel.INFO, log, "${headers.URI}")
                .toD("${headers.URI}")
                .log(LoggingLevel.INFO, log, "${body}");


    }
}
