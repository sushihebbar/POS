package model;

public class ItemModel {
    private String itemCode;
    private String itemName;
    private int packSize;
    private Float mrp;

    public ItemModel() {
    }

    public ItemModel(String itemCode, String itemName, int packSize, Float mrp) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.packSize = packSize;
        this.mrp = mrp;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPackSize() {
        return packSize;
    }

    public Float getMrp() {
        return mrp;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPackSize(int packSize) {
        this.packSize = packSize;
    }

    public void setMrp(Float mrp) {
        this.mrp = mrp;
    }
}
