package lk.ijse.cmjd.project.dto;

public class PublisherDetailsDTO {
    private  String id;
    private String name;
    private String address;
    private int teleNo;

    public PublisherDetailsDTO() {
    }

    public PublisherDetailsDTO(String id, String name, String address, int teleNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.teleNo = teleNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTeleNo() {
        return teleNo;
    }

    public void setTeleNo(int teleNo) {
        this.teleNo = teleNo;
    }

    @Override
    public String toString() {
        return "PublisherDetailsDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", teleNo=" + teleNo +
                '}';
    }
}
