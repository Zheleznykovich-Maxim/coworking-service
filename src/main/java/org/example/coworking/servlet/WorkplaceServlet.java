package org.example.coworking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coworking.annotations.Loggable;
import org.example.coworking.dto.request.WorkplaceRequestDto;
import org.example.coworking.dto.response.WorkplaceResponseDto;
import org.example.coworking.factory.CoworkingSpaceServiceFactory;
import org.example.coworking.mapper.WorkplaceMapper;
import org.example.coworking.mapper.WorkplaceMapperImpl;
import org.example.coworking.model.Workplace;
import org.example.coworking.service.CoworkingSpaceService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

/**
 * Servlet implementation for handling workplace-related operations.
 */
@Loggable
@WebServlet("/workplace/*")
public class WorkplaceServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final CoworkingSpaceService coworkingSpaceService;
    private final WorkplaceMapper workplaceMapper;

    /**
     * Initializes the WorkplaceServlet.
     *
     * @throws IOException if there is an issue initializing ObjectMapper or CoworkingSpaceServiceFactory.
     */
    public WorkplaceServlet() throws IOException {
        this.workplaceMapper = new WorkplaceMapperImpl();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.coworkingSpaceService = new CoworkingSpaceServiceFactory().create();
    }

    /**
     * Handles GET requests for retrieving workplaces.
     *
     * @param req  HTTP servlet request.
     * @param resp HTTP servlet response.
     * @throws IOException if there is an issue with servlet IO operations.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<Workplace> workplaces = coworkingSpaceService.getWorkplaces();
            Collection<WorkplaceResponseDto> workplaceResponseDtos = workplaceMapper.worplacesToWorkplaceResponseDtos(workplaces);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), workplaceResponseDtos);
        } else {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Workplace workplace = coworkingSpaceService.findWorkplaceById(id);
                WorkplaceResponseDto workplaceResponseDto = workplaceMapper.workplaceToWorkplaceResponseDto(workplace);
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getOutputStream(), workplaceResponseDto);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный путь запроса!");
            }
        }
    }

    /**
     * Handles POST requests for creating new workplaces.
     *
     * @param req  HTTP servlet request.
     * @param resp HTTP servlet response.
     * @throws IOException if there is an issue with servlet IO operations.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BufferedReader reader = req.getReader();
            WorkplaceRequestDto workplaceRequestDto = objectMapper.readValue(reader, WorkplaceRequestDto.class);
            Workplace workplace = workplaceMapper.workplaceRequestDtotoWorkplace(workplaceRequestDto);
            coworkingSpaceService.addWorkplace(workplace);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Handles PUT requests for updating existing workplaces.
     *
     * @param req  HTTP servlet request.
     * @param resp HTTP servlet response.
     * @throws IOException if there is an issue with servlet IO operations.
     */
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            BufferedReader reader = req.getReader();
            WorkplaceRequestDto workplaceRequestDto = objectMapper.readValue(reader, WorkplaceRequestDto.class);
            Workplace workplace = workplaceMapper.workplaceRequestDtotoWorkplace(workplaceRequestDto);
            workplace.setId(id);
            WorkplaceResponseDto workplaceResponseDto = workplaceMapper.workplaceToWorkplaceResponseDto(workplace);
            coworkingSpaceService.findWorkplaceById(id);
            coworkingSpaceService.updateWorkplace(workplace);
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), workplaceResponseDto);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid workplace ID");
        }
    }

    /**
     * Handles DELETE requests for deleting existing workplaces.
     *
     * @param req  HTTP servlet request.
     * @param resp HTTP servlet response.
     * @throws IOException if there is an issue with servlet IO operations.
     */
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            coworkingSpaceService.findWorkplaceById(id);
            coworkingSpaceService.deleteWorkplace(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid workplace ID");
        }
    }
}
