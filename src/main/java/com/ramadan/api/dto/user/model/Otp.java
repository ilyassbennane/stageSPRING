package com.ramadan.api.dto.user.model;

import java.text.DecimalFormat;
import java.util.Random;

import lombok.Data;

@Data
public class Otp {
    private String description;
    private String value;

    public Otp() {
        this.value = generateOTP();
    }
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}
