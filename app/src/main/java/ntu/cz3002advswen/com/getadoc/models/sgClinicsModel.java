/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ntu.cz3002advswen.com.getadoc.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class sgClinicsModel implements ClusterItem {
    private LatLng _mPosition;
    private String _mName;
    private String _mDescription;
    private String _mPostalCode;
    private String _mTelephone;
    private String _mHCICode;
    private String _mAddress;
    private List<String> _mSubType = new ArrayList<String>();

    //region Constructors

    public sgClinicsModel() {

    }

    public sgClinicsModel(double lat, double lng) {
        _mPosition = new LatLng(lat, lng);
        _mName = null;
    }

    public sgClinicsModel(double lat, double lng, String title) {
        _mPosition = new LatLng(lat, lng);
        _mName = title;
    }

    public sgClinicsModel(double lat, double lng, String title, String Description) {
        _mPosition = new LatLng(lat, lng);
        _mName = title;
        _mDescription = Description;
        processHTML();
    }

    //endregion

    //region Getter Setter

    @Override
    public LatLng getPosition() {
        return _mPosition;
    }

    @Override
    public String getTitle() {
        return _mName;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public String getDescription() {
        return _mDescription;
    }

    public String getPostalCode() {
        return _mPostalCode;
    }

    public String getHCICode() {
        return _mHCICode;
    }

    public String getAddress() {
        return _mAddress;
    }

    public String getTelephone() {
        return _mTelephone;
    }

    public List<String> getSubType() {
        return _mSubType;
    }

    public void setTitle(String title) {
        this._mName = title;
    }

    public void setPosition(LatLng mPos) {
        this._mPosition = mPos;
    }

    public void setName(String mName) {
        this._mName = mName;
    }

    public void setDescription(String mDescription) {
        this._mDescription = mDescription;
    }

    public void setPostalCode(String mPostalCode) {
        this._mPostalCode = mPostalCode;
    }

    public void setHCICode(String mHCICode) {
        this._mHCICode = mHCICode;
    }

    public void setAddress(String mAddress) {
        this._mAddress = mAddress;
    }

    public void setTelephone(String mTelephone) {
        this._mTelephone = mTelephone;
    }

    //endregion

    //region Methods

    public void processHTML() {
        Document doc = Jsoup.parse(getDescription());
        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");

        if ((rows != null) && (rows.size() > 0)) {
            //HCI Code
            Elements col3 = rows.get(3).select("td");
            setHCICode((!((col3.get(1).text()).equals("")) ? col3.get(1).text() : ""));

            //Telephone
            Elements col6 = rows.get(6).select("td");
            setTelephone((!((col6.get(1).text()).equals("")) ? col6.get(1).text() : ""));

            //PostalCode
            Elements col7 = rows.get(7).select("td");
            setPostalCode((!((col7.get(1).text()).equals("")) ? col7.get(1).text() : ""));

            // Subsidies Type
            Elements col14 = rows.get(14).select("td");
            List<String> subType = Arrays.asList(col14.get(1).text().split(","));
            for (Iterator<String> i = subType.iterator(); i.hasNext(); ) {
                String item = i.next();
                getSubType().add(item);
            }

            Elements col19 = rows.get(9).select("td");
            Elements col112 = rows.get(12).select("td");
            Elements col110 = rows.get(10).select("td");
            Elements col111 = rows.get(11).select("td");

            if ((!col19.get(1).text().equals("&lt;Null&gt;")) && (!col112.get(1).text().equals("&lt;Null&gt;"))) {
                if ((!col110.get(1).text().equals("&lt;Null&gt;")) && (!col111.get(1).text().equals("&lt;Null&gt;"))) {
                    String tempString = "Blk " + col19.get(1).text() + ", " + col112.get(1).text().toUpperCase() + ", SINGAPORE " + getPostalCode();
                    setAddress(tempString);
                } else {
                    String tempString = "Blk " + col19.get(1).text() + ", " + col112.get(1).text().toUpperCase() + " # " + col110.get(1).text() + "-" + col111.get(1).text() + ", SINGAPORE " + getPostalCode();
                    setAddress(tempString);
                }
            }
        }
    }
    //endregion
}
