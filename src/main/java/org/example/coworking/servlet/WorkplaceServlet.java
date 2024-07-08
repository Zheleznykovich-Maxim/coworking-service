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

@Loggable
@WebServlet("/workplace/*")
public class WorkplaceServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final CoworkingSpaceService coworkingSpaceService;
    private final WorkplaceMapper workplaceMapper;

    public WorkplaceServlet() throws IOException {
        this.workplaceMapper = new WorkplaceMapperImpl();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.coworkingSpaceService = new CoworkingSpaceServiceFactory().create();
    }

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
