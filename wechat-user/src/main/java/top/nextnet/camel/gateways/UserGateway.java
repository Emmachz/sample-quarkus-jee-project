package top.nextnet.camel.gateways;

import fr.pantheonsorbonne.ufr27.miage.dto.TransfertArgent;

public interface UserGateway {
    boolean sendTransfertInfos(TransfertArgent transfertArgent);
}
