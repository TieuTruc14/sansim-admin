package com.osp.modelCustomer.view;

/**
 * Created by Admin on 2/1/2018.
 */
public class CacGoiCuoc {
    private long dangky;
    private long giahan;
    private long xacthuc;
    private long giahanxt;
    private long huyHT;
    private long huyNguoiDung;
    private long total;

    public CacGoiCuoc() {
    }

    public CacGoiCuoc(long dangky, long giahan, long xacthuc, long giahanxt, long huyHT, long huyNguoiDung, long total) {
        this.dangky = dangky;
        this.giahan = giahan;
        this.xacthuc = xacthuc;
        this.giahanxt = giahanxt;
        this.huyHT = huyHT;
        this.huyNguoiDung = huyNguoiDung;
        this.total = total;
    }

    public long getDangky() {
        return dangky;
    }

    public void setDangky(long dangky) {
        this.dangky = dangky;
    }

    public long getGiahan() {
        return giahan;
    }

    public void setGiahan(long giahan) {
        this.giahan = giahan;
    }

    public long getXacthuc() {
        return xacthuc;
    }

    public void setXacthuc(long xacthuc) {
        this.xacthuc = xacthuc;
    }

    public long getGiahanxt() {
        return giahanxt;
    }

    public void setGiahanxt(long giahanxt) {
        this.giahanxt = giahanxt;
    }

    public long getHuyHT() {
        return huyHT;
    }

    public void setHuyHT(long huyHT) {
        this.huyHT = huyHT;
    }

    public long getHuyNguoiDung() {
        return huyNguoiDung;
    }

    public void setHuyNguoiDung(long huyNguoiDung) {
        this.huyNguoiDung = huyNguoiDung;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
