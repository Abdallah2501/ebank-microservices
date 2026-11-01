package ma.emsi.ebank.ebankservice.clients;

import ma.emsi.ebank.ebankservice.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerRestClientFallback implements CustomerRestClient {

    @Override
    public Customer findCustomerById(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Not Available");
        customer.setEmail("Not Available");
        return customer;
    }
}
