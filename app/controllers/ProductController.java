package controllers;

import models.Category;
import models.Product;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

public class ProductController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public ProductController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
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
    public Result searchProducts()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String productName = form.get("productname");

        if (productName == null)
        {
            productName = "";
        }

        List<Product> products =
                jpaApi.em().createQuery("SELECT p FROM Product p WHERE productname LIKE :productname ORDER BY productName", Product.class).
                        setParameter("productname", "%" + productName + "%").
                        getResultList();

        return ok(views.html.productsearch.render(products));
    }


    @Transactional(readOnly = true)
    public Result getProduct(Integer id)
    {
        Product product =
                jpaApi.em().createQuery("SELECT p FROM Product p WHERE productId = :id", Product.class).setParameter("id", id).getSingleResult();

        List<Category> categories =
                jpaApi.em().createQuery("SELECT c FROM Category c ORDER BY categoryName", Category.class).getResultList();

        return ok(views.html.editproduct.render(product, categories));
    }

    @Transactional
    public Result updateProduct(Integer id)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String productName = form.get("productname");
        BigDecimal unitPrice = new BigDecimal(form.get("unitprice"));
        int unitsInStock = new Integer(form.get("unitsinstock"));
        int unitsOnOrder = new Integer(form.get("unitsonorder"));

        Product product =
                jpaApi.em().createQuery("SELECT p FROM Product p WHERE productId = :id", Product.class).setParameter("id", id).getSingleResult();

        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setUnitsInStock(unitsInStock);
        product.setUnitsOnOrder(unitsOnOrder);

        jpaApi.em().persist(product);

        return redirect(routes.ProductController.getProducts());
    }

    @Transactional
    public Result addProduct()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String productName = form.get("productname");
        BigDecimal unitPrice = new BigDecimal(form.get("unitprice"));
        int unitsInStock = new Integer(form.get("unitsinstock"));
        int unitsOnOrder = new Integer(form.get("unitsonorder"));
        int categoryId = new Integer(form.get("categoryId"));

        Product product = new Product();

        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setUnitsInStock(unitsInStock);
        product.setUnitsOnOrder(unitsOnOrder);
        product.setCategoryId(categoryId);

        jpaApi.em().persist(product);

        return redirect(routes.ProductController.getProducts());
    }

    @Transactional(readOnly = true)
    public Result newProduct()
    {
        Product product = new Product();
        List<Category> categories =
                jpaApi.em().createQuery("SELECT c FROM Category c ORDER BY categoryName", Category.class).getResultList();

        return ok(views.html.editproduct.render(product, categories));
    }
}