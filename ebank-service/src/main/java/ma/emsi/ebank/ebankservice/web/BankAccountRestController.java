package ma.emsi.ebank.ebankservice.web;

import ma.emsi.ebank.ebankservice.clients.CustomerRestClient;
import ma.emsi.ebank.ebankservice.entities.BankAccount;
import ma.emsi.ebank.ebankservice.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountRestController {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRestClient customerRestClient;

    public BankAccountRestController(BankAccountRepository bankAccountRepository,
                                     CustomerRestClient customerRestClient) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping
    public List<BankAccount> accounts() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        accounts.forEach(a -> a.setCustomer(customerRestClient.findCustomerById(a.getCustomerId())));
        return accounts;
    }

    @GetMapping("/{accountId}")
    public BankAccount accountById(@PathVariable String accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId()));
        return account;
    }

    @GetMapping("/customer/{customerId}")
    public List<BankAccount> accountsByCustomer(@PathVariable Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId);
    }

    @PostMapping
    public BankAccount save(@RequestBody BankAccount account) {
        account.setAccountId(UUID.randomUUID().toString());
        account.setCreatedAt(LocalDate.now());
        return bankAccountRepository.save(account);
    }
}