package com.example.order.service;

public interface OrderService {
    /**
     * 创建订单
     *
     * @param userId 用户编号
     * @param productId 产品编号
     * @param price 价格
     * @return 订单编号
     */
    Integer createOrder(Long userId, Long productId, Integer amount, Integer price) throws Exception;

}
