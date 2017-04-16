package model;

import java.util.List;

/**
 * Created by LENOVO on 2/2/2017.
 */

public class WhereHelper {
    public List<String> getKey() {
        return key;
    }

    public void setKey(List<String> key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public List<String> getOperator() {
        return operator;
    }

    public void setOperator(List<String> operator) {
        this.operator = operator;
    }

    public List<String> getPenghubung() {
        return penghubung;
    }

    public void setPenghubung(List<String> penghubung) {
        this.penghubung = penghubung;
    }

    public WhereHelper(List<String> key, List<String> value, List<String> operator, List<String> penghubung) {
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.penghubung = penghubung;
    }

    private List<String> key;
    private List<String> value;
    private List<String> operator;
    private List<String> penghubung;
    public static String more_than = ">", less_than = "<", less_or_equal = "<=", more_or_equal = ">=", equal = "=", not_equal = "!=";
}
