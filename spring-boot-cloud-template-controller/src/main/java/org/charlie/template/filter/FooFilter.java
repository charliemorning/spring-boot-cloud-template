package org.charlie.template.filter;

import lombok.extern.slf4j.Slf4j;
import org.charlie.template.init.FooInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


@Component
@WebServlet(urlPatterns = "/**")
@Slf4j
public class FooFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Filter.init.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Filter.doFilter.");
    }

    @Override
    public void destroy() {
        log.info("Filter.destroy.");
    }
}
