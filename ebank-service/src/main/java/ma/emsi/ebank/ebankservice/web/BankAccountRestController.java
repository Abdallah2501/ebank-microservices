package ma.emsi.ebank.ebankservice.web;

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

    public BankAccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping
    public List<BankAccount> accounts() {
        return bankAccountRepository.findAll();
    }

    @GetMapping("/{accountId}")
    public BankAccount accountById(@PathVariable String accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
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
