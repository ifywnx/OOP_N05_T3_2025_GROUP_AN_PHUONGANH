package com.example.servingwebcontent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * 💾 Database Configuration
 * 
 * Cấu hình database cho từng environment:
 * - Development: H2 in-memory database
 * - Test: H2 in-memory với isolated data
 * - Production: MySQL cloud database
 * 
 * Features:
 * - Multi-profile database configuration
 * - Connection pooling optimization
 * - Transaction management
 * - JPA repository scanning
 * 
 * @author Phenikaa Student
 * @version 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.servingwebcontent.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    // =================== DEVELOPMENT PROFILE (H2) ===================
    
    /**
     * 🔧 Development DataSource - H2 In-Memory Database
     * 
     * Ưu điểm:
     * - Nhanh chóng setup và test
     * - Không cần cài đặt database server
     * - Auto-reset mỗi lần restart application
     * - H2 Console để debug SQL queries
     */
    @Bean
    @Profile("dev")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource devDataSource() {
        System.out.println("🔧 [DEV] Configuring H2 In-Memory Database...");
        
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:bakerydb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;" +
                     "INIT=CREATE SCHEMA IF NOT EXISTS bakery")
                .username("sa")
                .password("")
                .build();
    }

    // =================== TEST PROFILE (H2) ===================
    
    /**
     * 🧪 Test DataSource - H2 In-Memory Database (Isolated)
     * 
     * Đặc điểm:
     * - Isolated database cho mỗi test
     * - Không có sample data để test thuần túy
     * - Fast rollback sau mỗi test
     */
    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        System.out.println("🧪 [TEST] Configuring H2 Test Database...");
        
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL")
                .username("sa")
                .password("")
                .build();
    }

    // =================== PRODUCTION PROFILE (MYSQL) ===================
    
    /**
     * 🚀 Production DataSource - MySQL Cloud Database
     * 
     * Configuration cho production deployment:
     * - MySQL trên cloud (Aiven, AWS RDS, etc.)
     * - Connection pooling optimization
     * - SSL connection
     * - Persistent data storage
     */
    @Bean
    @Profile("prod")
    public DataSource prodDataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        
        System.out.println("🚀 [PROD] Configuring MySQL Cloud Database...");
        System.out.println("📍 Database URL: " + url.replaceAll("password=[^&]*", "password=***"));
        
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    // =================== STAGING PROFILE (MYSQL) ===================
    
    /**
     * 🎭 Staging DataSource - MySQL Testing Environment
     * 
     * Environment để test production deployment:
     * - Giống production nhưng ít data hơn
     * - Testing performance và scaling
     * - UAT (User Acceptance Testing)
     */
    @Bean
    @Profile("staging")
    public DataSource stagingDataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        
        System.out.println("🎭 [STAGING] Configuring MySQL Staging Database...");
        
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    // =================== DATABASE INITIALIZATION CONFIGURATION ===================
    
    /**
     * 📊 Database Initialization Properties
     * 
     * Control database initialization behavior:
     * - Sample data creation
     * - Schema management
     * - Data migration
     */
    @ConfigurationProperties(prefix = "bakery.data")
    public static class DataInitializationProperties {
        
        private Sample sample = new Sample();
        
        public static class Sample {
            private boolean enabled = true;
            private int products = 20;
            private int customers = 10;
            private int employees = 5;
            private int transactions = 15;
            
            // Getters and Setters
            public boolean isEnabled() { return enabled; }
            public void setEnabled(boolean enabled) { this.enabled = enabled; }
            
            public int getProducts() { return products; }
            public void setProducts(int products) { this.products = products; }
            
            public int getCustomers() { return customers; }
            public void setCustomers(int customers) { this.customers = customers; }
            
            public int getEmployees() { return employees; }
            public void setEmployees(int employees) { this.employees = employees; }
            
            public int getTransactions() { return transactions; }
            public void setTransactions(int transactions) { this.transactions = transactions; }
        }
        
        public Sample getSample() { return sample; }
        public void setSample(Sample sample) { this.sample = sample; }
    }

    @Bean
    @ConfigurationProperties(prefix = "bakery.data")
    public DataInitializationProperties dataInitializationProperties() {
        return new DataInitializationProperties();
    }

    // =================== BUSINESS CONFIGURATION ===================
    
    /**
     * 🏪 Business Configuration Properties
     * 
     * Thông tin cấu hình business logic:
     * - Thông tin tiệm bánh
     * - Cài đặt loyalty system
     * - Discount policies
     */
    @ConfigurationProperties(prefix = "bakery.business")
    public static class BusinessProperties {
        private String name = "Tiệm Bánh Phenikaa";
        private String address = "Hà Nội, Việt Nam";
        private String phone = "+84 123 456 789";
        private String email = "contact@phenikaa-bakery.com";
        
        // Loyalty System Configuration
        private Loyalty loyalty = new Loyalty();
        
        public static class Loyalty {
            private int pointsPerVnd = 10000; // 1 point per 10,000 VND
            private int pointValueVnd = 1000; // 1 point = 1,000 VND
            private double vipThreshold = 5000000.0; // 5M VND for VIP
            private double premiumThreshold = 2000000.0; // 2M VND for Premium
            
            // Getters and Setters
            public int getPointsPerVnd() { return pointsPerVnd; }
            public void setPointsPerVnd(int pointsPerVnd) { this.pointsPerVnd = pointsPerVnd; }
            
            public int getPointValueVnd() { return pointValueVnd; }
            public void setPointValueVnd(int pointValueVnd) { this.pointValueVnd = pointValueVnd; }
            
            public double getVipThreshold() { return vipThreshold; }
            public void setVipThreshold(double vipThreshold) { this.vipThreshold = vipThreshold; }
            
            public double getPremiumThreshold() { return premiumThreshold; }
            public void setPremiumThreshold(double premiumThreshold) { this.premiumThreshold = premiumThreshold; }
        }
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public Loyalty getLoyalty() { return loyalty; }
        public void setLoyalty(Loyalty loyalty) { this.loyalty = loyalty; }
    }

    @Bean
    @ConfigurationProperties(prefix = "bakery.business")
    public BusinessProperties businessProperties() {
        return new BusinessProperties();
    }

    // =================== STARTUP MESSAGE ===================
    
    /**
     * 📢 Database Configuration Startup Message
     */
    @Bean
    public String databaseConfigMessage(@Value("${spring.profiles.active:dev}") String activeProfile) {
        String message = switch (activeProfile) {
            case "dev" -> "🔧 Development Mode: H2 In-Memory Database | Console: http://localhost:8080/h2-console";
            case "test" -> "🧪 Test Mode: H2 Isolated Database for Testing";
            case "prod" -> "🚀 Production Mode: MySQL Cloud Database";
            case "staging" -> "🎭 Staging Mode: MySQL Testing Environment";
            default -> "⚙️ Default Mode: H2 In-Memory Database";
        };
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("💾 DATABASE CONFIGURATION");
        System.out.println(message);
        System.out.println("=".repeat(80) + "\n");
        
        return message;
    }
}