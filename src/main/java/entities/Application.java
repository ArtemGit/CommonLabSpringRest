package entities;

import classes.ApplicationStatus;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Тёма on 10.04.2017.
 */
public class Application {
    private Date date;
    private int assetIdAsset;
    private int idapplication;
    private String fio;
    private String phone;
    private int status;
    private BigDecimal price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAssetIdAsset() {
        return assetIdAsset;
    }

    public void setAssetIdAsset(int assetIdAsset) {
        this.assetIdAsset = assetIdAsset;
    }

    public int getIdapplication() {
        return idapplication;
    }

    public void setIdapplication(int idapplication) {
        this.idapplication = idapplication;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (assetIdAsset != that.assetIdAsset) return false;
        if (idapplication != that.idapplication) return false;
        if (status != that.status) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (fio != null ? !fio.equals(that.fio) : that.fio != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + assetIdAsset;
        result = 31 * result + idapplication;
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
