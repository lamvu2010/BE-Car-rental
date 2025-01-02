package com.example.be_car_rental.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import vn.payos.type.WebhookData;

@JsonDeserialize(
        builder = vn.payos.type.Webhook.WebhookBuilder.class
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Webhook {
    private @NonNull String code;
    private @NonNull String desc;
    private Boolean success;
    private @NonNull WebhookData data;
    private @NonNull String signature;
}
