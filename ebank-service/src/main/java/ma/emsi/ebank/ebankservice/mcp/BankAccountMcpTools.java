package ma.emsi.ebank.ebankservice.mcp;

import ma.emsi.ebank.ebankservice.entities.BankAccount;
import ma.emsi.ebank.ebankservice.enums.AccountType;
import ma.emsi.ebank.ebankservice.repositories.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BankAccountMcpTools {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountMcpTools(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @McpTool(name = "get_all_accounts", description = "Retourne la liste de tous les comptes bancaires")
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    @McpTool(name = "get_account_by_id", description = "Retourne un compte bancaire à partir de son identifiant")
    public BankAccount getAccountById(
            @McpToolParam(description = "Identifiant du compte", required = true) String accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Compte introuvable : " + accountId));
    }

    @McpTool(name = "get_accounts_by_customer", description = "Retourne les comptes bancaires d'un client")
    public List<BankAccount> getAccountsByCustomer(
            @McpToolParam(description = "Identifiant du client", required = true) Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId);
    }

    @McpTool(name = "create_account", description = "Crée un compte bancaire pour un client")
    public BankAccount createAccount(
            @McpToolParam(description = "Identifiant du client", required = true) Long customerId,
            @McpToolParam(description = "Solde initial", required = true) double balance,
            @McpToolParam(description = "Devise, par exemple MAD", required = true) String currency,
            @McpToolParam(description = "Type : CURRENT_ACCOUNT ou SAVING_ACCOUNT", required = true) String type) {
        return bankAccountRepository.save(BankAccount.builder()
                .accountId(UUID.randomUUID().toString())
                .customerId(customerId)
                .balance(balance)
                .currency(currency)
                .type(AccountType.valueOf(type))
                .createdAt(LocalDate.now())
                .build());
    }
}
