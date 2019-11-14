package ntu.cz3002advswen.com.getadoc.customcontrol;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;
import ntu.cz3002advswen.com.getadoc.R;
import ntu.cz3002advswen.com.getadoc.models.queueTableModel;
import ntu.cz3002advswen.com.getadoc.comparator.QueueComparators;


public class QueueSortableTableView extends SortableTableView<queueTableModel> {
    public QueueSortableTableView(final Context context) {
        this(context, null);
    }

    public QueueSortableTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public QueueSortableTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);


        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter
                (context, "ID", "Name", "Contact", "No", "Valid");

        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.table_header_text));
        setColumnCount(5);

        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.accent));
        setHeaderAdapter(simpleTableHeaderAdapter);

        final int rowColorEven = ContextCompat.getColor(context, R.color.table_data_row_even);
        final int rowColorOdd = ContextCompat.getColor(context, R.color.table_data_row_odd);
        setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        setHeaderBackgroundColor(getResources().getColor(R.color.primary));
        setHeaderElevation(10);

        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(5);
        tableColumnWeightModel.setColumnWeight(0, 2); // Queue ID
        tableColumnWeightModel.setColumnWeight(1, 3); // Clinic Name
        tableColumnWeightModel.setColumnWeight(2, 3); // Contact
        tableColumnWeightModel.setColumnWeight(3, 2); // Queue No
        tableColumnWeightModel.setColumnWeight(4, 2); // Valid
        setColumnModel(tableColumnWeightModel);


        setColumnComparator(0, QueueComparators.getRowCom());
        setColumnComparator(1, QueueComparators.getClinicNameCom());
        setColumnComparator(4, QueueComparators.getValidCom());
    }

}
