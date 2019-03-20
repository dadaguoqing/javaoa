package com.genius;

/**
 * Class: AssetType
 * Description: TODO
 * Author: Genius
 * Date: 2019/3/20 10:38
 * Version: V1.0
 */
public class AssetType {
    private Integer id;
    private Integer pid;
    private String name;

    public AssetType() {
    }

    public AssetType(Integer id, Integer pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetType assetType = (AssetType) o;

        if (id != null ? !id.equals(assetType.id) : assetType.id != null) return false;
        if (pid != null ? !pid.equals(assetType.pid) : assetType.pid != null) return false;
        return name != null ? name.equals(assetType.name) : assetType.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AssetType{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
