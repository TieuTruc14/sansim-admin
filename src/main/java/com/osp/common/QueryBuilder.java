package com.osp.common;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private Query query;
    private StringBuffer sqlbuffer;
    private List<String> keys;
    private List<Object> values;
    private List<String> orderField;
    private List<String> orderVal;
    private List<String> groupByField;
    EntityManager em;

    public static final String EQ = "=";
    public static final String NE = "<>";
    public static final String LIKE = "LIKE";
    public static final String GT = ">";
    public static final String LT = "<";
    public static final String GE = ">=";
    public static final String LE = "<=";
    public static final String NOT_LIKE = "NOT LIKE";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT IN";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String DESC = "DESC";
    public static final String ASC = "ASC";

    public QueryBuilder(EntityManager em, StringBuffer sqlbuffer) {
        this.sqlbuffer = sqlbuffer;
        this.sqlbuffer.append(" WHERE 1 = 1 ");
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<Object>();
        this.orderField = new ArrayList<String>();
        this.orderVal = new ArrayList<String>();
        this.groupByField=new ArrayList<>();
        this.em = em;
    }

    public void sqlManual(String sql) {
        this.sqlbuffer.append(" " + sql + " ");
    }

    public void and(String operator, String key, Object value) {
        this.sqlbuffer.append(" AND (");
        this.sqlbuffer.append(" " + key + " ");
        this.sqlbuffer.append(" " + operator + " ");
        this.sqlbuffer.append(" ( :param_" + (keys.size() + 1) + " ) ");
        this.sqlbuffer.append(" )");
        this.keys.add("param_" + (keys.size() + 1) + "");
        this.values.add(value);
    }

    public void or(String operator, String key, Object value) {
        this.sqlbuffer.append(" OR (");
        this.sqlbuffer.append(" " + key + " ");
        this.sqlbuffer.append(" " + operator + " ");
        this.sqlbuffer.append("( :param_" + (keys.size() + 1) + " )");
        this.sqlbuffer.append(" )");
        this.keys.add("param_" + (keys.size() + 1) + "");
        this.values.add(value);
    }

    public void reference(String from, String to) {
        this.sqlbuffer.append(" AND " + from + " = " + to + " ");
    }

    public void addOrder(String key, String val) {
        this.orderField.add(key);
        this.orderVal.add(val);
    }
    public void addGroupBy(String key){
        this.groupByField.add(key);
    }


    public Query initQuery(Class cls) {
        genOrder();
        this.query = this.em.createQuery(sqlbuffer.toString(), cls);
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

    public Query initQuery(boolean order) {
        if(order){
            genOrder();
        }
        this.query = this.em.createQuery(sqlbuffer.toString());
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

    public Query initQueryHaveGroupBy(boolean order) {
        genGroupBy();
        if(order){
            genOrder();
        }
        this.query = this.em.createQuery(sqlbuffer.toString());
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

    public Query initNativeQueryHaveGroupBy(boolean order){
        genGroupBy();
        if(order){
            genOrder();
        }
        this.query = this.em.createNativeQuery(sqlbuffer.toString());
        if (this.keys.size() > 0) {
            for (int i = 0; i < this.keys.size(); i++) {
                this.query.setParameter(keys.get(i), values.get(i));
            }
        }
        return this.query;
    }

    private void genOrder(){
        if (this.orderField != null && this.orderField.size() > 0) {
            this.sqlbuffer.append(" ORDER BY ");
            for (int i = 0; i < this.orderField.size(); i++) {
                if (i == 0) {
                    this.sqlbuffer.append(" " + this.orderField.get(i) + " " + this.orderVal.get(i));
                } else {
                    this.sqlbuffer.append(" , " + this.orderField.get(i) + " " + this.orderVal.get(i));
                }
            }
        }
    }

    private void genGroupBy(){
        if(this.groupByField!=null && this.groupByField.size()>0){
            this.sqlbuffer.append(" GROUP BY ");
            for(int i=0;i<this.groupByField.size();i++){
                if(i==0){
                    this.sqlbuffer.append(" "+this.groupByField.get(i)+ " ");
                }else{
                    this.sqlbuffer.append(" , "+this.groupByField.get(i)+ " ");
                }
            }
        }
    }

    /*FOR NATIVE QUERY*/
//    public void andNative(String operator, String key, Object value) {
//        this.sqlbuffer.append(" AND (");
//        this.sqlbuffer.append(" " + key + " ");
//        this.sqlbuffer.append(" " + operator + " ");
//        this.sqlbuffer.append(" ( :param_" + (keys.size() + 1) + " ) ");
//        this.sqlbuffer.append(" )");
//        this.keys.add("param_" + (keys.size() + 1) + "");
//        this.values.add(value);
//    }
//
//    public void orNative(String operator, String key, Object value) {
//        this.sqlbuffer.append(" OR (");
//        this.sqlbuffer.append(" " + key + " ");
//        this.sqlbuffer.append(" " + operator + " ");
//        this.sqlbuffer.append("( :param_" + (keys.size() + 1) + " )");
//        this.sqlbuffer.append(" )");
//        this.keys.add("param_" + (keys.size() + 1) + "");
//        this.values.add(value);
//    }

    public void andOrListNative(List<ConditionObject> items){
        if(items!=null && items.size()>0){
            this.sqlbuffer.append(" AND (");
            for(int i=0;i<items.size();i++){
                this.sqlbuffer.append(" " + items.get(i).getKey() + " ");
                this.sqlbuffer.append(" " + items.get(i).getOperator() + " ");
//                this.sqlbuffer.append("( ?param_" + (keys.size() + 1) + " )");
                this.sqlbuffer.append("( :param_" + (keys.size() + 1) + " )");
//                this.sqlbuffer.append(" )");
                if((i+1)== items.size()){
                    this.sqlbuffer.append(" ) ");
                }else{
                    this.sqlbuffer.append(" OR ");
                }
                this.keys.add("param_" + (keys.size() + 1) + "");
                this.values.add(items.get(i).getValue());
            }
        }
    }

    public static class ConditionObject{
        private String operator;
        private String key;
        private Object value;

        public ConditionObject(String operator, String key, Object value) {
            this.operator = operator;
            this.key = key;
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
