package model.DTO;

public class BillDTO {

    private String clientPNC;
    private String amount;

    public BillDTO(String clientPNC, String amount) {
        this.clientPNC = clientPNC;
        this.amount = amount;
    }

    public String getClientPNC() {
        return clientPNC;
    }

    public void setClientPNC(String clientPNC) {
        this.clientPNC = clientPNC;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
