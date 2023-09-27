package com.jrjr.invest.simulation.dto;

import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class SimulationUserDTO {

    private Long seq;
    private Simulation simulation;
    private User user;
    private Long seedMoney;
    private Long currentMoney;
    private Integer previousRank;
    private Integer currentRank;
    private Boolean isExited;
}