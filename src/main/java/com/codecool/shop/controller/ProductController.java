package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    private static final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        Cart cart = new Cart();
        cart.initFromSession(req);

        Map params = new HashMap<>();
        params.put("cart", cart);
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", supplierDataStore.getAll());

        root.debug("category: {} | filter: {}", req.queryParams("category"), req.queryParams("supplier"));

        if (req.queryParams("category") != null) {
            params.put("filter", productCategoryDataStore.find(Integer.parseInt(req.queryParams("category"))));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(req.queryParams("category")))));

        } else if (req.queryParams("supplier") != null) {
            params.put("filter", supplierDataStore.find(Integer.parseInt(req.queryParams("supplier"))));
            params.put("products", productDataStore.getBy(supplierDataStore.find(Integer.parseInt(req.queryParams("supplier")))));

        } else {
            params.put("filter", productCategoryDataStore.find(1));
            params.put("products", productDataStore.getAll());

        }

        return new ModelAndView(params, "product/index");
    }

}
