package ntu.cz3002advswen.com.getadoc.models;


import java.util.Date;

public class patientAllergyModel {

    private String _PUsername;
    private Date _Date;
    private String _DrugAllergy;
    private String _AllergyReaction;
    private Integer _ID;


    //region Constructor

    public patientAllergyModel() {
    }

    public patientAllergyModel(String _PUsername, Date _Date, String _DrugAllergy, String _AllergyReaction, Integer _ID) {
        this._PUsername = _PUsername;
        this._Date = _Date;
        this._DrugAllergy = _DrugAllergy;
        this._AllergyReaction = _AllergyReaction;
        this._ID = _ID;
    }

    //endregion

    //region Getter Setter

    public String getPUsername() {
        return _PUsername;
    }

    public void setPUsername(String _PUsername) {
        this._PUsername = _PUsername;
    }

    public Date getDate() {
        return _Date;
    }

    public void setDate(Date _Date) {
        this._Date = _Date;
    }

    public String getDrugAllergy() {
        return _DrugAllergy;
    }

    public void setDrugAllergy(String _DrugAllergy) {
        this._DrugAllergy = _DrugAllergy;
    }

    public String getAllergyReaction() {
        return _AllergyReaction;
    }

    public void setAllergyReaction(String _AllergyReaction) {
        this._AllergyReaction = _AllergyReaction;
    }

    public Integer getID() {
        return _ID;
    }

    public void setID(Integer _ID) {
        this._ID = _ID;
    }

    //endregion
}
