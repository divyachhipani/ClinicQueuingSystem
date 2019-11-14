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

import java.util.ArrayList;

import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.activities.MainActivity;
import ntu.cz3002advswen.com.getadoc.models.queueTableModel;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.customcontrol.QueueSortableTableView;
import ntu.cz3002advswen.com.getadoc.datadapter.QueueTableDataAdapter;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;


public class QueueFragment extends Fragment {

    private queueTableModel queueTable;
    QueueSortableTableView queueTableView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_queue, container, false);
        getActivity().setTitle(R.string.queue_ticket);

        queueTableView = (QueueSortableTableView) view.findViewById(R.id.tableView_Q);

        queueTable = new queueTableModel(MainActivity.getUser(),
                -1,
                "",
                Boolean.TRUE);

        new AsyncRetrieveQueue().execute(queueTable);

        return view;
    }

    protected class AsyncRetrieveQueue extends AsyncTask<queueTableModel, JSONObject, ArrayList<queueTableModel>> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);

        @Override
        protected ArrayList<queueTableModel> doInBackground(queueTableModel... params) {
            ArrayList<queueTableModel> tempList = new ArrayList<queueTableModel>();

            RestAPI api = new RestAPI();

            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.RetrieveQueueInformation(params[0]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                tempList = parser.parseRetrieve_QueueInformation(jsonObj);

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
            progressDialog.setMessage("Retrieving Queue List... Please Wait.");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<queueTableModel> returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {


                            if (queueTableView != null) {
                                QueueTableDataAdapter queueTableAdapt = new QueueTableDataAdapter(getActivity(), returnResult, queueTableView);
                                queueTableView.setDataAdapter(queueTableAdapt);
                                queueTableView.addDataClickListener(new TableDataClickListener<queueTableModel>() {
                                    @Override
                                    public void onDataClicked(int rowIndex, queueTableModel clickedData) {
                                        final String carString = "Click: " + clickedData.getRow() + " / " + clickedData.getQueueNo();
                                        Toast.makeText(getActivity(), carString, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                queueTableView.addDataLongClickListener(new TableDataLongClickListener<queueTableModel>() {
                                    @Override
                                    public boolean onDataLongClicked(int rowIndex, queueTableModel clickedData) {
                                        final String carString = "Click: " + clickedData.getRow() + " / " + clickedData.getQueueNo();
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
