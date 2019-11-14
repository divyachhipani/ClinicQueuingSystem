package ntu.cz3002advswen.com.getadoc.utilities;

import java.util.ArrayList;
import java.util.List;

import ntu.cz3002advswen.com.getadoc.models.queueTableModel;

/**
 * Created by Kelvin on 6/11/2017.
 */

public class DataFactory {



    public static List<queueTableModel> createQueue() {
        queueTableModel QQ1 = new queueTableModel("Username7", 1, "9401935", true);
        queueTableModel QQ2 = new queueTableModel("Username7", 2, "9401935", true);
        queueTableModel QQ3 = new queueTableModel("Username7", 3, "9401935", true);
        final List<queueTableModel> queueList = new ArrayList<>();
        queueList.add(QQ1);
        queueList.add(QQ2);
        queueList.add(QQ3);
        return  queueList;
    }

}
