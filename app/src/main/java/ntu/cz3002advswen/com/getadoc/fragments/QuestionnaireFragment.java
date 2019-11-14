package ntu.cz3002advswen.com.getadoc.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.activities.MainActivity;
import ntu.cz3002advswen.com.getadoc.models.questionnaireTableModel;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.customcontrol.QuestionnaireSortableTableView;
import ntu.cz3002advswen.com.getadoc.datadapter.QuestionnaireTableDataAdapter;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;


public class QuestionnaireFragment extends Fragment {

    QuestionnaireSortableTableView questionnaireTableView;

    private questionnaireTableModel questionnaireTable;

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_questionnaire, container, false);
        getActivity().setTitle(R.string.questionnaire_history);

        questionnaireTableView = (QuestionnaireSortableTableView) view.findViewById(R.id.tableView_QA);

        Date _tempDate = new Date();
        questionnaireTable = new questionnaireTableModel("", MainActivity.getUser().toString(),
                                                        "", -1, _tempDate,-1);

        new AsyncRetrieveQuestionnaire().execute(questionnaireTable);

        return view;
    }

    protected class AsyncRetrieveQuestionnaire extends AsyncTask<questionnaireTableModel, JSONObject, ArrayList<questionnaireTableModel>> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);

        @Override
        protected ArrayList<questionnaireTableModel> doInBackground(questionnaireTableModel... params) {
            ArrayList<questionnaireTableModel> tempList = new ArrayList<questionnaireTableModel>();

            RestAPI api = new RestAPI();

            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.RetrieveQuestionnaire(params[0]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                tempList = parser.parseRetrieve_QueestionnaireInformation(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncRetQuestionnaire", e.getMessage());

            }
            return tempList;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Retrieving Questionnaire List... Please Wait.");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<questionnaireTableModel> returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {



                            if (questionnaireTableView != null) {
                                QuestionnaireTableDataAdapter queueTableAdapt = new QuestionnaireTableDataAdapter(getActivity(), returnResult, questionnaireTableView);
                                questionnaireTableView.setDataAdapter(queueTableAdapt);
                                questionnaireTableView.addDataClickListener(new TableDataClickListener<questionnaireTableModel>() {
                                    @Override
                                    public void onDataClicked(int rowIndex, questionnaireTableModel clickedData) {
                                        final String carString = "Click: " + clickedData.getQID() + " / " + clickedData.getCName();
                                        Toast.makeText(getActivity(), carString, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                questionnaireTableView.addDataLongClickListener(new TableDataLongClickListener<questionnaireTableModel>() {
                                    @Override
                                    public boolean onDataLongClicked(int rowIndex, questionnaireTableModel clickedData) {
                                        final String carString = "Click: " + clickedData.getQID() + " / " + clickedData.getCName();
                                        Toast.makeText(getActivity(), carString, Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                });

                            }

                            //No Additional Method Needed
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }


}
