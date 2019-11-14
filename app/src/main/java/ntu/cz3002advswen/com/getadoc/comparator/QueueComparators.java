package ntu.cz3002advswen.com.getadoc.comparator;

import java.util.Comparator;

import ntu.cz3002advswen.com.getadoc.models.queueTableModel;

public class QueueComparators {

    public QueueComparators() {
    }

    public static Comparator<queueTableModel> getRowCom() {
        return new RowComparator();
    }

    public static Comparator<queueTableModel> getClinicNameCom() {
        return new ClinicComparator();
    }

    public static Comparator<queueTableModel> getValidCom() {
        return new ValidComparator();
    }

    private static class RowComparator implements Comparator<queueTableModel> {

        @Override
        public int compare(final queueTableModel queue1, final queueTableModel queue2) {
            return Integer.compare(queue1.getRow(), queue2.getRow());
        }
    }

    private static class ClinicComparator implements Comparator<queueTableModel> {

        @Override
        public int compare(final queueTableModel queue1, final queueTableModel queue2) {
            return queue1.getClinicName().compareTo(queue2.getClinicName());
        }
    }

    private static class ValidComparator implements Comparator<queueTableModel> {

        @Override
        public int compare(final queueTableModel queue1, final queueTableModel queue2) {
                return  Boolean.valueOf(queue1.getValid()).compareTo(Boolean.valueOf(queue2.getValid()));

        }
    }
}
