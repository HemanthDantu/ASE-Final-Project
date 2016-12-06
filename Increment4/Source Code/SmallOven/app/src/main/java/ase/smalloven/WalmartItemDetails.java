package ase.smalloven;

/**
 * Created by daras on 03-Dec-16.
 */
public class WalmartItemDetails {
    private String Name;
    private String UPCNo;
    private String Description;
    private String Price;
    private String StandardShipRate;
    private String addToCartUrl;
    private String availableOnline;
    private String ItemID;
    private String Image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUPCNo() {
        return UPCNo;
    }

    public void setUPCNo(String UPCNo) {
        this.UPCNo = UPCNo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStandardShipRate() {
        return StandardShipRate;
    }

    public void setStandardShipRate(String standardShipRate) {
        StandardShipRate = standardShipRate;
    }

    public String getAddToCartUrl() {
        return addToCartUrl;
    }

    public void setAddToCartUrl(String addToCartUrl) {
        this.addToCartUrl = addToCartUrl;
    }

    public String getAvailableOnline() {
        return availableOnline;
    }

    public void setAvailableOnline(String availableOnline) {
        this.availableOnline = availableOnline;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
