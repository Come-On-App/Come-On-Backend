package com.comeon.backend.date.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingConfirmDatesParam {

    // TODO "\"^\\d{4}-(0[1-9]|1[012])$\"와 일치해야 합니다" <- 메시지 수정
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])$")
    private String calendar = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

    public int getYear() {
        return Integer.parseInt(this.calendar.split("-")[0]);
    }

    public int getMonth() {
        return Integer.parseInt(this.calendar.split("-")[1]);
    }
}
