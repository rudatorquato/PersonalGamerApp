package modelo;

import java.util.ArrayList;

public class Users {
    private int id;
    private String Name,Email,Telephone,Info,TypeUser;
    private ArrayList<Traning> Traning;
    private ArrayList<Measures> Measures;

    public Users(int id, String Name, String Email, String Telephone, String Info, String TypeUser){
        this.id = id;
        this.Name = Name;
        this.Email = Email;
        this.Telephone = Telephone;
        this.Info = Info;
        this.TypeUser = TypeUser;
        this.Traning = new ArrayList<>();
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

    public void setTraning(Traning Traning) {
        this.Traning.add(Traning);
    }

    public ArrayList<Traning> getTraning() {
        return Traning;
    }

    public void setTraning(ArrayList<Traning> Traning) {
        this.Traning = Traning;
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
