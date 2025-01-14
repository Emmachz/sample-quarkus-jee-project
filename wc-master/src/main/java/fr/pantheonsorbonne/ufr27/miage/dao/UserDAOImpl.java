package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.Giving;
import fr.pantheonsorbonne.ufr27.miage.dto.TransfertArgent;
import fr.pantheonsorbonne.ufr27.miage.dto.UserLocal;
import fr.pantheonsorbonne.ufr27.miage.exception.UnsufficientWalletAmountToPay;
import fr.pantheonsorbonne.ufr27.miage.exception.UserNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.model.Versement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(name = "mysql")
    EntityManager em;

    @Override
    @Transactional
    public User findUser(Long userId) throws NoSuchUserException {
        try {
            User userFind = (User) em.createQuery("Select user from User user where user.userId=:userId").setParameter("userId", userId).getSingleResult();
            return userFind;
        } catch (NoResultException e) {
            throw new NoSuchUserException();
        }
    }

    @Override
    @Transactional
    public User findUserByLogin(String userLogin) throws UserNotFoundException.NoExistUserException {
        try {
            User userFind = (User) em.createQuery("Select user from User user where user.userLogin=:userLogin").setParameter("userLogin", userLogin).getSingleResult();
            return userFind;
        } catch (NoResultException e) {
            throw new UserNotFoundException.NoExistUserException(userLogin);
        }
    }

    @Override
    public TransfertArgent upadateUser (Versement versement){
        try{
            User emetteur = em.find(User.class, versement.getEmetteurId().getUserId());
            User receveur = em.find(User.class, versement.getReceveurId().getUserId());
            double value = versement.getVersementAmount();
            if (emetteur != null && receveur != null){
                emetteur.setUserWallet(emetteur.getUserWallet() - value);
                receveur.setUserWallet(receveur.getUserWallet() + value);
                em.persist(emetteur);
                em.persist(receveur);
                UserLocal emetteurLocal = new UserLocal(emetteur.getUserName(), emetteur.getUserWallet(), emetteur.getUserLogin(), emetteur.getUserRegion().getRegion(), emetteur.getUserEmail(), emetteur.getUserNameBank(), emetteur.getUserNumeroBank());
                UserLocal receveurLocal = new UserLocal(receveur.getUserName(), receveur.getUserWallet(), receveur.getUserLogin(), receveur.getUserRegion().getRegion(), receveur.getUserEmail(), receveur.getUserNameBank(), receveur.getUserNumeroBank());
                return new TransfertArgent(emetteurLocal,receveurLocal, value);
            }
        } catch (NoResultException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    @Transactional
    public void debitAmountToUser(String login, double amount) throws UnsufficientWalletAmountToPay {
        try {
            User user = this.findUserByLogin(login);
            double initialWalletAmount = user.getUserWallet();
            if(initialWalletAmount - amount < 0)
            {
                throw  new UnsufficientWalletAmountToPay();
            }
            else
            {
                user.setUserWallet(initialWalletAmount - amount);
                em.persist(user);
            }
        } catch(UserNotFoundException.NoExistUserException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional
    public void creditAmountToUser(String login, double amount) {
        try {
            User user = this.findUserByLogin(login);
            double initialWalletAmount = user.getUserWallet();
            user.setUserWallet(initialWalletAmount + amount);
            em.persist(user);
        } catch(UserNotFoundException.NoExistUserException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Giving upadateUserGiving(Giving give){
        try{
            User emetteur = this.findUserByLogin(give.getUsergive().userLogin());
            double value = give.getQuantity();
            if (emetteur != null){
                emetteur.setUserWallet(emetteur.getUserWallet() - value);
                em.persist(emetteur);
                return give;
            }
        } catch (UserNotFoundException.NoExistUserException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
