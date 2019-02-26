package com.st.jdpolonio.inmobiliapp.models;

public class Photo {

    private String id;
    private String propertyId;
    private String imgurLink;
    private String deleteHash;

    public Photo() {
    }

    public Photo(String propertyId, String imgurLink, String deleteHash) {
        this.propertyId = propertyId;
        this.imgurLink = imgurLink;
        this.deleteHash = deleteHash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getImgurLink() {
        return imgurLink;
    }

    public void setImgurLink(String imgurLink) {
        this.imgurLink = imgurLink;
    }

    public String getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(String deleteHash) {
        this.deleteHash = deleteHash;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", imgurLink='" + imgurLink + '\'' +
                ", deleteHash='" + deleteHash + '\'' +
                '}';
    }
}
