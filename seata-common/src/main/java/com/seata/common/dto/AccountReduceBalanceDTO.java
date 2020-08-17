package com.seata.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 账户减少余额 DTO
 */
@Data
public class AccountReduceBalanceDTO implements Serializable {
    private static final long serialVersionUID = -6444158397057712443L;
    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 扣减金额
     */
    private Integer price;

}
