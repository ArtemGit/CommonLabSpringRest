package entities;

/**
 * Created by Тёма on 10.04.2017.
 */
public class AssetImage {
    private int idassetImage;
    private String nameImage;
    private int assetIdAsset;

    public int getIdassetImage() {
        return idassetImage;
    }

    public void setIdassetImage(int idassetImage) {
        this.idassetImage = idassetImage;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public int getAssetIdAsset() {
        return assetIdAsset;
    }

    public void setAssetIdAsset(int assetIdAsset) {
        this.assetIdAsset = assetIdAsset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetImage that = (AssetImage) o;

        if (idassetImage != that.idassetImage) return false;
        if (assetIdAsset != that.assetIdAsset) return false;
        if (nameImage != null ? !nameImage.equals(that.nameImage) : that.nameImage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idassetImage;
        result = 31 * result + (nameImage != null ? nameImage.hashCode() : 0);
        result = 31 * result + assetIdAsset;
        return result;
    }
}
