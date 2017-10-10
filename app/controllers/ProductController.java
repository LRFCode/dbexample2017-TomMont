package controllers;

import models.Product;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class ProductController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public ProductController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getProducts()
    {
        List<Product> products =
                jpaApi.em().createQuery("SELECT p FROM Product p ORDER BY productName", Product.class).getResultList();

        return ok(views.html.product.render(products));
    }

    @Transactional(readOnly = true)
    public Result getProduct(Integer id)
    {
        Product product =
                jpaApi.em().createQuery("SELECT p FROM Product p WHERE productId = :id", Product.class).setParameter("id", id).getSingleResult();
        return ok(views.html.editproduct.render(product));
    }
}