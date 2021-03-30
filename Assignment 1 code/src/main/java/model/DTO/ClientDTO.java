package model.DTO;

public class ClientDTO {

    private String name;
    private Long personalNumericalCode;
    private String identityCardNumber;
    private String address;
    private String phoneNumber;

    public ClientDTO(String name, Long personalNumericalCode, String identityCardNumber, String address, String phoneNumber) {
        this.name = name;
        this.personalNumericalCode = personalNumericalCode;
        this.identityCardNumber = identityCardNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPersonalNumericalCode() {
        return personalNumericalCode;
    }

    public void setPersonalNumericalCode(Long personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
