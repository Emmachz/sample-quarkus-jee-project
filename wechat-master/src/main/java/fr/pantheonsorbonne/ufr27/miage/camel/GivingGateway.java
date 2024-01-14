package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.Giving;

import fr.pantheonsorbonne.ufr27.miage.exception.QuantityGivenHigherThanDemandedException;
import fr.pantheonsorbonne.ufr27.miage.exception.UnsuficientQuotaForVenueException;
import fr.pantheonsorbonne.ufr27.miage.model.Alert;
import fr.pantheonsorbonne.ufr27.miage.service.AlertService;
import fr.pantheonsorbonne.ufr27.miage.service.GivingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;

@ApplicationScoped
public class GivingGateway {

    @Inject
    GivingService givingService;

    @Inject
    CamelContext camelContext;


    public Giving giveMoney(Giving give) throws QuantityGivenHigherThanDemandedException, UnsuficientQuotaForVenueException {
        return givingService.giveMoney(give);
    }
    public Giving giveTime(Giving give) throws QuantityGivenHigherThanDemandedException, UnsuficientQuotaForVenueException {
        return givingService.giveTime(give);
    }
    public Giving giveClothe(Giving give) throws QuantityGivenHigherThanDemandedException, UnsuficientQuotaForVenueException {

        return givingService.giveClothe(give);
    }

}