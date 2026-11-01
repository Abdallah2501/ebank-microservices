package ma.emsi.ebank.ebankservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String email;
}