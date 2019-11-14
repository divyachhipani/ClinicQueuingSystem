package ntu.cz3002advswen.com.getadoc.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.activities.MainActivity;
import ntu.cz3002advswen.com.getadoc.models.patientInfoModel;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;

public class ProfileFragment extends Fragment {

    EditText _usernameText;
    EditText _nricText;
    EditText _emailText;
    EditText _hint;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    EditText _firstname;
    EditText _lastname;
    DatePicker _dobPick;
    EditText _age;
    EditText _nationality;
    EditText _mobileNo;
    EditText _houseNo;
    ToggleButton _gender;
    EditText _maritalStat;
    EditText _adress;
    Button _cancel;
    Button _update;

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    Calendar _setcalendar = Calendar.getInstance();

    WorkCounter wc_Count;
    static patientInfoModel patIM = new patientInfoModel();

    public class WorkCounter {
        private int runningTasks;
        private final Context ctx;

        public WorkCounter(int numberOfTasks, Context ctx) {
            this.runningTasks = numberOfTasks;
            this.ctx = ctx;
        }

        // Only call this in onPostExecute! (or add synchronized to method declaration)
        public void taskFinished() {
            if (--runningTasks == 0) {
                LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(this.ctx);
                mgr.sendBroadcast(new Intent("all_tasks_have_finished"));
                Fragment fragment = new MapsFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle(R.string.profile);

        _usernameText = (EditText) view.findViewById(R.id.input_username);
        _nricText = (EditText) view.findViewById(R.id.input_nric);
        _emailText = (EditText) view.findViewById(R.id.input_email);
        _hint = (EditText) view.findViewById(R.id.input_hint);
        _passwordText = (EditText) view.findViewById(R.id.input_password);
        _reEnterPasswordText = (EditText) view.findViewById(R.id.input_reEnterPassword);
        _firstname = (EditText) view.findViewById(R.id.input_firstname);
        _lastname = (EditText) view.findViewById(R.id.input_lastname);
        _dobPick = (DatePicker) view.findViewById(R.id.dobdatePicker);
        _age = (EditText) view.findViewById(R.id.input_age);
        _nationality = (EditText) view.findViewById(R.id.input_natioanlity);
        _mobileNo = (EditText) view.findViewById(R.id.input_mobileNo);
        _houseNo = (EditText) view.findViewById(R.id.input_houseNo);
        _gender = (ToggleButton) view.findViewById(R.id.tbGender);
        _maritalStat = (EditText) view.findViewById(R.id.input_maritalstatus);
        _adress = (EditText) view.findViewById(R.id.input_address);
        _cancel = (Button) view.findViewById(R.id.btn_cancel);
        _update = (Button) view.findViewById(R.id.btn_update);

        new AsyncPatientRetrieve().execute(MainActivity.getUser());

        _update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    onValidateFailed();
                    return;
                }

                patIM.setEmail(_emailText.getText().toString());
                patIM.setHint(_hint.getText().toString());
                patIM.setPassword(_passwordText.getText().toString());
                patIM.setFirstName(_firstname.getText().toString());
                patIM.setLastName(_lastname.getText().toString());
                patIM.setDOB(dateFormat.format(_setcalendar.getTime()));
                patIM.setAge(Integer.parseInt(_age.getText().toString()));
                patIM.setNationality(_nationality.getText().toString());
                patIM.setMobileNo(_mobileNo.getText().toString());
                patIM.setHouseNo(_houseNo.getText().toString());
                if (_gender.isChecked() == true)
                    patIM.setGender("Male");
                else
                    patIM.setGender("Female");
                patIM.setMaritalStatus(_maritalStat.getText().toString());
                patIM.setAddress(_adress.getText().toString());

                new AsyncPatientUpdate().execute(patIM);

            }
        });

        _cancel.setEnabled(false);
        _cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MapsFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        wc_Count = new WorkCounter(1, getActivity());
        return view;
    }


    public void onRetrieveSuccess(ArrayList<patientInfoModel> returnResult) {

        patIM = returnResult.get(0);

        _usernameText.setText(MainActivity.getUser());
        _usernameText.setFocusable(false);

        _nricText.setText(returnResult.get(0).getNRIC());
        _nricText.setFocusable(false);

        _emailText.setText(returnResult.get(0).getEmail());

        _hint.setText(returnResult.get(0).getHint());
        _firstname.setText(returnResult.get(0).getFirstName());
        _lastname.setText(returnResult.get(0).getLastName());


        try {

            Date convertedDate = new Date();
            Calendar calendar = Calendar.getInstance();
            if (!returnResult.get(0).getDOB().equals("")) {
                convertedDate = dateFormat.parse(returnResult.get(0).getDOB().toString());
                calendar.setTime(convertedDate);
            }
            calendar.setTime(convertedDate);
            _dobPick.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), new DatePicker.OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    //Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                    try {
                        _setcalendar.set(year,(month+1),dayOfMonth);

                    } catch (Exception e) {

                    }
                }
            });
        } catch (Exception e) {
        }

        _age.setText(returnResult.get(0).getAge().toString());
        _nationality.setText(returnResult.get(0).getNationality());
        _mobileNo.setText(returnResult.get(0).getMobileNo());
        _houseNo.setText(returnResult.get(0).getHouseNo());
        if (!(returnResult.get(0).getGender().equals(""))) {
            _gender.setChecked((returnResult.get(0).getGender().toUpperCase().equals("MALE") ? true : false));
            _gender.setEnabled(false);
        } else {
            _gender.setChecked(false);
        }
        _maritalStat.setText(returnResult.get(0).getMaritalStatus());
        _adress.setText(returnResult.get(0).getAddress());
    }

    public void onRetrieveFailed() {
        Toast.makeText(getActivity(), "Retrieve Information Failed", Toast.LENGTH_LONG).show();
    }

    public void onValidateFailed() {
        Toast.makeText(getActivity(), "Please Check Inputs Again", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        try {
            String username = _usernameText.getText().toString();
            String nric = _nricText.getText().toString();
            String email = _emailText.getText().toString();
            String hint = _hint.getText().toString();
            String password = _passwordText.getText().toString();
            String reEnterPassword = _reEnterPasswordText.getText().toString();
            String FirstName = _firstname.getText().toString();
            String LastName = _lastname.getText().toString();
            Integer Age = Integer.parseInt(_age.getText().toString());
            //String DOB =
            String Nationality = _nationality.getText().toString();
            String MobileNo = _mobileNo.getText().toString();
            String HouseNo = _houseNo.getText().toString();
            //String Gender =
            String MaritalStatus = _maritalStat.getText().toString();
            String Address = _adress.getText().toString();


            if (username.isEmpty() || username.length() < 3) {
                _usernameText.setError("Username at least 3 characters");
                valid = false;
            } else {
                _usernameText.setError(null);
            }

            if (nric.isEmpty()) {
                _nricText.setError("Enter Valid NRIC");
                valid = false;
            } else {
                _nricText.setError(null);
            }

            if (hint.isEmpty()) {
                _hint.setError("Password Hint at least 3 characters");
                valid = false;
            } else {
                _hint.setError(null);
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("Enter a valid email address");
                valid = false;
            } else {
                _emailText.setError(null);
            }


            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("Password is not between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
                _reEnterPasswordText.setError("Password Do not match");
                valid = false;
            } else {
                _reEnterPasswordText.setError(null);
            }

            if (FirstName.isEmpty() || FirstName.length() < 2) {
                _firstname.setError("First Name at least 2 characters");
                valid = false;
            } else {
                _firstname.setError(null);
            }

            if (LastName.isEmpty() || LastName.length() < 2) {
                _lastname.setError("First Name at least 2 characters");
                valid = false;
            } else {
                _lastname.setError(null);
            }

            if (Age == 0) {
                _age.setError("Age Cannot Be 0");
                valid = false;
            } else {
                _age.setError(null);
            }

            if (Nationality.isEmpty()) {
                _nationality.setError("Nationality cannot be null");
                valid = false;
            } else {
                _nationality.setError(null);
            }

            if (MobileNo.isEmpty()) {
                _mobileNo.setError("Mobile Number cannot be null");
                valid = false;
            } else {
                _mobileNo.setError(null);
            }

            if (HouseNo.isEmpty()) {
                _houseNo.setError("House Number cannot be null");
                valid = false;
            } else {
                _houseNo.setError(null);
            }

            if (MaritalStatus.isEmpty()) {
                _maritalStat.setError("Marital Status cannot be null");
                valid = false;
            } else {
                _maritalStat.setError(null);
            }

            if (Address.isEmpty()) {
                _adress.setError("Address cannot be null");
                valid = false;
            } else {
                _adress.setError(null);
            }

        } catch (Exception e) {

        }


        return valid;
    }

    protected class AsyncPatientRetrieve extends AsyncTask<String, JSONObject, ArrayList<patientInfoModel>> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);

        @Override
        protected ArrayList<patientInfoModel> doInBackground(String... params) {

            RestAPI api = new RestAPI();
            ArrayList<patientInfoModel> user = new ArrayList<patientInfoModel>();
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.RetrievePatientInformation(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                user = parser.parseRetrieve_PatientInformation(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncPatientRetrieve", e.getMessage());

            }
            return user;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Fetching Account Information...");
            progressDialog.show();
            //Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(final ArrayList<patientInfoModel> returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or validateFailed
                            if (returnResult.size() != 0) {
                                onRetrieveSuccess(returnResult);
                            } else {
                                onRetrieveFailed();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }


    protected class AsyncPatientUpdate extends AsyncTask<patientInfoModel, JSONObject, Boolean> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);

        @Override
        protected Boolean doInBackground(patientInfoModel... params) {

            RestAPI api = new RestAPI();
            Boolean updateUser = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.UpdatePatientAccount(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                updateUser = parser.parseUpdatePatientAccount(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncPatientRetrieve", e.getMessage());

            }
            return updateUser;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Updating Account Information...");
            progressDialog.show();
            //Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        protected void onPostExecute(final Boolean returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or validateFailed
                            if (returnResult == true) {
                                onUpdateSuccess();
                            } else {
                                onUpdateFailed();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }

    public void onUpdateSuccess() {
        Toast.makeText(getActivity(), "Update Information Success", Toast.LENGTH_LONG).show();
        wc_Count.taskFinished();
    }

    public void onUpdateFailed() {
        Toast.makeText(getActivity(), "Update Information Failed", Toast.LENGTH_LONG).show();
    }

}
