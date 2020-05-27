package modelo;

import java.util.ArrayList;

public class Users {
    private int id;
    private String Name,Email,Telephone,Info,TypeUser, Username;
    private ArrayList<Training> Training;
    private ArrayList<Measures> Measures;

    public Users(int id, String Name, String Email, String Telephone, String Info, String TypeUser, String Username){
        this.id = id;
        this.Name = Name;
        this.Email = Email;
        this.Telephone = Telephone;
        this.Info = Info;
        this.TypeUser = TypeUser;
        this.Username = Username;
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
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public String getTypeUser() {
        return TypeUser;
    }

    public void setTypeUser(String TypeUser) {
        this.TypeUser = TypeUser;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }


    public void setTraining(Training Training) {
        this.Training.add(Training);
    }

    public ArrayList<Training> getTraining() {
        return Training;
    }

    public void setTraning(ArrayList<Training> Training) {
        this.Training = Training;
    }

    public void setMeasures(Measures Measures) {
        this.Measures.add(Measures);
    }

    public ArrayList<Measures> getMeasures() {
        return Measures;
    }

    public void setMeasures(ArrayList<Measures> Measures) {
        this.Measures = Measures;
    }
}
