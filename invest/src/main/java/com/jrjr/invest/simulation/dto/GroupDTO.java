package com.jrjr.invest.simulation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class GroupDTO {

    @NotNull
    private Long simulationSeq;
    private String title;
    private Integer currentMemberNum;
    private Long seedMoney;
    private Integer averageTier;
    private String progressState;
    private Integer period;
}
