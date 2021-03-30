package model.DTO;

public class TransferDTO {

    private String amount;
    private String senderPersonalNumericalCode;
    private String receiverPersonalNumericalCode;
    private Long senderAccountId;
    private Long receiverAccountId;

    public TransferDTO(String amount, String senderPersonalNumericalCode, String receiverPersonalNumericalCode) {
        this.amount = amount;
        this.senderPersonalNumericalCode = senderPersonalNumericalCode;
        this.receiverPersonalNumericalCode = receiverPersonalNumericalCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSenderPersonalNumericalCode() {
        return senderPersonalNumericalCode;
    }

    public void setSenderPersonalNumericalCode(String senderPersonalNumericalCode) {
        this.senderPersonalNumericalCode = senderPersonalNumericalCode;
    }

    public String getReceiverPersonalNumericalCode() {
        return receiverPersonalNumericalCode;
    }

    public void setReceiverPersonalNumericalCode(String receiverPersonalNumericalCode) {
        this.receiverPersonalNumericalCode = receiverPersonalNumericalCode;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }
}
