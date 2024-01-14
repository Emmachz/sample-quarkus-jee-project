package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.BankDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.NoSuchComptException;
import fr.pantheonsorbonne.ufr27.miage.dto.TransfertArgent;
import fr.pantheonsorbonne.ufr27.miage.dto.UserLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OperationServiceImpl implements OperationService{
    @Inject
    BankDAO bankDAO;

    @Override
    @Transactional
    public TransfertArgent realizeOperation (TransfertArgent transfertArgent) throws NoSuchComptException {
        UserLocal emetteur = transfertArgent.getEmetteur();
        UserLocal receveur = transfertArgent.getReceveur();
        double value = transfertArgent.getValue();
        if(!transfertArgent.getEmetteur().getUserNameBank().equals("YesBank") && !transfertArgent.getReceveur().getUserNameBank().equals("YesBank")){
            return new TransfertArgent(emetteur.getUserLogin(), receveur.getUserLogin(), transfertArgent.getValue());
        }else if (transfertArgent.getEmetteur().getUserNameBank().equals("YesBank") && transfertArgent.getReceveur().getUserNameBank().equals("YesBank")){
            if (bankDAO.checkSolde(emetteur.getUserNumeroBank(), value) && bankDAO.checkSolde(receveur.getUserNumeroBank(), value)){
                bankDAO.updateTwoComptes(transfertArgent);
                return transfertArgent;
            }
        } else if (transfertArgent.getEmetteur().getUserNameBank().equals("YesBank")) {
            if (bankDAO.checkSolde(emetteur.getUserNumeroBank(), value)){
                bankDAO.updateCompteCredit(transfertArgent);
                return transfertArgent;
            }
        }else if (transfertArgent.getReceveur().getUserNameBank().equals("YesBank")){
            if (bankDAO.checkSolde(receveur.getUserNumeroBank(), value)){
                bankDAO.updateCompteDebit(transfertArgent);
                return transfertArgent;
            }
        }
        return new TransfertArgent(emetteur.getUserLogin(), receveur.getUserLogin(), transfertArgent.getValue());

    }


    @Override
    @Transactional
    public void creditMoney(String bankNumber, double amount) {
        bankDAO.addMoneyToAccount(bankNumber, amount);
    }

    @Override
    public void debitMoney(String bankNumber, double amount) {
        bankDAO.debitMoneyFromAccount(bankNumber, amount);
    }



}
