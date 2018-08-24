package com.osp.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/26/2017.
 */
public class PagingResult {
    private List<?> items = new ArrayList();
    private long rowCount = 0;
    private int numberPerPage = 25;
    private int pageNumber = 1;

    public PagingResult() {
    }

    public List<Integer> getPageList() {
        List<Integer> pages = new ArrayList();
        int from = this.pageNumber - 4;
        int to = this.pageNumber + 7;
        if(from < 0) {
            to -= from;
            from = 1;
        }

        if(from < 1) {
            from = 1;
        }

        if(to > this.getPageCount()) {
            to = this.getPageCount();
        }

        for(int i = from; i <= to; ++i) {
            pages.add(Integer.valueOf(i));
        }

        return pages;
    }

//    public int getPageCount() {
//        return (int)(Math.ceil((double)this.rowCount / (double)this.numberPerPage) + 1.0D);
//    }
    public int getPageCount() {
        return (int)(Math.ceil((double)this.rowCount / (double)this.numberPerPage));
    }

    public List<?> getItems() {
        return this.items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }

    public int getNumberPerPage() {
        return this.numberPerPage;
    }

    public void setNumberPerPage(int numberPerPage) {
        this.numberPerPage = numberPerPage;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
