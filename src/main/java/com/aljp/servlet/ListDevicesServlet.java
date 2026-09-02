package com.aljp.servlet;

import com.aljp.model.Device;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ListDevicesServlet extends BaseSpringServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h2>Dispositivos IoT Registrados</h2>");
        out.println("<a href='" + req.getContextPath() + "/devices/add'>Agregar dispositivo</a><br/>");
        out.println("<a href='" + req.getContextPath() + "/devices/state'>Actualizar Estate</a><br/><br/>");

        out.println("<table border='1' cellpadding='6'>");
        out.println("<tr>");
        out.println("<th>ID</th><th>Nombre</th><th>Serial</th><th>Ubicación</th><th>Tipo</th><th>Estate</th>");
        out.println("</tr>");

        for (Device device : deviceService.listDevices()) {
            out.println("<tr>");
            out.println("<td>" + device.getId() + "</td>");
            out.println("<td>" + escapeHtml(device.getName()) + "</td>");
            out.println("<td>" + escapeHtml(device.getSerialNumber()) + "</td>");
            out.println("<td>" + escapeHtml(device.getUbicación()) + "</td>");
            out.println("<td>" + escapeHtml(device.getType()) + "</td>");
            out.println("<td>" + escapeHtml(device.getEstate()) + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
