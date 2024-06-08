package com.example.binarfud.initialization;

import com.example.binarfud.entity.Merchant;
import com.example.binarfud.entity.Order;
import com.example.binarfud.entity.OrderDetail;
import com.example.binarfud.entity.Product;
import com.example.binarfud.entity.accounts.ERole;
import com.example.binarfud.entity.accounts.Role;
import com.example.binarfud.entity.accounts.User;
import com.example.binarfud.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @PostConstruct
    public void init() {
        initializeRoles();
        initializeUsers();
        initializeMerchantsAndProducts();
        initializeOrders();
    }

    private void initializeRoles() {
        if (!roleRepository.existsByName(ERole.ROLE_USER)) {
            Role userRole = new Role();
            userRole.setName(ERole.ROLE_USER);
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByName(ERole.ROLE_MERCHANT)) {
            Role merchantRole = new Role();
            merchantRole.setName(ERole.ROLE_MERCHANT);
            roleRepository.save(merchantRole);
        }
    }

    private void initializeUsers() {
        // Create a user with ROLE_USER
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setEmailAddress("usermail@mail.com");
            user.setPassword(passwordEncoder.encode("userpassword"));

            Set<Role> userRoles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
            userRoles.add(userRole);
            user.setRoles(userRoles);

            userRepository.save(user);
        }

        // Create a user with ROLE_MERCHANT
        if (!userRepository.existsByUsername("merchant")) {
            User merchant = new User();
            merchant.setUsername("merchant");
            merchant.setEmailAddress("merchantmail@mail.com");
            merchant.setPassword(passwordEncoder.encode("merchantpassword"));

            Set<Role> merchantRoles = new HashSet<>();
            Role merchantRole = roleRepository.findByName(ERole.ROLE_MERCHANT);
            merchantRoles.add(merchantRole);
            merchant.setRoles(merchantRoles);

            userRepository.save(merchant);
        }
    }

    private void initializeMerchantsAndProducts() {
        // Initialize Merchant
        Merchant merchant = new Merchant();
        merchant.setMerchantName("BinarFud Merchant Official");
        merchant.setMerchantLocation("BinarFud Merchant Jakarta");
        merchant.setOpen(true);
        merchantRepository.save(merchant);

        // Initialize Products
        initializeProducts(merchant);
    }

    private void initializeProducts(Merchant merchant) {
        List<Product> products = new ArrayList<>();

        // Add your products here
        products.add(createProduct("Nasi Goreng", 15000L, Product.ProductCategory.FOODS, merchant));
        products.add(createProduct("Mie Goreng", 13000L, Product.ProductCategory.FOODS, merchant));
        products.add(createProduct("Nasi + Ayam", 18000L, Product.ProductCategory.FOODS, merchant));
        products.add(createProduct("Es Teh Manis", 3000L, Product.ProductCategory.BEVERAGES, merchant));
        products.add(createProduct("Es Jeruk", 5000L, Product.ProductCategory.BEVERAGES, merchant));

        productRepository.saveAll(products);
    }

    private Product createProduct(String productName, Long price, Product.ProductCategory category, Merchant merchant) {
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setCategory(category);
        product.setMerchant(merchant);
        return product;
    }

    private void initializeOrders() {
        // Retrieve a user (you can retrieve an existing user or create a new one)
        User user = userRepository.findByUsername("user").orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve products (you can retrieve existing products or create new ones)
        List<Product> products = productRepository.findAll();

        // Create orders and order details
        for (int i = 0; i < 3; i++) { // Create 3 orders for demonstration
            Order order = new Order();
            order.setOrderTime(LocalDateTime.now());
            order.setDestinationAddress("Destination Address " + i);
            order.setUser(user);
            order.setCompleted(false);

            // Create order details for each order
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (int j = 0; j < 2; j++) { // Add 2 products to each order for demonstration
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(products.get(j)); // Add different products to each order
                orderDetail.setQuantity(1); // Set quantity (you can adjust as needed)
                orderDetail.setTotalPrice(products.get(j).getPrice()); // Set total price based on product price
                orderDetails.add(orderDetail);
            }

            order.setOrderDetails(orderDetails);
            orderRepository.save(order);
        }
    }
}
