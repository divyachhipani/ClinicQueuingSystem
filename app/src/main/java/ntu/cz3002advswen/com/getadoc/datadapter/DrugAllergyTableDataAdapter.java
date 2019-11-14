package ntu.cz3002advswen.com.getadoc.datadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;
import ntu.cz3002advswen.com.getadoc.models.patientAllergyModel;


public class DrugAllergyTableDataAdapter extends LongPressAwareTableDataAdapter<patientAllergyModel> {

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public DrugAllergyTableDataAdapter(final Context context, final List<patientAllergyModel> data, final TableView<patientAllergyModel> tableView) {
        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final patientAllergyModel queue = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderQueueNo(queue);
                break;
            case 1:
                renderedView = renderDrugAllergy(queue);
                break;
            case 2:
                renderedView = renderDate(queue);
                break;
        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {

        final patientAllergyModel queue = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderQueueNo(queue);
                break;
            case 1:
                renderedView = renderDrugAllergy(queue);
                break;
            case 2:
                renderedView = renderDate(queue);
                break;
        }

        return renderedView;
    }


    private View renderDate(final patientAllergyModel allergyModel) {
        return renderString(dateFormat.format(allergyModel.getDate()));
    }

    private View renderQueueNo(final patientAllergyModel allergyModel) {
        return renderString(allergyModel.getID().toString());
    }

    private View renderDrugAllergy(final patientAllergyModel allergyModel) {
        return renderString(allergyModel.getDrugAllergy());
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }


}
