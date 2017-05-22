package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by flowerpower on 2017. 05. 04..
 */
public class OrderController {
    private static final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public static ModelAndView checkout(Request req, Response res) {
        Map params = new HashMap<>();
        Cart cart = new Cart();
        cart.initFromSession(req);
        params.put("cart", cart);

        root.info("Opened checkout page");
        return new ModelAndView(params, "order/checkout");
    }

    public static ModelAndView payment(Request req, Response res) {

        //Instantiate needed variables
        Map params = new HashMap<>();
        Cart cart = new Cart();
        cart.initFromSession(req);

        //Set changings on order and save
        cart.order.setByUser(
                req.queryParams("name"),
                req.queryParams("email"),
                req.queryParams("phoneNumber"),
                req.queryParams("billingAddress"),
                req.queryParams("shippingAddress"),
                req.queryParams("description")
        );
        cart.order.setStatus(1);
        cart.saveToSession(req);

        root.debug(
                "Order properties: {}, {}, {}, {}, {}, {}, {}",
                cart.order.getName(),
                cart.order.getEmail(),
                cart.order.getPhoneNumber(),
                cart.order.getBillingAddress(),
                cart.order.getShippingAddress(),
                cart.order.getDescription(),
                cart.order.getStatus()
        );

        params.put("cart", cart);

        return new ModelAndView(params, "order/payment");
    }

    public static ModelAndView confirmation(Request req, Response res) {

        //Instantiate needed variables
        Map params = new HashMap<>();
        Cart cart = new Cart();
        cart.initFromSession(req);

        //Set changings on order and save
        cart.order.setPaymentMethod(Integer.parseInt(req.queryParams("payment")));
        cart.order.setStatus(2);
        cart.dropSession(req.session());
        params.put("cart", cart);

        root.debug(
                "Order properties: {}, {}, {}, {}, {}, {}, {}",
                cart.order.getName(),
                cart.order.getEmail(),
                cart.order.getPhoneNumber(),
                cart.order.getBillingAddress(),
                cart.order.getShippingAddress(),
                cart.order.getDescription(),
                cart.order.getStatus()
        );

        OrderDaoJdbc.getInstance().add(
                cart.order.getName(),
                cart.order.getEmail(),
                cart.order.getPhoneNumber(),
                cart.order.getBillingAddress(),
                cart.order.getShippingAddress(),
                cart.order.getDescription(),
                cart.order.getDate(),
                cart.order.getPaymentMethod().getId(),
                cart.order.getStatus()
        );
        return new ModelAndView(params, "order/confirmation");
    }

}
