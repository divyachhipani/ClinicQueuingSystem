package ntu.cz3002advswen.com.getadoc.comparator;

import java.util.Comparator;

import ntu.cz3002advswen.com.getadoc.models.patientAllergyModel;

public class DrugAllergyComparators {

    public DrugAllergyComparators() {
    }

    public static Comparator<patientAllergyModel> getNoCom() {
        return new QueueComparator();
    }

    public static Comparator<patientAllergyModel> getDrugAllergyCom() {
        return new DrugAllergyComparator();
    }

    public static Comparator<patientAllergyModel> getDateCom() {
        return new DateComparator();
    }

    private static class QueueComparator implements Comparator<patientAllergyModel> {

        @Override
        public int compare(final patientAllergyModel pat1, final patientAllergyModel pat2) {
            return Integer.compare(pat1.getID(), pat2.getID());
        }
    }

    private static class DrugAllergyComparator implements Comparator<patientAllergyModel> {

        @Override
        public int compare(final patientAllergyModel pat1, final patientAllergyModel pat2) {
            return pat1.getDrugAllergy().compareTo(pat2.getDrugAllergy());
        }
    }

    private static class DateComparator implements Comparator<patientAllergyModel> {

        @Override
        public int compare(final patientAllergyModel pat1, final patientAllergyModel pat2) {
            long n1 = pat1.getDate().getTime();
            long n2 = pat2.getDate().getTime();
            if (n1 < n2) return -1;
            else if (n1 > n2) return 1;
            else return 0;
        }
    }
}
