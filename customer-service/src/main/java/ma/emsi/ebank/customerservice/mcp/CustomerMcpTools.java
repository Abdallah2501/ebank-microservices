package ma.emsi.ebank.customerservice.mcp;

import ma.emsi.ebank.customerservice.entities.Customer;
import ma.emsi.ebank.customerservice.repositories.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMcpTools {

    private final CustomerRepository customerRepository;

    public CustomerMcpTools(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @McpTool(name = "get_all_customers",
            description = "Retourne la liste de tous les clients de la banque")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @McpTool(name = "get_customer_by_id",
            description = "Retourne un client à partir de son identifiant")
    public Customer getCustomerById(
            @McpToolParam(description = "Identifiant du client", required = true) Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable : " + id));
    }

    @McpTool(name = "create_customer", description = "Crée un nouveau client")
    public Customer createCustomer(
            @McpToolParam(description = "Nom du client", required = true) String name,
            @McpToolParam(description = "Email du client", required = true) String email) {
        return customerRepository.save(Customer.builder().name(name).email(email).build());
    }
}
