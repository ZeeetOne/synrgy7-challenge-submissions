package com.binarfud.binarfud_service.initialization;

import com.binarfud.binarfud_service.entity.Merchant;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.OrderDetail;
import com.binarfud.binarfud_service.entity.Product;
import com.binarfud.binarfud_service.entity.accounts.ERole;
import com.binarfud.binarfud_service.entity.accounts.Role;
import com.binarfud.binarfud_service.entity.accounts.User;
import com.binarfud.binarfud_service.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private Environment env;

    @Value("${app.data.initialized}")
    private boolean isInitialized;

    @PostConstruct
    public void init() {
        if (!isInitialized) {
            initializeRoles();
            initializeUsers();
            initializeMerchantsAndProducts();
            initializeOrder();
            setInitialized();
        }
    }

    private void setInitialized() {
        try {
            Properties properties = new Properties();
            properties.load(Files.newInputStream(Paths.get("src/main/resources/application.properties")));
            properties.setProperty("app.data.initialized", "true");
            properties.store(Files.newOutputStream(Paths.get("src/main/resources/application.properties")), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Merchant merchant = new Merchant();
        merchant.setName("BinarFud Merchant Official");
        merchant.setLocation("BinarFud Merchant Jakarta");
        merchant.setOpen(true);
        merchantRepository.save(merchant);

        initializeProducts(merchant);
    }

    private void initializeProducts(Merchant merchant) {
        List<Product> products = new ArrayList<>();

        products.add(createProduct("Nasi Goreng", 15000L, Product.ProductCategory.FOODS, merchant));
        products.add(createProduct("Mie Goreng", 13000L, Product.ProductCategory.FOODS, merchant));
        products.add(createProduct("Nasi + Ayam", 18000L, Product.ProductCategory.FOODS, merchant));
        products.add(createProduct("Es Teh Manis", 3000L, Product.ProductCategory.BAVERAGES, merchant));
        products.add(createProduct("Es Jeruk", 5000L, Product.ProductCategory.BAVERAGES, merchant));

        productRepository.saveAll(products);
    }

    private Product createProduct(String productName, Long price, Product.ProductCategory category, Merchant merchant) {
        Product product = new Product();
        product.setName(productName);
        product.setPrice(price);
        product.setCategory(category);
        product.setMerchant(merchant);
        return product;
    }

    private void initializeOrder() {
        User user = userRepository.findByUsername("user").orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products = productRepository.findAll();

        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setDestinationAddress("Destination Address");
        order.setUser(user);
        order.setCompleted(false);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(createOrderDetail(order, products, "Nasi Goreng", 2));
        orderDetails.add(createOrderDetail(order, products, "Mie Goreng", 1));
        orderDetails.add(createOrderDetail(order, products, "Nasi + Ayam", 1));
        orderDetails.add(createOrderDetail(order, products, "Es Jeruk", 4));

        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
    }

    private OrderDetail createOrderDetail(Order order, List<Product> products, String productName, int quantity) {
        Product product = products.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalPrice(product.getPrice() * quantity);
        return orderDetail;
    }
}
