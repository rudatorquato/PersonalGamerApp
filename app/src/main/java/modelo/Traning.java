package modelo;

public class Traning{
    String NameTraning, Place, Exercise;
    int Sequence, Series,Repetition, Charge;

    public Traning(String NameTraning, String Place, String Exercise, int Sequence, int Series, int Repetition, int Charge){
        this.NameTraning = NameTraning;
        this.Place = Place;
        this.Exercise = Exercise;
        this.Sequence = Sequence;
        this.Series = Series;
        this.Repetition = Repetition;
        this.Charge = Charge;
    }

    public String getNameTraning(){
        return NameTraning;
    }

    public void setNameTraning(String NameTraning) {
        this.NameTraning = NameTraning;
    }

    public String getPlace(){
        return Place;
    }

    public void setPlace(String Place) {
        this.Place = Place;
    }

    public String getExercise(){
        return  Exercise;
    }

    public void setExercise(String Exercise){
        this.Exercise = Exercise;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int Sequence) {
        this.Sequence = Sequence;
    }

    public int getSeries() {
        return Series;
    }

    public void setSeries(int Series) {
        this.Series = Series;
    }

    public int getRepetition() {
        return Repetition;
    }

    public void setRepetition(int Repetition) {
        this.Repetition = Repetition;
    }

    public int getCharge() {
        return Charge;
    }

    public void setCharge(int Charge) {
        this.Charge = Charge;
    }
}
