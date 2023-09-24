package com.jrjr.invest.group.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class SimulationDTO {

	@NotNull
	private String title;

	@NotNull
	private Integer period;

	@NotNull
	private Long seedMoney;

	@NotNull
	private List<InvitedUserDTO> invitedUsersList;


}
