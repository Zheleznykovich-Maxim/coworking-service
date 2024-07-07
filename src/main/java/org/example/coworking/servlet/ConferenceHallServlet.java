package org.example.coworking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.coworking.annotations.Loggable;
import org.example.coworking.dto.request.ConferenceHallRequestDto;
import org.example.coworking.dto.response.ConferenceHallResponseDto;
import org.example.coworking.factory.CoworkingSpaceServiceFactory;
import org.example.coworking.mapper.ConferenceHallMapper;
import org.example.coworking.mapper.ConferenceHallMapperImpl;
import org.example.coworking.model.ConferenceHall;
import org.example.coworking.service.CoworkingSpaceService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

@Loggable
@WebServlet("/conference-hall/*")
public class ConferenceHallServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final CoworkingSpaceService coworkingSpaceService;
    private final ConferenceHallMapper conferenceHallMapper;

    public ConferenceHallServlet() throws IOException {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.conferenceHallMapper = new ConferenceHallMapperImpl();
        this.coworkingSpaceService = new CoworkingSpaceServiceFactory().create();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            Collection<ConferenceHall> conferenceHalls = coworkingSpaceService.getConferenceHalls();
            Collection<ConferenceHallResponseDto> conferenceHallResponseDtos = conferenceHallMapper.conferenceHallsToConferenceHallResponseDtos(conferenceHalls);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), conferenceHallResponseDtos);
        } else {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                ConferenceHall conferenceHall = coworkingSpaceService.findConferenceHallById(id);
                ConferenceHallResponseDto conferenceHallResponseDto = conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall);
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getOutputStream(), conferenceHallResponseDto);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный путь запроса!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BufferedReader reader = req.getReader();
            ConferenceHallRequestDto conferenceHallRequestDto = objectMapper.readValue(reader, ConferenceHallRequestDto.class);
            ConferenceHall conferenceHall = conferenceHallMapper.conferenceHallRequestDtotoConferenceHall(conferenceHallRequestDto);
            coworkingSpaceService.addConferenceHall(conferenceHall);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            BufferedReader reader = req.getReader();
            ConferenceHallRequestDto conferenceHallRequestDto = objectMapper.readValue(reader, ConferenceHallRequestDto.class);
            ConferenceHall conferenceHall = conferenceHallMapper.conferenceHallRequestDtotoConferenceHall(conferenceHallRequestDto);
            conferenceHall.setId(id);
            ConferenceHallResponseDto conferenceHallResponseDto = conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall);
            coworkingSpaceService.findConferenceHallById(id);
            coworkingSpaceService.updateConferenceHall(conferenceHall);
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), conferenceHallResponseDto);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid workplace ID");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        try {
            int id = Integer.parseInt(pathParts[1]);
            coworkingSpaceService.findConferenceHallById(id);
            coworkingSpaceService.deleteConferenceHall(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid workplace ID");
        }
    }
}
