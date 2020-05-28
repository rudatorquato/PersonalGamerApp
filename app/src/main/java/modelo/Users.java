package modelo;

import java.util.ArrayList;

public class Users {
    private String id, name, email, phone, info, typeUser, username;
    private ArrayList<Training> Training;
    private ArrayList<Measures> Measures;

    public Users(String id, String name, String email, String phone, String info, String typeUser, String username){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.info = info;
        this.typeUser = typeUser;
        this.username = username;
        this.Training = new ArrayList<>();
        this.Measures = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
