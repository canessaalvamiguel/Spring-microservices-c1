package com.example.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostTimePassedFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostTimePassedFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context =  RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        log.info("Entering to post filter");

        Long initTime = (Long) request.getAttribute("iniTime");
        Long endTime = System.currentTimeMillis();
        Long timeConsumed = endTime - initTime;

        log.info(String.format("Time passed in seconds %s",timeConsumed.doubleValue()/1000.00));
        log.info(String.format("Time passed in milliseconds %s",timeConsumed.doubleValue()));

        request.setAttribute("iniTime", initTime);

        return null;
    }
}
