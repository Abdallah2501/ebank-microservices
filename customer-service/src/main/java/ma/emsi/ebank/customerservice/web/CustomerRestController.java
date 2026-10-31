package ma.emsi.ebank.customerservice.web;

import ma.emsi.ebank.customerservice.entities.Customer;
import ma.emsi.ebank.customerservice.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerRepository customerRepository;

    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> customers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer customerById(@PathVariable Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}