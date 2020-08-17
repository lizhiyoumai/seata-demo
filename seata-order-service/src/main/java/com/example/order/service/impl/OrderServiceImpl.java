package com.example.order.service.impl;

import com.example.order.dao.OrderDao;
import com.example.order.entity.OrderDO;
import com.example.order.feign.AccountServiceFeignClient;
import com.example.order.feign.ProductServiceFeignClient;
import com.example.order.service.OrderService;
import com.seata.common.dto.AccountReduceBalanceDTO;
import com.seata.common.dto.ProductReduceStockDTO;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AccountServiceFeignClient accountService;
    @Autowired
    private ProductServiceFeignClient productService;

    @Override
    @GlobalTransactional
    public Integer createOrder(Long userId, Long productId, Integer amount, Integer price) throws Exception {

        log.info("[createOrder] 当前 XID: {}", RootContext.getXID());

        // 保存订单
        OrderDO order = OrderDO.builder().userId(userId).productId(productId).payAmount(amount * price).build();
        orderDao.saveOrder(order);
        log.info("[createOrder] 保存订单: {}", order.getId());

        // 扣减库存
        ProductReduceStockDTO productReduceStockDTO = new ProductReduceStockDTO();
        productReduceStockDTO.setProductId(productId);
        productReduceStockDTO.setAmount(amount);
        productService.reduceStock(productReduceStockDTO);

        // 扣减余额
        AccountReduceBalanceDTO accountReduceBalanceDTO = new AccountReduceBalanceDTO();
        accountReduceBalanceDTO.setUserId(userId);
        accountReduceBalanceDTO.setPrice(amount * price);
        accountService.reduceBalance(accountReduceBalanceDTO);

        // 返回订单编号
        return order.getId();
    }
}
