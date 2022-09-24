package com.harsh.walletApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 30)
    @NotBlank(message = "Name can't be blank")
    private String name;
    @Size(max = 30)
    private String accountNumber;
    @Size(max = 30)
    private String description;
    @Min(1)
    @Max(3)
    private Integer priority; // 1=high, 2=low, 3=Low
    private Double currentBalance;

    //Mapping for relation between a wallet and it's transaction
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "wallet", orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions;

    @PrePersist
    public void setBalance(){ this.currentBalance = new Double(0); }
}
