package com.seata.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品减少库存 DTO
 */
@Data
public class ProductReduceStockDTO implements Serializable {
    private static final long serialVersionUID = -9156857881431466728L;

    /**
     * 商品编号
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer amount;

}
