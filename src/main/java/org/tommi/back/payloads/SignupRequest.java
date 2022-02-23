package org.tommi.back.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotNull
    @Min(14)
    @Max(100)
    private int age;

    @NotNull
    @Min(value = 40)
    @Max(value = 150)
    private double weigth;

    @NotNull
    @Min(value = 140)
    @Max(value = 220)
    private double heigth;

    @NotNull
    @Min(value = 20)
    @Max(value = 300)
    private double bestSquat;

    @NotNull
    @Min(value = 20)
    @Max(value = 200)
    private double bestBenchPress;

    @NotNull
    @Min(value = 20)
    @Max(value = 200)
    private double bestBarbellRow;

    @NotNull
    @Min(value = 20)
    @Max(value = 150)
    private double bestOverheadPress;

    @NotNull
    @Min(value = 20)
    @Max(value = 300)
    private double bestDeadlift;
}