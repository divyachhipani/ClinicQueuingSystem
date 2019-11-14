package ntu.cz3002advswen.com.getadoc.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.activities.MainActivity;
import ntu.cz3002advswen.com.getadoc.customcontrol.DrugAllergySortableTableView;
import ntu.cz3002advswen.com.getadoc.datadapter.DrugAllergyTableDataAdapter;
import ntu.cz3002advswen.com.getadoc.models.patientAllergyModel;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;

public class DrugAllergyFrament extends Fragment {

    private patientAllergyModel allergyTable;
    private Date _CurrentDate;

    DrugAllergySortableTableView _drugAllergyTableView;


    EditText _drugAllergy;
    EditText _allergyReaction;
    Button _update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_drugallergy, container, false);
        getActivity().setTitle(R.string.drug_allergy);

        _drugAllergy = (EditText) view.findViewById(R.id.input_drugallergy);
        _allergyReaction = (EditText) view.findViewById(R.id.input_allergyreaction);
        _update = (Button) view.findViewById(R.id.btn_updateallergy);
        _drugAllergyTableView = (DrugAllergySortableTableView) view.findViewById(R.id.tableView_DrugAllergy);

        _CurrentDate = new Date();

        allergyTable = new patientAllergyModel(MainActivity.getUser(),
                _CurrentDate,
                "",
                "",
                0);

        _update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _CurrentDate = new Date();

                if (_update.getText().equals(getString(R.string.update_allergy))) {
                    if (!validate()) {
                        onValidateFailed();
                        return;
                    }
                    allergyTable.setAllergyReaction(_allergyReaction.getText().toString());
                    allergyTable.setDrugAllergy(_drugAllergy.getText().toString());
                    allergyTable.setPUsername(MainActivity.getUser().toLowerCase());
                    allergyTable.setDate(_CurrentDate);

                new AsyncDrugUpdate().execute(allergyTable);
                }
                else if(_update.getText().equals(getString(R.string.reset_input)))
                {
                    _drugAllergy.setText("");
                    _allergyReaction.setText("");
                    _allergyReaction.setFocusable(true);
                    _allergyReaction.setFocusableInTouchMode(true);
                    _drugAllergy.setFocusable(true);
                    _drugAllergy.setFocusableInTouchMode(true);
                    _update.setText(getString(R.string.update_allergy));
                }

            }
        });


        new AsyncDrugAllergyQueue().execute(allergyTable);

        return view;
    }


    protected class AsyncDrugAllergyQueue extends AsyncTask<patientAllergyModel, JSONObject, ArrayList<patientAllergyModel>> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);

        @Override
        protected ArrayList<patientAllergyModel> doInBackground(patientAllergyModel... params) {
            ArrayList<patientAllergyModel> tempList = new ArrayList<patientAllergyModel>();

            RestAPI api = new RestAPI();

            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.RetrieveAllergyInformation(params[0]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                tempList = parser.parseRetrieve_PatientAllergy(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncDrugAllergyQueue", e.getMessage());

            }
            return tempList;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Retrieving Drug Allergy List... Please Wait.");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<patientAllergyModel> returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {


                            if (_drugAllergyTableView != null) {
                                DrugAllergyTableDataAdapter queueTableAdapt = new DrugAllergyTableDataAdapter(getActivity(), returnResult, _drugAllergyTableView);
                                _drugAllergyTableView.setDataAdapter(queueTableAdapt);
                                _drugAllergyTableView.addDataClickListener(new TableDataClickListener<patientAllergyModel>() {
                                    @Override
                                    public void onDataClicked(int rowIndex, patientAllergyModel clickedData) {
                                        _update.setText(getString(R.string.reset_input));
                                        _allergyReaction.setFocusable(false);
                                        _drugAllergy.setFocusable(false);
                                        _allergyReaction.setText(clickedData.getAllergyReaction());
                                        _drugAllergy.setText(clickedData.getDrugAllergy());
                                        final String tempString = "Display Drug Allergy No : " + clickedData.getID() + ". Drug Allergy : " + clickedData.getDrugAllergy();
                                        Toast.makeText(getActivity(), tempString, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                _drugAllergyTableView.addDataLongClickListener(new TableDataLongClickListener<patientAllergyModel>() {
                                    @Override
                                    public boolean onDataLongClicked(int rowIndex, patientAllergyModel clickedData) {
                                        _update.setText(getString(R.string.reset_input));
                                        _allergyReaction.setFocusable(false);
                                        _drugAllergy.setFocusable(false);
                                        _allergyReaction.setText(clickedData.getAllergyReaction());
                                        _drugAllergy.setText(clickedData.getDrugAllergy());
                                        final String tempString = "Display Drug Allergy No : " + clickedData.getID() + ". Drug Allergy : " + clickedData.getDrugAllergy();
                                        Toast.makeText(getActivity(), tempString, Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                });

                                _drugAllergyTableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
                                    @Override
                                    public void onRefresh(RefreshIndicator refreshIndicator) {
                                        new AsyncDrugAllergyQueue().execute(allergyTable);
                                        refreshIndicator.hide();
                                    }
                                });


                            }

                            //No Additional Method Needed
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }

    protected class AsyncDrugUpdate extends AsyncTask<patientAllergyModel, JSONObject, Boolean> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);

        @Override
        protected Boolean doInBackground(patientAllergyModel... params) {
            Boolean tempResult = false;

            RestAPI api = new RestAPI();

            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.CreateDrugAllergy(params[0]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                tempResult = parser.parseCreateAllergy(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncDrugUpdate", e.getMessage());

            }
            return tempResult;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Adding New Drug Allergy ... Please Wait.");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            if (returnResult == true) {
                                onCreateSuccess();
                            } else {
                                onCreateFailed();
                            }

                            //No Additional Method Needed
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }

    public boolean validate() {
        boolean valid = true;

        try {
            String drugallergy = _drugAllergy.getText().toString();
            String allergyReaction = _allergyReaction.getText().toString();


            if (drugallergy.isEmpty()) {
                _drugAllergy.setError("Drug Allergy cannot be empty");
                valid = false;
            } else {
                _drugAllergy.setError(null);
            }

            if (allergyReaction.isEmpty()) {
                _allergyReaction.setError("Allergy Reaction cannot be empty");
                valid = false;
            } else {
                _allergyReaction.setError(null);
            }

        } catch (Exception e) {

        }


        return valid;
    }

    public void onValidateFailed() {
        Toast.makeText(getActivity(), "Please Check Inputs Again", Toast.LENGTH_LONG).show();
    }

    public void onCreateSuccess() {
        Toast.makeText(getActivity(), "Adding Drug Allergy Success", Toast.LENGTH_LONG).show();
        _allergyReaction.setText("");
        _drugAllergy.setText("");
        new AsyncDrugAllergyQueue().execute(allergyTable);
    }

    public void onCreateFailed() {
        Toast.makeText(getActivity(), "Adding Drug Allergy Failed", Toast.LENGTH_LONG).show();
    }
}
