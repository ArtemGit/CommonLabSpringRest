package entities;

/**
 * Created by Тёма on 10.04.2017.
 */
public class StaticGoodsGroup {
    private String groupname;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StaticGoodsGroup that = (StaticGoodsGroup) o;

        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return groupname != null ? groupname.hashCode() : 0;
    }
}
