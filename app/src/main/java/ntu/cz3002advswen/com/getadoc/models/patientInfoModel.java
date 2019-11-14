package ntu.cz3002advswen.com.getadoc.models;

public class patientInfoModel {


    String _PUsername;
    String _NRIC;
    String _Email;
    String _Password;
    String _Hint;
    String _FirstName;
    String _LastName;
    Integer _Age;
    String _DOB;
    String _Nationality;
    String _MobileNo;
    String _HouseNo;
    String _Gender;
    String _MaritalStatus;
    String _Address;

    //region Constructor

    public patientInfoModel() {
    }

    public patientInfoModel(String PUsername, String NRIC, String Email,
                            String Password, String Hint, String FirstName,
                            String LastName, Integer Age, String DOB,
                            String Nationality, String MobileNo, String HouseNo,
                            String Gender, String MaritalStatus, String Address) {
        this._PUsername = PUsername;
        this._NRIC = NRIC;
        this._Email = Email;
        this._Password = Password;
        this._Hint = Hint;
        this._FirstName = FirstName;
        this._LastName = LastName;
        this._Age = Age;
        this._DOB = DOB;
        this._Nationality = Nationality;
        this._MobileNo = MobileNo;
        this._HouseNo = HouseNo;
        this._Gender = Gender;
        this._MaritalStatus = MaritalStatus;
        this._Address = Address;
    }

    //endregion

    //region Getter Setter
    public String getPUsername() {
        return _PUsername;
    }

    public void setPUsername(String _PUsername) {
        this._PUsername = _PUsername;
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

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String _Password) {
        this._Password = _Password;
    }

    public String getHint() {
        return _Hint;
    }

    public void setHint(String _Hint) {
        this._Hint = _Hint;
    }

    public String getFirstName() {
        return _FirstName;
    }

    public void setFirstName(String _FirstName) {
        this._FirstName = _FirstName;
    }

    public String getLastName() {
        return _LastName;
    }

    public void setLastName(String _LastName) {
        this._LastName = _LastName;
    }

    public Integer getAge() {
        return _Age;
    }

    public void setAge(Integer _Age) {
        this._Age = _Age;
    }

    public String getDOB() {
        return _DOB;
    }

    public void setDOB(String _DOB) {
        this._DOB = _DOB;
    }

    public String getNationality() {
        return _Nationality;
    }

    public void setNationality(String _Nationality) {
        this._Nationality = _Nationality;
    }

    public String getMobileNo() {
        return _MobileNo;
    }

    public void setMobileNo(String _MobileNo) {
        this._MobileNo = _MobileNo;
    }

    public String getHouseNo() {
        return _HouseNo;
    }

    public void setHouseNo(String _HouseNo) {
        this._HouseNo = _HouseNo;
    }

    public String getGender() {
        return _Gender;
    }

    public void setGender(String _Gender) {
        this._Gender = _Gender;
    }

    public String getMaritalStatus() {
        return _MaritalStatus;
    }

    public void setMaritalStatus(String _MaritalStatus) {
        this._MaritalStatus = _MaritalStatus;
    }

    public String getAddress() {
        return _Address;
    }

    public void setAddress(String _Address) {
        this._Address = _Address;
    }
    //endregion
}
