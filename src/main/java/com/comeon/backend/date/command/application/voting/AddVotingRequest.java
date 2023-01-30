package com.comeon.backend.date.command.application.voting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
public class AddVotingRequest {

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String date;

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
