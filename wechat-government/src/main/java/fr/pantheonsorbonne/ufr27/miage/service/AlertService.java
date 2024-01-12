package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Alert;
import fr.pantheonsorbonne.ufr27.miage.exception.EventNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Event;

import java.util.Collection;

public interface AlertService {

    Alert alert(Alert alert) throws EventNotFoundException;

    Collection<Event> getEventService();

    Event getEventServiceId(int id);

    Event postEventService( int id, String region);

    String addEventService(Event event);

    void addEventService(int id, String category, String region, String date, String hour, String description, String level, String status);

    void deleteEventServiceId(int id);


}
