package ma.emsi.ebank.customerservice;

import ma.emsi.ebank.customerservice.entities.Customer;
import ma.emsi.ebank.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(Customer.builder().name("Abdallah").email("abdallah@gmail.com").build());
            customerRepository.save(Customer.builder().name("Sara").email("sara@gmail.com").build());
            customerRepository.save(Customer.builder().name("Youssef").email("youssef@gmail.com").build());
        };
    }
}
