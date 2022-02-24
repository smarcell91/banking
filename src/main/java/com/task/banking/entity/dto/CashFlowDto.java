package com.task.banking.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashFlowDto {
    private Long accountId;
    private double amount;
}
