package ntu.cz3002advswen.com.getadoc.comparator;

import java.util.Comparator;

import ntu.cz3002advswen.com.getadoc.models.questionnaireTableModel;

public class QuestionnaireComparators {

    public QuestionnaireComparators() {
    }

    public static Comparator<questionnaireTableModel> getQueueNoCom() {
        return new QueueComparator();
    }

    public static Comparator<questionnaireTableModel> getClinicNameCom() {
        return new ClinicComparator();
    }

    public static Comparator<questionnaireTableModel> getDateCom() {
        return new DateComparator();
    }

    private static class QueueComparator implements Comparator<questionnaireTableModel> {

        @Override
        public int compare(final questionnaireTableModel question1, final questionnaireTableModel question2) {
            return Integer.compare(question1.getQID(), question2.getQID());
        }
    }

    private static class ClinicComparator implements Comparator<questionnaireTableModel> {

        @Override
        public int compare(final questionnaireTableModel question1, final questionnaireTableModel question2) {
            return question1.getCName().compareTo(question2.getCName());
        }
    }

    private static class DateComparator implements Comparator<questionnaireTableModel> {

        @Override
        public int compare(final questionnaireTableModel question1, final questionnaireTableModel question2) {
            long n1 = question1.getDate().getTime();
            long n2 = question2.getDate().getTime();
            if (n1 < n2) return -1;
            else if (n1 > n2) return 1;
            else return 0;
        }
    }
}
