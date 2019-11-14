package ntu.cz3002advswen.com.getadoc.models;

public class queueTableModel {



    private Integer _Row;
    private String _ClinicName;
    private String _ClinicContact;
    private String _ClinicAddress;
    private String _ClinicID;
    private Integer _QueueNo;


    private String _Username;
    private String _NRIC;
    private String _Email;
    private String _Firstname;
    private String _Lastname;
    private String _DOB;
    private String _Age;
    private String _Nationality;
    private String _Gender;
    private Boolean _Valid;
    private String _MobileNo;


    //region Constructor

    public queueTableModel() {

    }

    public queueTableModel(String Username, Integer QueueNo, String ClinicID,
                           Boolean Valid) {
        this._QueueNo = QueueNo;
        this._Valid = Valid;
        this._Username = Username;
        this._ClinicID = ClinicID;

    }

    public queueTableModel(String Username, Integer QueueNo, String ClinicID,
                           Boolean Valid, String _ClinicName, String ClinicContact,
                           String ClinicAddress, String NRIC, String Email,
                           String Firstname, String Lastname, String DOB,
                           String Age, String Nationality, String MobileNo,
                           String Gender, Integer Row) {
        this._QueueNo = QueueNo;
        this._Valid = Valid;
        this._Username = Username;
        this._ClinicID = ClinicID;

        this._ClinicName = _ClinicName;
        this._ClinicContact = ClinicContact;
        this._ClinicAddress = ClinicAddress;

        this._NRIC = NRIC;
        this._Email = Email;
        this._Firstname = Firstname;
        this._Lastname = Lastname;
        this._DOB = DOB;
        this._Age = Age;
        this._Nationality = Nationality;
        this._Gender = Gender;
        this._MobileNo = MobileNo;
        this._Row = Row;

    }

    //endregion

    //region Getter Setter

    public Integer getRow() {
        return _Row;
    }

    public void setRow(Integer _Row) {
        this._Row = _Row;
    }

    public String getMobileNo() {
        return _MobileNo;
    }

    public void setMobileNo(String _MobileNo) {
        this._MobileNo = _MobileNo;
    }

    public String getClinicName() {
        return _ClinicName;
    }

    public void setClinicName(String _ClinicName) {
        this._ClinicName = _ClinicName;
    }

    public String getClinicContact() {
        return _ClinicContact;
    }

    public void setClinicContact(String _ClinicContact) {
        this._ClinicContact = _ClinicContact;
    }

    public String getClinicAddress() {
        return _ClinicAddress;
    }

    public void setClinicAddress(String _ClinicAddress) {
        this._ClinicAddress = _ClinicAddress;
    }

    public String getClinicID() {
        return _ClinicID;
    }

    public void setClinicID(String _ClinicID) {
        this._ClinicID = _ClinicID;
    }

    public Integer getQueueNo() {
        return _QueueNo;
    }

    public void setQueueNo(Integer _QueueNo) {
        this._QueueNo = _QueueNo;
    }

    public String getUsername() {
        return _Username;
    }

    public void setUsername(String _Username) {
        this._Username = _Username;
    }

    public String getNRIC() {
        return _NRIC;
    }

    public void setNRIC(String _NRIC) {
        this._NRIC = _NRIC;
    }

    public String getEmail() {
        return _Email;
    }

    public void setEmail(String _Email) {
        this._Email = _Email;
    }

    public String getFirstname() {
        return _Firstname;
    }

    public void setFirstname(String _Firstname) {
        this._Firstname = _Firstname;
    }

    public String getLastname() {
        return _Lastname;
    }

    public void setLastname(String _Lastname) {
        this._Lastname = _Lastname;
    }

    public String getDOB() {
        return _DOB;
    }

    public void setDOB(String _DOB) {
        this._DOB = _DOB;
    }

    public String getAge() {
        return _Age;
    }

    public void setAge(String _Age) {
        this._Age = _Age;
    }

    public String getNationality() {
        return _Nationality;
    }

    public void setNationality(String _Nationality) {
        this._Nationality = _Nationality;
    }

    public String getGender() {
        return _Gender;
    }

    public void setGender(String _Gender) {
        this._Gender = _Gender;
    }

    public Boolean getValid() {
        return _Valid;
    }

    public void setValid(Boolean _Valid) {
        this._Valid = _Valid;
    }
    //endregion
}
