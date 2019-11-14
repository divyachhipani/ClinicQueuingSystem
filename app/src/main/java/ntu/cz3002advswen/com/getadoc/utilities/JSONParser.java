package ntu.cz3002advswen.com.getadoc.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ntu.cz3002advswen.com.getadoc.models.patientAllergyModel;
import ntu.cz3002advswen.com.getadoc.models.patientInfoModel;
import ntu.cz3002advswen.com.getadoc.models.questionnaireTableModel;
import ntu.cz3002advswen.com.getadoc.models.queueTableModel;

public class JSONParser {

    public JSONParser() {
        super();
    }

    //region Queue Table

    public String parseCreateQueue(JSONObject object) {
        String createSuccess = "";
        try {
            createSuccess = object.getString("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseCreateQueue");
        }
        return createSuccess;
    }

    public Integer parseRetrieveTotalQueueByClinicID(JSONObject object) {
        Integer queueCount = -1;
        try {
            queueCount = object.getInt("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseRetrieveTotalQueueByClinicID");
        }
        return queueCount;
    }

    public ArrayList<queueTableModel> parseRetrieve_QueueInformation(JSONObject object) {
        ArrayList<queueTableModel> patient_QueueList = new ArrayList<queueTableModel>();

        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                String CName = (jsonObj.has("CName")) ? jsonObj.getString("CName") : "";
                String CcontactNo = (jsonObj.has("CcontactNo")) ? jsonObj.getString("CcontactNo") : "";
                String CAddress = (jsonObj.has("CAddress")) ? jsonObj.getString("CAddress") : "";
                Integer QueueNo = (jsonObj.has("QueueNo")) ? Integer.parseInt(jsonObj.getString("QueueNo").toString()) : 0;
                String Username = (jsonObj.has("Username")) ? jsonObj.getString("Username") : "";
                String ClinicID = (jsonObj.has("ClinicID")) ? jsonObj.getString("ClinicID") : "";
                Boolean Valid = (jsonObj.has("Valid")) ? jsonObj.getBoolean("Valid") : false;
                String NRIC = (jsonObj.has("NRIC")) ? jsonObj.getString("NRIC") : "";
                String Email = (jsonObj.has("Email")) ? jsonObj.getString("Email") : "";
                String FirstName = (jsonObj.has("FirstName")) ? jsonObj.getString("FirstName") : "";
                String LastName = (jsonObj.has("LastName")) ? jsonObj.getString("LastName") : "";
                String DOB = (jsonObj.has("DOB")) ? jsonObj.getString("DOB") : "";
                String Age = (jsonObj.has("Age")) ? jsonObj.getString("Age") : "";
                String Nationality = (jsonObj.has("Nationality")) ? jsonObj.getString("Nationality") : "";
                String MobileNo = (jsonObj.has("MobileNo")) ? jsonObj.getString("MobileNo") : "";
                String Gender = (jsonObj.has("Gender")) ? jsonObj.getString("Gender") : "";
                Integer Row = (jsonObj.has("Row")) ? Integer.parseInt(jsonObj.getString("Row").toString()) : 0;

                queueTableModel qTemp = new queueTableModel
                        (Username, QueueNo, ClinicID, Valid, CName, CcontactNo, CAddress, NRIC, Email, FirstName, LastName, DOB,
                                Age, Nationality, MobileNo, Gender, Row);

                patient_QueueList.add(qTemp);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseRetrieve_QueueInformation");
        }
        return patient_QueueList;
    }
    //endregion

    //region Questionnaire & Question Record

    public boolean parseCreateQuestionnaire(JSONObject object) {
        boolean createSuccess = false;
        try {
            createSuccess = object.getBoolean("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseCreateQuestionnaire");
        }
        return createSuccess;
    }

    public ArrayList<questionnaireTableModel> parseRetrieve_QueestionnaireInformation(JSONObject object) {
        ArrayList<questionnaireTableModel> patient_QueueList = new ArrayList<questionnaireTableModel>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObj = jsonArray.getJSONObject(i);
                    String CName = (jsonObj.has("CName")) ? jsonObj.getString("CName") : "";
                    String Username = (jsonObj.has("Username")) ? jsonObj.getString("Username") : "";
                    String ClinicID = (jsonObj.has("ClinicID")) ? jsonObj.getString("ClinicID") : "";
                    Integer QID = (jsonObj.has("QID")) ? Integer.parseInt(jsonObj.getString("QID").toString()) : 0;

                    Boolean HFever = (jsonObj.has("HFever")) ? jsonObj.getBoolean("HFever") : false;
                    Boolean HFlu = (jsonObj.has("HFlu")) ? jsonObj.getBoolean("HFlu") : false;
                    Boolean HCough = (jsonObj.has("HCough")) ? jsonObj.getBoolean("HCough") : false;

                    String OtherCountries = (jsonObj.has("OtherCountries")) ? jsonObj.getString("OtherCountries") : "";
                    String OtherSymptoms = (jsonObj.has("OtherSymptoms")) ? jsonObj.getString("OtherSymptoms") : "";
                    String DateCreated = (jsonObj.has("Date")) ? jsonObj.getString("Date") : "";

                    Date tempDate = new Date();

                    tempDate = dateFormat.parse(DateCreated.toString());

                    questionnaireTableModel qTemp = new questionnaireTableModel(CName, Username, OtherCountries, OtherSymptoms,
                            ClinicID, QID, HFever, HFlu, HCough, tempDate, questionnaireTableModel.integerNull);

                    patient_QueueList.add(qTemp);
                } catch (Exception e) {

                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseCreateQuestionnaire");
        }
        return patient_QueueList;
    }
    //endregion

    //region Patient Accounts & Information & Drug allergy

    public boolean parseCreateUserAccount(JSONObject object) {
        boolean createSuccess = false;
        try {
            createSuccess = object.getBoolean("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseCreateUserAccount");
        }
        return createSuccess;
    }

    public boolean parseUserAuthentication(JSONObject object, String... params) {
        boolean userAtuh = false;
        boolean userVerify = false;
        boolean passVerfiy = false;
        try {
            JSONObject jsonObj = object.getJSONArray("Value").getJSONObject(0);
            userVerify = (jsonObj.getString("PUsername").equals(params[0])) ? true : false;
            passVerfiy = (jsonObj.getString("Password").equals(params[1])) ? true : false;
            userAtuh = ((userVerify == true) && (passVerfiy == true)) ? true : false;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseUserAuthentication");
        }

        return userAtuh;
    }

    public ArrayList<patientInfoModel> parseRetrieve_PatientInformation(JSONObject object) {
        boolean userAtuh = false;
        ArrayList<patientInfoModel> patientList = new ArrayList<patientInfoModel>();


        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                String PUsername = (jsonObj.has("PUsername")) ? jsonObj.getString("PUsername") : "";
                String NRIC = (jsonObj.has("NRIC")) ? jsonObj.getString("NRIC") : "";
                String Email = (jsonObj.has("Email")) ? jsonObj.getString("Email") : "";
                String Password = (jsonObj.has("Password")) ? jsonObj.getString("Password") : "";
                String Hint = (jsonObj.has("Hint")) ? jsonObj.getString("Hint") : "";
                String FirstName = (jsonObj.has("FirstName")) ? jsonObj.getString("FirstName") : "";
                String LastName = (jsonObj.has("LastName")) ? jsonObj.getString("LastName") : "";
                Integer Age = (jsonObj.has("Age")) ? Integer.parseInt(jsonObj.getString("Age").toString()) : 0;
                String DOB = (jsonObj.has("DOB")) ? jsonObj.getString("DOB") : "";
                String Nationality = (jsonObj.has("Nationality")) ? jsonObj.getString("Nationality") : "";
                String MobileNo = (jsonObj.has("MobileNo")) ? jsonObj.getString("MobileNo") : "";
                String HouseNo = (jsonObj.has("HouseNo")) ? jsonObj.getString("HouseNo") : "";
                String Gender = (jsonObj.has("Gender")) ? jsonObj.getString("Gender") : "";
                String MaritalStatus = (jsonObj.has("MaritalStatus")) ? jsonObj.getString("MaritalStatus") : "";
                String Address = (jsonObj.has("Address")) ? jsonObj.getString("Address") : "";

                patientInfoModel qTemp = new patientInfoModel(PUsername, NRIC, Email, Password, Hint, FirstName, LastName, Age, DOB, Nationality, MobileNo, HouseNo, Gender, MaritalStatus, Address);

                patientList.add(qTemp);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseRetrieve_PatientInformation");
        }
        return patientList;
    }

    public boolean parseUpdatePatientAccount(JSONObject object) {
        boolean updateSuccess = false;
        try {
            updateSuccess = object.getBoolean("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseUpdatePatientAccount");
        }
        return updateSuccess;
    }

    public boolean parseCreateAllergy(JSONObject object) {
        boolean createSuccess = false;
        try {
            createSuccess = object.getBoolean("Value");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseCreateAllergy");
        }
        return createSuccess;
    }

    public ArrayList<patientAllergyModel> parseRetrieve_PatientAllergy(JSONObject object) {
        ArrayList<patientAllergyModel> patient_AllergyList = new ArrayList<patientAllergyModel>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            JSONArray jsonArray = object.getJSONArray("Value");
            JSONObject jsonObj = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObj = jsonArray.getJSONObject(i);
                    Integer Row = (jsonObj.has("Row")) ? Integer.parseInt(jsonObj.getString("Row").toString()) : 0;
                    String PUsername = (jsonObj.has("PUsername")) ? jsonObj.getString("PUsername") : "";
                    String DrugAllergy = (jsonObj.has("DrugAllergy")) ? jsonObj.getString("DrugAllergy") : "";
                    String AllergyReaction = (jsonObj.has("AllergyReaction")) ? jsonObj.getString("AllergyReaction") : "";
                    String DateDeclare = (jsonObj.has("DateDeclare")) ? jsonObj.getString("DateDeclare") : "";

                    Date tempDate = new Date();

                    tempDate = dateFormat.parse(DateDeclare.toString());

                    patientAllergyModel patientAllergy = new patientAllergyModel(PUsername, tempDate, DrugAllergy, AllergyReaction, Row);


                    patient_AllergyList.add(patientAllergy);
                } catch (Exception e) {

                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(e.getMessage(), "JSONParser => parseRetrieve_PatientAllergy");
        }
        return patient_AllergyList;
    }
    //endregion


}
