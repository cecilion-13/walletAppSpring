package com.harsh.walletApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(1)
    @NotNull(message = "Amount can't be null")
    private Double amount;
    private String description;
    @Min(1)
    @Max(3)
    private int type;//1 for Income, 2 for expense, 3 for transfer within accounts
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date transactionDate;

    //Mapping for an wallet
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id", nullable = false)
    @JsonIgnore
    private Wallet wallet;

    @PrePersist
    public void setTransactionDate(){ this.transactionDate = new Date(); }

}
