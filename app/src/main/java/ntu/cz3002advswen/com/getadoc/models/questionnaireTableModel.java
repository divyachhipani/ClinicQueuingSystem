package ntu.cz3002advswen.com.getadoc.models;

import java.util.Date;

/**
 * Created by Kelvin on 3/11/2017.
 */

public class questionnaireTableModel {

    public final static Integer integerNull = -1;

    private String _CName;
    private Integer _DType;


    private String _UserName;
    private String _OtherCountries;
    private String _OtherSymptoms;
    private String _ClinicID;
    private Integer _QID;
    private Boolean _HFever;
    private Boolean _HFlu;
    private Boolean _HCough;
    private Date _Date;

    //region Constructor

    public questionnaireTableModel() {
    }

    public questionnaireTableModel(String UserName, String OtherCountries, String OtherSymptoms,
                                   String ClinicID, Integer QID, Boolean HFever, Boolean HFlu,
                                   Boolean HCough, Date Date) {
        this._UserName = UserName;
        this._OtherCountries = OtherCountries;
        this._OtherSymptoms = OtherSymptoms;
        this._ClinicID = ClinicID;
        this._QID = QID;
        this._HFever = HFever;
        this._HFlu = HFlu;
        this._HCough = HCough;
        this._Date = Date;
    }

    public questionnaireTableModel(String CName, String UserName, String OtherCountries, String OtherSymptoms,
                                   String ClinicID, Integer QID, Boolean HFever, Boolean HFlu,
                                   Boolean HCough, Date Date, Integer Dtype) {
        this._CName = CName;
        this._UserName = UserName;
        this._OtherCountries = OtherCountries;
        this._OtherSymptoms = OtherSymptoms;
        this._ClinicID = ClinicID;
        this._QID = QID;
        this._HFever = HFever;
        this._HFlu = HFlu;
        this._HCough = HCough;
        this._Date = Date;
    }

    public questionnaireTableModel(String CName, String UserName, String ClinicID,
                                   Integer QID, Date Date, Integer Dtype) {
        this._CName = CName;
        this._UserName = UserName;
        this._ClinicID = ClinicID;
        this._QID = QID;
        this._Date = Date;
        this._DType = Dtype;
    }
    //endregion

    //region Getter Setter

    public Integer get_DType() {
        return _DType;
    }

    public void set_DType(Integer _DType) {
        this._DType = _DType;
    }

    public String getCName() {
        return _CName;
    }

    public void setCName(String _CName) {
        this._CName = _CName;
    }

    public String getUserName() {
        return _UserName;
    }

    public void setUserName(String _UserName) {
        this._UserName = _UserName;
    }

    public String getOtherCountries() {
        return _OtherCountries;
    }

    public void setOtherCountries(String _OtherCountries) {
        this._OtherCountries = _OtherCountries;
    }

    public String getOtherSymptoms() {
        return _OtherSymptoms;
    }

    public void setOtherSymptoms(String _OtherSymptoms) {
        this._OtherSymptoms = _OtherSymptoms;
    }

    public String getClinicID() {
        return _ClinicID;
    }

    public void setClinicID(String _ClinicID) {
        this._ClinicID = _ClinicID;
    }

    public Integer getQID() {
        return _QID;
    }

    public void setQID(Integer _QID) {
        this._QID = _QID;
    }

    public Boolean getHFever() {
        return _HFever;
    }

    public void setHFever(Boolean _HFever) {
        this._HFever = _HFever;
    }

    public Boolean getHFlu() {
        return _HFlu;
    }

    public void setHFlu(Boolean _HFlu) {
        this._HFlu = _HFlu;
    }

    public Boolean getHCough() {
        return _HCough;
    }

    public void setHCough(Boolean _HCough) {
        this._HCough = _HCough;
    }

    public Date getDate() {
        return _Date;
    }

    public void setDate(Date  _Date) {
        this._Date = _Date;
    }

    //endregion

}
