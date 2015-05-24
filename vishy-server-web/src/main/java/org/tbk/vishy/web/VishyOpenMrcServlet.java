package org.tbk.vishy.web;


import org.tbk.openmrc.core.client.OpenMrcClientList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class VishyOpenMrcServlet extends HttpServlet {

    private final OpenMrcClientList clients;

    public VishyOpenMrcServlet(OpenMrcClientList clients) {
        super();
        this.clients = Objects.requireNonNull(clients);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "");
    }
}
