package com.task.banking.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDto {
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
}
