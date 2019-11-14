package ntu.cz3002advswen.com.getadoc.datadapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;

import ntu.cz3002advswen.com.getadoc.models.queueTableModel;


public class QueueTableDataAdapter extends LongPressAwareTableDataAdapter<queueTableModel> {

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();


    public QueueTableDataAdapter(final Context context, final List<queueTableModel> data, final TableView<queueTableModel> tableView) {
        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final queueTableModel queue = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderClincID(queue);
                break;
            case 1:
                renderedView = renderClinicName(queue);
                break;
            case 2:
                renderedView = renderClincContact(queue);
                break;
            case 3:
                renderedView = renderQueueNo(queue);
                break;
            case 4:
                renderedView = renderValid(queue);
                break;

        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {

        final queueTableModel queue = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderClincID(queue);
                break;
            case 1:
                renderedView = renderClinicName(queue);
                break;
            case 2:
                renderedView = renderClincContact(queue);
                break;
            case 3:
                renderedView = renderQueueNo(queue);
                break;
            case 4:
                renderedView = renderValid(queue);
                break;

        }

        return renderedView;
    }


    private View renderClincID(final queueTableModel queue) {
        return renderString(queue.getRow().toString());
    }

    private View renderClincContact(final queueTableModel queue) {
        return renderString(queue.getClinicContact());
    }

    private View renderClinicName(final queueTableModel queue) {
        return renderString(queue.getClinicName());
    }

    private View renderQueueNo(final queueTableModel queue) {
        return renderString(queue.getQueueNo().toString());
    }

    private View renderValid(final queueTableModel queue) {
        return renderString(queue.getValid().toString());
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }


}
