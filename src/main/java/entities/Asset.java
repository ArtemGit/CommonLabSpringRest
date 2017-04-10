package entities;

/**
 * Created by Тёма on 10.04.2017.
 */
public class Asset {
    private int idAsset;
    private String assetNameModel;
    private double assetCost;
    private String description;
    private String username;
    private String goodsGroupName;
    private String staticAssetStatusIdstaticAssetStatus;
    private String dateRegistration;
    private byte enable;

    public int getIdAsset() {
        return idAsset;
    }

    public void setIdAsset(int idAsset) {
        this.idAsset = idAsset;
    }

    public String getAssetNameModel() {
        return assetNameModel;
    }

    public void setAssetNameModel(String assetNameModel) {
        this.assetNameModel = assetNameModel;
    }

    public double getAssetCost() {
        return assetCost;
    }

    public void setAssetCost(double assetCost) {
        this.assetCost = assetCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoodsGroupName() {
        return goodsGroupName;
    }

    public void setGoodsGroupName(String goodsGroupName) {
        this.goodsGroupName = goodsGroupName;
    }

    public String getStaticAssetStatusIdstaticAssetStatus() {
        return staticAssetStatusIdstaticAssetStatus;
    }

    public void setStaticAssetStatusIdstaticAssetStatus(String staticAssetStatusIdstaticAssetStatus) {
        this.staticAssetStatusIdstaticAssetStatus = staticAssetStatusIdstaticAssetStatus;
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public byte getEnable() {
        return enable;
    }

    public void setEnable(byte enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Asset asset = (Asset) o;

        if (idAsset != asset.idAsset) return false;
        if (Double.compare(asset.assetCost, assetCost) != 0) return false;
        if (enable != asset.enable) return false;
        if (assetNameModel != null ? !assetNameModel.equals(asset.assetNameModel) : asset.assetNameModel != null)
            return false;
        if (description != null ? !description.equals(asset.description) : asset.description != null) return false;
        if (username != null ? !username.equals(asset.username) : asset.username != null) return false;
        if (goodsGroupName != null ? !goodsGroupName.equals(asset.goodsGroupName) : asset.goodsGroupName != null)
            return false;
        if (staticAssetStatusIdstaticAssetStatus != null ? !staticAssetStatusIdstaticAssetStatus.equals(asset.staticAssetStatusIdstaticAssetStatus) : asset.staticAssetStatusIdstaticAssetStatus != null)
            return false;
        if (dateRegistration != null ? !dateRegistration.equals(asset.dateRegistration) : asset.dateRegistration != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idAsset;
        result = 31 * result + (assetNameModel != null ? assetNameModel.hashCode() : 0);
        temp = Double.doubleToLongBits(assetCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (goodsGroupName != null ? goodsGroupName.hashCode() : 0);
        result = 31 * result + (staticAssetStatusIdstaticAssetStatus != null ? staticAssetStatusIdstaticAssetStatus.hashCode() : 0);
        result = 31 * result + (dateRegistration != null ? dateRegistration.hashCode() : 0);
        result = 31 * result + (int) enable;
        return result;
    }
}
