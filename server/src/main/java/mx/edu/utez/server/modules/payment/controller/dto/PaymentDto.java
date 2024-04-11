package mx.edu.utez.server.modules.payment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.payment.module.Payment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    @NotNull
    @Positive
    private Double total;

    @NotNull
    @Positive
    private Double totalPaid;

    @NotNull
    @PositiveOrZero
    private Double discount;

    @NotNull
    @NotBlank
    private String charge;

    public Payment getPaymentEntity() {
        return new Payment(
                getTotal(),
                getTotalPaid(),
                getDiscount(),
                getCharge()
        );
    }
}