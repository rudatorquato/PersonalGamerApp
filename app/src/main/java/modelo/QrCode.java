package modelo;

public class QrCode {
    private String image, links, imageQrcode;

    public QrCode(String image, String Links, String imageQrcode){
        this.image = image;
        this.links = Links;
        this.imageQrcode = imageQrcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String images) {
        this.image = images;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getImageQrcode() {
        return imageQrcode;
    }

    public void setImageQrcode(String imageQrcode) {
        this.imageQrcode = imageQrcode;
    }
}
