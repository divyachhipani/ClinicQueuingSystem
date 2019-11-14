package ntu.cz3002advswen.com.getadoc.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPoint;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.activities.MainActivity;
import ntu.cz3002advswen.com.getadoc.activities.QuestionnaireActivity;
import ntu.cz3002advswen.com.getadoc.models.sgClinicsModel;
import ntu.cz3002advswen.com.getadoc.utilities.JSONParser;
import ntu.cz3002advswen.com.getadoc.utilities.PermissionUtils;
import ntu.cz3002advswen.com.getadoc.utilities.RestAPI;

public class MapsFragment extends Fragment implements
        OnMapReadyCallback,
        OnMarkerClickListener,
        OnInfoWindowClickListener,
        OnMapClickListener {


    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    private static final int REQUEST_SUCCESSFUL = 0;

    private GoogleMap mMap;
    private View locationView;


    /**
     * Cluster manager variables for marker clustering
     */
    private ClusterManager<sgClinicsModel> mClusterManager;

    /**
     * Variables for marker's info window
     */
    private View mWindow;
    TextView _tvQueueCountForSelectedMarker;
    Marker _SelectedMarker;
    FloatingActionButton fab;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        getActivity().setTitle(R.string.find_a_clinic);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.setVisibility(View.GONE);


        // Get the view for the buttons.
        locationView = mapFragment.getView();


        // Inflate the layout for this fragment
        return view;

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        openDialogForQueueAndQuestionaire();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        fab.setVisibility(View.GONE);
    }

    public void openDialogForQueueAndQuestionaire() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(getActivity(), QuestionnaireActivity.class);
                        //Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, REQUEST_SUCCESSFUL);
                        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Proceed To Register In Clinic").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    class CustomInfoWindowAdapter implements InfoWindowAdapter {
        sgClinicsModel tempClincs;

        CustomInfoWindowAdapter(sgClinicsModel tempClincs) {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            this.tempClincs = tempClincs;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            renderMarker(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        private void renderMarker(Marker marker, View view) {


            _SelectedMarker = marker;
            String txName = marker.getTitle();
            TextView _tvName = ((TextView) view.findViewById(R.id.name));
            if (!txName.equals("")) {
                _tvName.setText(txName);
            } else {
                _tvName.setText("");
            }

            TextView _tvTelephone = ((TextView) view.findViewById(R.id.telephone));
            if (!tempClincs.getTelephone().equals("")) {
                _tvTelephone.setText("Telephone : " + tempClincs.getTelephone());
            } else {
                _tvTelephone.setText("");
            }

            TextView _tvPostalCode = ((TextView) view.findViewById(R.id.postalcode));
            if (!tempClincs.getTelephone().equals("")) {
                _tvPostalCode.setText("Postal Code : " + tempClincs.getPostalCode());
            } else {
                _tvPostalCode.setText("");
            }

            TextView _tvSubType = ((TextView) view.findViewById(R.id.subtype));
            if (!tempClincs.getTelephone().equals("")) {
                String subTypeConcatString="";
                for(int i =0;i<tempClincs.getSubType().size();i++)
                {
                    subTypeConcatString+=tempClincs.getSubType().get(i) + " ";
                }
                _tvSubType.setText("Subsidy Type : " + subTypeConcatString);
            } else {
                _tvSubType.setText("");
            }

            marker.hideInfoWindow();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.getUiSettings().setCompassEnabled(true);

        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.getUiSettings().setAllGesturesEnabled(true);


        /**
         * Set Camera to Singapore
         */
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.2897934, 103.8536279), 10.0f));


        /**
         * Set Current Location Button to Bottom Right
         */
        if (locationView != null &&
                locationView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) locationView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            // position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        }

        /**
         * Cluster Manager for grouping markers
         */
        mClusterManager = new ClusterManager<sgClinicsModel>(getActivity(), mMap);
        mMap.setOnCameraIdleListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<sgClinicsModel>() {
            @Override
            public boolean onClusterClick(Cluster<sgClinicsModel> cluster) {
                Log.d("Cluster", "Cluster Clicked");

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(cluster.getPosition())
                        .zoom(15.5f)
                        .build();
                CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(update);
                return true;
            }
        });

        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<sgClinicsModel>() {
                    @Override
                    public boolean onClusterItemClick(final sgClinicsModel clusterItem) {
                        Log.d("Cluster Item", "Cluster Item Clicked");


                        new AsyncRetrieveQueue().execute(clusterItem.getHCICode());
                        MainActivity.setClinicID(clusterItem.getHCICode());
                        mMap.setInfoWindowAdapter(new MapsFragment.CustomInfoWindowAdapter(clusterItem));

                        fab.setVisibility(View.VISIBLE);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (clusterItem != null) {
                                    openDialogForQueueAndQuestionaire();
                                }
                            }
                        });
                        return false;
                    }
                });


        mMap.setOnMarkerClickListener(mClusterManager);

        mMap.setOnMapClickListener(this);


        /**
         * Methods
         */

        if (MainActivity._listClinic.size() == 0)
            retrieveFileFromResource();
        else {
           mClusterManager.addItems(MainActivity._listClinic);
        }
        enableMyLocation();


        /**
         * Bottom Left
         */
        /*View locationButton = ((View) locationView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom


        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);

        rlp.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
        rlp.addRule(RelativeLayout.ALIGN_END, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rlp.setMargins(30, 0, 0, 40);*/

        /**
         * Top Right
         */
        /*View locationButton = ((View) locationView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);*/

        /**
         * Bottom Right
         */
       /* View locationButton = ((View) locationView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);rlp.setMargins(0,0,30,30);*/
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }


    /**
     * Extracting Information from KML
     */
    private void retrieveFileFromResource() {

        try {
            ArrayList<sgClinicsModel> tempClincs = new ArrayList<>();
            KmlLayer kmlLayer = new KmlLayer(mMap, R.raw.singapore_chas_clinics, getActivity());
            kmlLayer.addLayerToMap();

            //Retrieve the first container in the KML layer
            KmlContainer container = kmlLayer.getContainers().iterator().next();

            //Retrieve a nested container within the first container
            container = container.getContainers().iterator().next();

            if (container == null) {
                return;
            } else {
                //Iterate placemarks within the nested container
                for (KmlPlacemark placemark : container.getPlacemarks()) {
                    if (placemark.getGeometry().getGeometryType().equals("Point")) {
                        KmlPoint point = (KmlPoint) placemark.getGeometry();
                        LatLng latLng = new LatLng(point.getGeometryObject().latitude, point.getGeometryObject().longitude);
                        tempClincs.add(new sgClinicsModel(latLng.latitude, latLng.longitude, placemark.getProperty("name"), placemark.getProperty("description")));
                    }
                }
            }

            mClusterManager.addItems(tempClincs);
            MainActivity._listClinic = (ArrayList<sgClinicsModel>) tempClincs.clone();
            kmlLayer.removeLayerFromMap();

        } catch (IOException IO_exception) {
            IO_exception.printStackTrace();
        } catch (XmlPullParserException XML_exception) {
            XML_exception.printStackTrace();
        }
    }


    protected class AsyncRetrieveQueue extends AsyncTask<String, JSONObject, ArrayList<Integer>> {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        ;

        @Override
        protected ArrayList<Integer> doInBackground(String... params) {
            ArrayList<Integer> tempList = new ArrayList<Integer>();

            RestAPI api = new RestAPI();
            Integer returnResult1 = -1;
            Integer returnResult2 = -1;

            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.RetrieveRemainQueueByClinicID(params[0]);
                JSONObject jsonObj2 = api.RetrieveTotalQueueByClinicID(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                returnResult1 = parser.parseRetrieveTotalQueueByClinicID(jsonObj);
                tempList.add(returnResult1);

                returnResult2 = parser.parseRetrieveTotalQueueByClinicID(jsonObj2);
                tempList.add(returnResult2);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncRetrieveQuestionnaire", e.getMessage());

            }
            return tempList;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Processing Queue Count...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final ArrayList<Integer> returnResult) {
            // TODO Auto-generated method stub
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            _tvQueueCountForSelectedMarker = ((TextView) mWindow.findViewById(R.id.queuecount));
                            if (returnResult.get(0) != -1) {
                                SpannableString countText = new SpannableString("In Queue :\t" + returnResult.get(0).toString() + " Person(s) \nTotal :\t\t" + returnResult.get(1).toString() + " Count(s)");
                                if (returnResult.get(0) < 5) {
                                    countText.setSpan(new ForegroundColorSpan(Color.rgb(52, 237, 61)), 0, countText.length(), 0);
                                } else if (returnResult.get(0) < 10) {
                                    countText.setSpan(new ForegroundColorSpan(Color.rgb(255, 102, 20)), 0, countText.length(), 0);
                                } else if (returnResult.get(0) < 20) {
                                    countText.setSpan(new ForegroundColorSpan(Color.rgb(216, 60, 60)), 0, countText.length(), 0);
                                }
                                _tvQueueCountForSelectedMarker.setText(countText);
                            } else {
                                _tvQueueCountForSelectedMarker.setText("");
                            }
                            _SelectedMarker.showInfoWindow();
                            //No Additional Method Needed
                            progressDialog.dismiss();
                        }
                    }, 1000);
        }

    }
}

