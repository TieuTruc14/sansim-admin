package com.osp.modelCustomer.view;

import java.util.List;

/**
 * Created by Admin on 2/2/2018.
 */
public class BieuDo {
    private List<String> names;
    private List<Long> values;

    public BieuDo() {
    }

    public BieuDo(List<String> names, List<Long> values) {
        this.names = names;
        this.values = values;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
}
