package entities;

/**
 * Created by Тёма on 10.04.2017.
 */
public class StaticAssetStatus {
    private String idstaticAssetStatus;

    public String getIdstaticAssetStatus() {
        return idstaticAssetStatus;
    }

    public void setIdstaticAssetStatus(String idstaticAssetStatus) {
        this.idstaticAssetStatus = idstaticAssetStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StaticAssetStatus that = (StaticAssetStatus) o;

        if (idstaticAssetStatus != null ? !idstaticAssetStatus.equals(that.idstaticAssetStatus) : that.idstaticAssetStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idstaticAssetStatus != null ? idstaticAssetStatus.hashCode() : 0;
    }
}
