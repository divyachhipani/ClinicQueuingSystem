package ntu.cz3002advswen.com.getadoc.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final int REQUEST_SUCCESSFUL = 0;

    @Bind(R.id.input_username)
    EditText _usernameText;

    @Bind(R.id.input_nric)
    EditText _nricText;

    @Bind(R.id.input_email)
    EditText _emailText;

    @Bind(R.id.input_hint)
    EditText _hint;

    @Bind(R.id.input_password)
    EditText _passwordText;

    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;

    @Bind(R.id.btn_signup)
    Button _signupButton;

    @Bind(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        String username = _usernameText.getText().toString();
        String nric = _nricText.getText().toString();
        String email = _emailText.getText().toString();
        String passwordhint = _hint.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
        new AsyncSignup().execute(username, nric, email, reEnterPassword, passwordhint);

    }


    protected class AsyncSignup extends AsyncTask<String, JSONObject, Boolean> {
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        ;

        @Override
        protected Boolean doInBackground(String... params) {

            RestAPI api = new RestAPI();
            boolean createSuccess = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.CreateNewAccount(params[0], params[1], params[2], params[3], params[4]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                createSuccess = parser.parseCreateUserAccount(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncSignup", e.getMessage());

            }
            return createSuccess;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            //Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean returnResult) {
            // TODO Auto-generated method stub
            final Boolean result = returnResult;
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or validateFailed
                            if (result == true) {
                                onSignupSuccess();
                            } else {
                                onSignupFailed();
                            }

                            progressDialog.dismiss();
                        }
                    }, 3000);
        }

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Create User Successful, Redirecting To Login Page", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, REQUEST_SUCCESSFUL);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Create User failed. Username, Email Or NRIC has been taken", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String nric = _nricText.getText().toString();
        String email = _emailText.getText().toString();
        String hint = _hint.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

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

        return valid;
    }
}