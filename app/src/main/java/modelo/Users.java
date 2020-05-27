package modelo;

import java.util.ArrayList;

public class Users {
    private int id;
    private String name, email, telephone, info, typeUser, username;
    private ArrayList<Training> Training;
    private ArrayList<Measures> Measures;

    public Users(int id, String name, String email, String telephone, String info, String typeUser, String username){
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.info = info;
        this.typeUser = typeUser;
        this.username = username;
        this.Training = new ArrayList<>();
        this.Measures = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setTraining(Training training) {
        this.Training.add(training);
    }

    public ArrayList<Training> getTraining() {
        return Training;
    }

    public void setTraning(ArrayList<Training> trainings) {
        this.Training = trainings;
    }

    public void setMeasures(Measures measures) {
        this.Measures.add(measures);
    }

    public ArrayList<Measures> getMeasures() {
        return Measures;
    }

    public void setMeasures(ArrayList<Measures> measures) {
        this.Measures = measures;
    }
}
