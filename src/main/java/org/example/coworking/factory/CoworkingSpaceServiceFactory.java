package org.example.coworking.factory;

import org.example.coworking.model.ConferenceHall;
import org.example.coworking.model.Workplace;
import org.example.coworking.service.CoworkingSpaceService;

import java.util.ArrayList;

public class CoworkingSpaceServiceFactory implements CoworkingFactory<CoworkingSpaceService> {
    @Override
    public CoworkingSpaceService create() {
        ArrayList<Workplace> workplaces = new ArrayList<>();
        ArrayList<ConferenceHall> conferenceHalls = new ArrayList<>();
        return new CoworkingSpaceService(workplaces, conferenceHalls);
    }
}
