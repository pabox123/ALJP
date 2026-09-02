package com.aljp.servlet;

import com.aljp.model.Device;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class AddDeviceServlet extends BaseSpringServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        renderForm(resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        try {
            Integer id = parseOptionalInteger(req.getParameter("id"));
            String name = req.getParameter("name");
            String serialNumber = req.getParameter("serialNumber");
            String ubicacion = req.getParameter("ubicacion");
            String type = req.getParameter("type");
            String estate = req.getParameter("estate");

            Device newDevice = new Device(id, name, serialNumber, ubicacion, type, estate);
            Device createdDevice = deviceService.registerDevice(newDevice);

            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            out.println("<h2>Dispositivo registrado con éxito</h2>");
            out.println("<p>ID: " + createdDevice.getId() + "</p>");
            out.println("<p>Nombre: " + createdDevice.getName() + "</p>");
            out.println("<a href='" + req.getContextPath() + "/devices'>Ver dispositivos</a>");
            out.println("</body></html>");
        } catch (IllegalArgumentException e) {
            renderForm(resp, e.getMessage());
        }
    }

    private Integer parseOptionalInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }

    private void renderForm(HttpServletResponse resp, String errorMessage) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Agregar Dispositivo IoT</h2>");
        if (errorMessage != null) {
            out.println("<p style='color:red;'>" + errorMessage + "</p>");
        }
        out.println("<form method='post'>");
        out.println("ID (opcional): <input type='number' name='id'/><br/><br/>");
        out.println("Nombre: <input type='text' name='name' required/><br/><br/>");
        out.println("Serial Number: <input type='text' name='serialNumber' required/><br/><br/>");
        out.println("Ubicación: <input type='text' name='ubicacion' required/><br/><br/>");
        out.println("Tipo: <input type='text' name='type' required/><br/><br/>");
        out.println("Estate: <select name='estate'>");
        out.println("<option value='ACTIVE'>ACTIVE</option>");
        out.println("<option value='INACTIVE'>INACTIVE</option>");
        out.println("</select><br/><br/>");
        out.println("<button type='submit'>Registrar</button>");
        out.println("</form>");
        out.println("<br/><a href='devices'>Ver dispositivos</a>");
        out.println("</body></html>");
    }
}
