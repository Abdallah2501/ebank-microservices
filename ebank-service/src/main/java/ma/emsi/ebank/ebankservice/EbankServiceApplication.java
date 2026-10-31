package ma.emsi.ebank.ebankservice;

import ma.emsi.ebank.ebankservice.entities.BankAccount;
import ma.emsi.ebank.ebankservice.enums.AccountType;
import ma.emsi.ebank.ebankservice.repositories.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository) {
        return args -> {
            for (long customerId = 1; customerId <= 3; customerId++) {
                bankAccountRepository.save(BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .balance(10000 + Math.random() * 90000)
                        .createdAt(LocalDate.now())
                        .currency("MAD")
                        .type(AccountType.CURRENT_ACCOUNT)
                        .customerId(customerId)
                        .build());
                bankAccountRepository.save(BankAccount.builder()
                        .accountId(UUID.randomUUID().toString())
                        .balance(10000 + Math.random() * 90000)
                        .createdAt(LocalDate.now())
                        .currency("MAD")
                        .type(AccountType.SAVING_ACCOUNT)
                        .customerId(customerId)
                        .build());
            }
        };
    }
}
