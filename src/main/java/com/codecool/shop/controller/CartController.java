package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by flowerpower on 2017. 05. 02..
 */
public class CartController {

    private static final Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public static ModelAndView renderProducts(Request req, Response res) {
        Map params = new HashMap<>();
        Cart cart = new Cart();
        cart.initFromSession(req);

        params.put("cart", cart);

        root.debug("Your cart size is {} at the moment!", cart.getCartSize());
        return new ModelAndView(params, "cart/list");
    }

    public static String addProduct(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Cart cart = new Cart();
        cart.initFromSession(req);

        int id = Integer.parseInt(req.params("id"));
        Product productToAdd = productDataStore.find(id);
        cart.addToCart(productToAdd, 1);
        cart.saveToSession(req);

        Map params = new HashMap<>();
        params.put("cart", cart);

        root.debug("Your cart size is {} at the moment!", cart.getCartSize());
        root.debug("Added product", productToAdd.getName());

        return cart.getCartSize().toString();
    }

    public static ModelAndView removeProduct(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Cart cart = new Cart();
        cart.initFromSession(req);

        int id = Integer.parseInt(req.params("id"));
        cart.removeItem(productDataStore.find(id));
        cart.saveToSession(req);

        Map params = new HashMap<>();
        params.put("cart", cart);

        root.debug("Your cart size is {} at the moment!", cart.getCartSize());

        return new ModelAndView(params, "cart/list");
    }

    public static ModelAndView modifyProduct(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Cart cart = new Cart();
        cart.initFromSession(req);

        int id = Integer.parseInt(req.params("id"));
        int quantity = Integer.parseInt(req.queryParams("quantity"));
        cart.modifyItem(productDataStore.find(id), quantity);
        cart.saveToSession(req);

        Map params = new HashMap<>();
        params.put("cart", cart);

        root.debug("Your cart size is {} at the moment!", cart.getCartSize());
        return new ModelAndView(params, "cart/list");
    }
}
