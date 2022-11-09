package edu.upc.dsa.minim.UI.services;

import edu.upc.dsa.minim.Domain.Entity.Order;
import edu.upc.dsa.minim.Domain.Entity.Product;
import edu.upc.dsa.minim.Domain.Entity.User;
import edu.upc.dsa.minim.Domain.Repository.ProductManager;
import edu.upc.dsa.minim.Infraestructure.ProductManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Api(value = "/product", description = "Endpoint to Product Service")
@Path("/product")
public class ProductService {
    private ProductManager productManager;

    public ProductService() {
        this.productManager = ProductManagerImpl.getInstance();
        if (productManager.size()==0) {
            this.productManager.addProduct("B001", "Coca cola", 2);
            this.productManager.addProduct("C002", "Café amb gel", 1.5);
            this.productManager.addProduct("A002", "Donut", 2.25);
            this.productManager.addProduct("A003", "Croissant", 1.25);
        }
    }

    @GET
    @ApiOperation(value = "get all Products", notes = "Ordered by Price")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/price")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {

        List<Product> products = this.productManager.productsByPrice();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;
    }

    @GET
    @ApiOperation(value = "get all Products", notes = "Ordered by Sales")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/sales")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {

        List<Product> products = this.productManager.productsBySales();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;
    }

    @POST
    @ApiOperation(value = "add a new product", notes = "new Product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Product.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {

        if (product.getProductId()==null || product.getProductName()==null)  return Response.status(500).entity(product).build();
        this.productManager.addProduct(product.getProductId(), product.getProductName(), product.getPrice());
        return Response.status(201).entity(product).build();
    }

    @POST
    @ApiOperation(value = "add a new order", notes = "from User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(Order order) {

        if (order.getUserId()==null )  return Response.status(500).entity(order).build();
        this.productManager.addOrder(order);
        return Response.status(201).entity(order).build();
    }

    @PUT
    @ApiOperation(value = "process an Order", notes = "fifo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processOrder() {

        Order order = this.productManager.processOrder();
        return Response.status(201).entity(order).build();
    }

    @POST
    @ApiOperation(value = "add a new user", notes = "new User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {

        if (user.getUserId()==null || user.getUserName()==null)  return Response.status(500).entity(user).build();
        this.productManager.addUser(user.getUserId(), user.getUserName(), user.getUserSurname());
        return Response.status(201).entity(user).build();
    }

    @GET
    @ApiOperation(value = "get Product", notes = "From Id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("productId") String productId) {

        Product product = this.productManager.getProduct(productId);
        if (Objects.equals(product.getProductId(), "")) return Response.status(404).build();
        else  return Response.status(201).entity(product).build();
    }

    @GET
    @ApiOperation(value = "get orders processed from User", notes = "from User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrders(@PathParam("userId") String userId) {

        List<Order> orders = this.productManager.ordersByUser(userId);

        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(orders) {};
        return Response.status(201).entity(entity).build()  ;
    }
}