package com.richard.bankapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private String bvn;
}
