package com.aljp.servlet;

import com.aljp.service.DeviceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class BaseSpringServlet extends HttpServlet {

    protected DeviceService deviceService;

    @Override
    public void init() throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.deviceService = context.getBean(DeviceService.class);
    }
}
