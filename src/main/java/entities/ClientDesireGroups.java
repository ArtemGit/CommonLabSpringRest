package entities;

/**
 * Created by Тёма on 10.04.2017.
 */
public class ClientDesireGroups {
    private int idclientDesireGroups;
    private String groupGoods;
    private String username;

    public int getIdclientDesireGroups() {
        return idclientDesireGroups;
    }

    public void setIdclientDesireGroups(int idclientDesireGroups) {
        this.idclientDesireGroups = idclientDesireGroups;
    }

    public String getGroupGoods() {
        return groupGoods;
    }

    public void setGroupGoods(String groupGoods) {
        this.groupGoods = groupGoods;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientDesireGroups that = (ClientDesireGroups) o;

        if (idclientDesireGroups != that.idclientDesireGroups) return false;
        if (groupGoods != null ? !groupGoods.equals(that.groupGoods) : that.groupGoods != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idclientDesireGroups;
        result = 31 * result + (groupGoods != null ? groupGoods.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
