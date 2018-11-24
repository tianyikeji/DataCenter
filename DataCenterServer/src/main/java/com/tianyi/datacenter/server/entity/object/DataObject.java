package com.tianyi.datacenter.server.entity.object;

/**
 * 数据对象
 *
 * @author wenxinyan
 * @version 0.1
 */
public class DataObject {
    private int id;
    private String name;
    private String type;
    private String defined;
    private String isDic;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefined() {
        return defined;
    }

    public void setDefined(String defined) {
        this.defined = defined;
    }

    public String getIsDic() {
        return isDic;
    }

    public void setIsDic(String isDic) {
        this.isDic = isDic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
