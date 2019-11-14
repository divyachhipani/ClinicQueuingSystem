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
import ntu.cz3002advswen.com.getadoc.comparator.DrugAllergyComparators;
import ntu.cz3002advswen.com.getadoc.models.patientAllergyModel;


public class DrugAllergySortableTableView extends SortableTableView<patientAllergyModel> {

    public DrugAllergySortableTableView(final Context context) {
        this(context, null);
    }

    public DrugAllergySortableTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public DrugAllergySortableTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter
                (context, "No", "Drug Allergy", "Date");
        setColumnCount(3);

        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.accent));
        setHeaderAdapter(simpleTableHeaderAdapter);
        setSwipeToRefreshEnabled(true);

        final int rowColorEven = ContextCompat.getColor(context, R.color.table_data_row_even);
        final int rowColorOdd = ContextCompat.getColor(context, R.color.table_data_row_odd);
        setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        setHeaderBackgroundColor(getResources().getColor(R.color.primary));
        setHeaderElevation(10);

        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(5);
        tableColumnWeightModel.setColumnWeight(0, 5); // No
        tableColumnWeightModel.setColumnWeight(1, 5); // Drug Allergy
        tableColumnWeightModel.setColumnWeight(2, 5); // Date
        setColumnModel(tableColumnWeightModel);

        setColumnComparator(0, DrugAllergyComparators.getNoCom());
        setColumnComparator(1, DrugAllergyComparators.getDrugAllergyCom());
        setColumnComparator(2, DrugAllergyComparators.getDateCom());
    }


}
