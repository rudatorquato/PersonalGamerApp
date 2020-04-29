package modelo;

public class QrCode {
    String Images, Links, ImagesQrcode;

    public QrCode(String Images, String Links, String ImagesQrcode){
        this.Images = Images;
        this.Links = Links;
        this.ImagesQrcode = ImagesQrcode;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String Images) {
        this.Images = Images;
    }

    public String getLinks() {
        return Links;
    }

    public void setLinks(String Links) {
        this.Links = Links;
    }

    public String getImagesQrcode() {
        return ImagesQrcode;
    }

    public void setImagesQrcode(String ImagesQrcode) {
        this.ImagesQrcode = ImagesQrcode;
    }
}
