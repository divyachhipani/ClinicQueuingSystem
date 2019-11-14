/* JSON API for android appliation */
package ntu.cz3002advswen.com.getadoc.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ntu.cz3002advswen.com.getadoc.models.patientAllergyModel;
import ntu.cz3002advswen.com.getadoc.models.patientInfoModel;
import ntu.cz3002advswen.com.getadoc.models.questionnaireTableModel;
import ntu.cz3002advswen.com.getadoc.models.queueTableModel;

public class RestAPI {


    //region Network Related Setting

    // School Network
     private final String urlString = "http://172.21.146.225/GetADocAPI/";

    // Home Lan
    // private final String urlString = "http://192.168.1.19:45455/";

    // Home Wikfi 
    // final String urlString = "http://192.168.1.12:45455/";

    // TR+91
    // final String urlString = "http://172.27.18.57:45455/";

    //endregion

    //region Rest API Setting

    private static String convertStreamToUTF8String(InputStream stream) throws IOException {
        String result = "";
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[4096];
            int readedChars = 0;
            while (readedChars != -1) {
                readedChars = reader.read(buffer);
                if (readedChars > 0)
                    sb.append(buffer, 0, readedChars);
            }
            result = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String load(String contents) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(60000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
        w.write(contents);
        w.flush();
        InputStream istream = conn.getInputStream();
        String result = convertStreamToUTF8String(istream);
        return result;
    }

    private Object mapObject(Object o) {
        Object finalValue = null;
        if (o.getClass() == String.class) {
            finalValue = o;
        } else if (Number.class.isInstance(o)) {
            finalValue = String.valueOf(o);
        } else if (Date.class.isInstance(o)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", new Locale("en", "USA"));
            finalValue = sdf.format((Date) o);
        } else if (Collection.class.isInstance(o)) {
            Collection<?> col = (Collection<?>) o;
            JSONArray jarray = new JSONArray();
            for (Object item : col) {
                jarray.put(mapObject(item));
            }
            finalValue = jarray;
        } else if (Boolean.class.isInstance(o)) {
            finalValue = ((Boolean) o).booleanValue();
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            Method[] methods = o.getClass().getMethods();
            for (Method method : methods) {
                if (method.getDeclaringClass() == o.getClass()
                        && method.getModifiers() == Modifier.PUBLIC
                        && method.getName().startsWith("get")) {
                    String key = method.getName().substring(3);
                    try {
                        Object obj = method.invoke(o, null);
                        Object value = mapObject(obj);
                        map.put(key, value);
                        finalValue = new JSONObject(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return finalValue;
    }
    //endregion

    //region Queue Table

    public JSONObject RetrieveTotalQueueByClinicID(String clinicID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_TotalQueueByClinicID");
        p.put("clinicID", mapObject(clinicID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject RetrieveRemainQueueByClinicID(String clinicID) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_RemainQueueByClinicID");
        p.put("clinicID", mapObject(clinicID));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject CreateQueue(queueTableModel qtTable) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Create_Queue");
        p.put("userName", mapObject(qtTable.getUsername()));
        p.put("queueNo", mapObject(qtTable.getQueueNo()));
        p.put("clinicID", mapObject(qtTable.getClinicID()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject RetrieveQueueInformation(queueTableModel qtTable) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_QueueInformation");
        p.put("userName", mapObject(qtTable.getUsername()));
        p.put("queueNo", mapObject(qtTable.getQueueNo()));
        p.put("clinicID", mapObject(qtTable.getClinicID()));
        p.put("valid", mapObject(qtTable.getValid()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    //endregion

    //region Questionnaire & Question Record

    public JSONObject CreateQuestionnaire(questionnaireTableModel questTable) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Create_Questionnaire");
        p.put("HFever", mapObject(questTable.getHFever()));
        p.put("HFlu", mapObject(questTable.getHFlu()));
        p.put("HCough", mapObject(questTable.getHCough()));
        p.put("OtherCountries", mapObject(questTable.getOtherCountries()));
        p.put("OtherSymptoms", mapObject(questTable.getOtherSymptoms()));
        p.put("UserID", mapObject(questTable.getUserName()));
        p.put("ClinicID", mapObject(questTable.getClinicID()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject RetrieveQuestionnaire(questionnaireTableModel quTable) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_Questionnaire");
        p.put("userName", mapObject(quTable.getUserName()));
        p.put("clinicID", mapObject(quTable.getClinicID()));
        p.put("date", mapObject(dateFormat.format(quTable.getDate())).toString());
        p.put("dateType", mapObject(quTable.get_DType()));
        p.put("QID", mapObject(quTable.getQID()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    //endregion

    //region region Patient Accounts & Information & Drug allergy

    public JSONObject CreateNewAccount(String PUsername, String NRIC, String Email, String Password, String Hint) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Create_PatientAccount");
        p.put("PUsername", mapObject(PUsername));
        p.put("NRIC", mapObject(NRIC));
        p.put("Email", mapObject(Email));
        p.put("Password", mapObject(Password));
        p.put("Hint", mapObject(Hint));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UserAuthentication(String PUsername) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_PatientAccount");
        p.put("PUsername", mapObject(PUsername));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject RetrievePatientInformation(String PUsername) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_PatientInformation");
        p.put("PUsername", mapObject(PUsername));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject UpdatePatientAccount(patientInfoModel pIM) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Update_PatientAccount");
        p.put("PUsername", mapObject(pIM.getPUsername()));
        p.put("NRIC", mapObject(pIM.getNRIC()));
        p.put("Email", mapObject(pIM.getEmail()));
        p.put("Password", mapObject(pIM.getPassword()));
        p.put("Hint", mapObject(pIM.getHint()));
        p.put("FirstName", mapObject(pIM.getFirstName()));
        p.put("LastName", mapObject(pIM.getLastName()));
        p.put("DOB", mapObject(pIM.getDOB()));
        p.put("Age", mapObject(pIM.getAge().toString()));
        p.put("Nationality", mapObject(pIM.getNationality()));
        p.put("MobileNo", mapObject(pIM.getMobileNo()));
        p.put("HouseNo", mapObject(pIM.getHouseNo()));
        p.put("Gender", mapObject(pIM.getGender()));
        p.put("MaritalStatus", mapObject(pIM.getMaritalStatus()));
        p.put("Address", mapObject(pIM.getAddress()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject CreateDrugAllergy(patientAllergyModel paTable) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Create_Allergy");
        p.put("PUsername", mapObject(paTable.getPUsername()));
        p.put("DrugAllergy", mapObject(paTable.getDrugAllergy()));
        p.put("AllergyReaction", mapObject(paTable.getAllergyReaction()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }

    public JSONObject RetrieveAllergyInformation(patientAllergyModel allTable) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface", "GetADocRestAPI");
        o.put("method", "Retrieve_PatientAllergy");
        p.put("PUsername", mapObject(allTable.getPUsername()));
        o.put("parameters", p);
        String s = o.toString();
        String r = load(s);
        result = new JSONObject(r);
        return result;
    }
    //endregion


}


