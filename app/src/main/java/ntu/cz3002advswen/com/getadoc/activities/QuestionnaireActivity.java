package ntu.cz3002advswen.com.getadoc.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.models.questionnaireTableModel;
import ntu.cz3002advswen.com.getadoc.models.queueTableModel;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;
import ntu.cz3002advswen.com.getadoc.datadapter.SimpleListAdapter;


public class QuestionnaireActivity extends AppCompatActivity {

    private static final String TAG = "QuestionnaireActivity";
    private static final int REQUEST_SUCCESSFUL = 0;

    private SearchableSpinner mSearchableSpinner;
    private SimpleListAdapter mSimpleListAdapter;
    private final ArrayList<String> mStrings = new ArrayList<>();

    @Bind(R.id.input_symptoms)
    EditText _symptoms;
    @Bind(R.id.tbCough)
    ToggleButton _cough;
    @Bind(R.id.tbFever)
    ToggleButton _fever;
    @Bind(R.id.tbFlu)
    ToggleButton _fly;
    @Bind(R.id.btn_Submit)
    Button _submit;

    private static String _selectedCountry = "";
    private queueTableModel queueTable;
    private questionnaireTableModel questionTable;

    ProgressDialog progressDialog = null;
    WorkCounter wc_Count;

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
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(QuestionnaireActivity.this, R.style.AppTheme_Dark_Dialog);
        wc_Count = new WorkCounter(2, QuestionnaireActivity.this);
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = new Date();
                questionTable = new questionnaireTableModel(MainActivity.getUser(),
                        _selectedCountry,
                        _symptoms.getText().toString(),
                        MainActivity.getclinicID(),
                        -1,
                        _fever.isChecked(),
                        _fly.isChecked(),
                        _cough.isChecked(),
                        date);

                queueTable = new queueTableModel(MainActivity.getUser(),
                        -1,
                        MainActivity.getclinicID(),
                        Boolean.TRUE);

                new AsyncQuestionnaire().execute(questionTable);
                new AsyncQueue().execute(queueTable);

            }
        });


        initListValues();
        mSimpleListAdapter = new SimpleListAdapter(this, mStrings);
        mSearchableSpinner = (SearchableSpinner) findViewById(R.id.SearchableSpinner2);
        mSearchableSpinner.setAdapter(mSimpleListAdapter);
        mSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    private void initListValues() {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !mStrings.contains(country)) {
                mStrings.add(country);
            }
        }

        Collections.sort(mStrings);
        for (String country : mStrings) {
        }
    }


    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int position, long id) {
            //Toast.makeText(QuestionnaireActivity.this, "Item on position " + position + " : " + mSimpleListAdapter.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
            _selectedCountry = mSimpleListAdapter.getItem(position).toString();
        }

        @Override
        public void onNothingSelected() {
            //Toast.makeText(QuestionnaireActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            _selectedCountry = "";
        }
    };

    protected class AsyncQuestionnaire extends AsyncTask<questionnaireTableModel, JSONObject, Boolean> {

        @Override
        protected Boolean doInBackground(questionnaireTableModel... params) {

            RestAPI api = new RestAPI();
            boolean CreateQuestionnaireResult = false;
            try {

                JSONObject jsonObj = api.CreateQuestionnaire(params[0]);

                JSONParser parser = new JSONParser();

                CreateQuestionnaireResult = parser.parseCreateQuestionnaire(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncQuestionnaire", e.getMessage());

            }
            return CreateQuestionnaireResult;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Submitting Questionnaire & Obtaining Queue From Clinic...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Boolean returnResult) {
            final Boolean result = returnResult;
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or validateFailed
                            if (result == true) {
                                wc_Count.taskFinished();
                                Toast.makeText(QuestionnaireActivity.this, "Questionnaire Submitted... Thank You", Toast.LENGTH_SHORT).show();
                            } else {
                                validateQuestionFailed();
                            }

                        }
                    }, 1000);
        }

    }

    protected class AsyncQueue extends AsyncTask<queueTableModel, JSONObject, String> {


        @Override
        protected String doInBackground(queueTableModel... params) {

            RestAPI api = new RestAPI();
            String CreateQueue = "";
            try {

                queueTableModel qtTable = new queueTableModel(params[0].getUsername(), params[0].getQueueNo(), params[0].getClinicID(), Boolean.TRUE);

                JSONObject jsonObj2 = api.CreateQueue(qtTable);

                JSONParser parser2 = new JSONParser();

                CreateQueue = parser2.parseCreateQueue(jsonObj2);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncQueue", e.getMessage());

            }
            return CreateQueue;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            /*progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Obtaining Queue...");
            progressDialog.show();*/

        }

        @Override
        protected void onPostExecute(String returnResult) {
            final String result_b4Sep = returnResult;
            Boolean result_Create = false;
            Integer QueueNo = -1;
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or validateFailed
                            List<String> splitString = new ArrayList<String>(Arrays.asList(result_b4Sep.split(",")));
                            if (splitString.get(0).toString().equals("true")) {
                                wc_Count.taskFinished();
                                Toast.makeText(QuestionnaireActivity.this, "Queue Obtained...Queue Issued : " + splitString.get(1) , Toast.LENGTH_SHORT).show();
                            } else {
                                validateQueueFailed();
                            }
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }

    public void validateQuestionFailed() {
        Toast.makeText(getBaseContext(), "Create Questionnaire failed", Toast.LENGTH_LONG).show();
    }

    public void validateQueueFailed() {
        Toast.makeText(getBaseContext(), "Obtaining Queue failed", Toast.LENGTH_LONG).show();
    }




    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(false);
    }




}


