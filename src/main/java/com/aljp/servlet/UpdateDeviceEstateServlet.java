package com.aljp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class UpdateDeviceEstateServlet extends BaseSpringServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderForm(resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            String estate = req.getParameter("estate");
            deviceService.updateDeviceEstate(id, estate);

            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            out.println("<h2>Estado actualizado correctamente</h2>");
            out.println("<a href='" + req.getContextPath() + "/devices'>Volver al listado</a>");
            out.println("</body></html>");
        } catch (RuntimeException e) {
            renderForm(resp, "No fue posible actualizar el estado. Verifica los datos.");
        }
    }

    private void renderForm(HttpServletResponse resp, String errorMessage) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Actualizar Estate del Dispositivo</h2>");
        if (errorMessage != null) {
            out.println("<p style='color:red;'>" + escapeHtml(errorMessage) + "</p>");
        }
        out.println("<form method='post'>");
        out.println("ID del dispositivo: <input type='number' name='id' required/><br/><br/>");
        out.println("Estate: <select name='estate'>");
        out.println("<option value='ACTIVE'>ACTIVE</option>");
        out.println("<option value='INACTIVE'>INACTIVE</option>");
        out.println("</select><br/><br/>");
        out.println("<button type='submit'>Actualizar</button>");
        out.println("</form>");
        out.println("<br/><a href='devices'>Ver dispositivos</a>");
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
