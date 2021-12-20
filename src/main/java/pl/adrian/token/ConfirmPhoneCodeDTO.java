package pl.adrian.token;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class ConfirmPhoneCodeDTO {
    private UUID id;
    private UUID userId;
    private String code;
    private ZonedDateTime expiryDate;

    public ConfirmPhoneCodeDTO(UUID id, UUID userId, String code, ZonedDateTime expiryDate) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.expiryDate = expiryDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmPhoneCodeDTO that = (ConfirmPhoneCodeDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(code, that.code) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, code, expiryDate);
    }
}