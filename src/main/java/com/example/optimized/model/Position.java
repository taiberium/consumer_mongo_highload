package com.example.optimized.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Document
public class Position {

    @Id
    private String id;

    @NotNull
    @Indexed
    private String account;
    @NotNull
    private String isin;
    private BigDecimal t0;
    @NotNull
    private BigDecimal tn;

}
