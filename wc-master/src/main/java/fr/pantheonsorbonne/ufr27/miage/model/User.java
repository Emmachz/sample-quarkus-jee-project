package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
@Table(name = "User")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "user_name", nullable = false, length = 45)
    private String userName;
    @Column(name = "user_login", nullable = false, unique = true)
    private String userLogin;
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;
    @Column(name = "user_wallet")
    private double userWallet;
    @Column(name = "user_nameBank")
    private String userNameBank;
    @Column(name = "user_numeroBank")
    private String userNumeroBank;
    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region idRegion;

    public User (String userName, String userLogin,
                 String userEmail, Region idRegion, double userWallet,
                 String userNameBank,String userNumeroBank ){
        this.userName = userName;
        this. userLogin = userLogin;
        this. userEmail = userEmail;
        this.idRegion = idRegion;
        this.userWallet = userWallet;
        this.userNameBank = userNameBank;
        this.userNumeroBank =userNumeroBank;
    }

    public User() {

    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserName (String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }

    public void setUserLogin (String userLogin){
        this.userLogin = userLogin;
    }

    public String getUserLogin(){
        return userLogin;
    }

    public void setUserEmail (String userEmail){
        this.userEmail = userEmail;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setUserRegion (Region idRegion){
        this.idRegion = idRegion;
    }

    public Region getUserRegion(){
        return idRegion;
    }

    public void setUserNameBank (String bankName){
        this.userNameBank = bankName;
    }

    public String getUserNameBank(){
        return userNameBank;
    }

    public void setUserNumeroBank(String numeroBank){
        this.userNumeroBank = numeroBank;
    }
    public String getUserNumeroBank(){
        return userNumeroBank;
    }

    public void setUserWallet(double walletValue){
        this.userWallet = walletValue;
    }
    public double getUserWallet(){
        return userWallet;
    }



}