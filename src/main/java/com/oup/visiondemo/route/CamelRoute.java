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
                .log(LoggingLevel.INFO,log,"Hello World!!!!")
                .to("http4://api.coronavirus.data.gov.uk/generic/metrics?category=Cases")
                .log(LoggingLevel.INFO,log,"${body}");


    }
}
