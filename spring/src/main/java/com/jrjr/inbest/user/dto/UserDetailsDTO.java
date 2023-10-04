package com.jrjr.inbest.user.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class UserDetailsDTO {

	@Schema(description = "회원의 pk 값")
	Long userSeq;

	@Schema(description = "이메일")
	String email;

	@Schema(description = "닉네임")
	String nickname;

	@Schema(description = "프로필 이미지 url")
	String profileImgSearchName;

	@Schema(description = "팔로잉 수")
	Integer followingNum;

	@Schema(description = "팔로워 수")
	Integer followerNum;

	@Schema(description = "티어 점수")
	Integer tier;

	@Schema(description = "전체 회원 수")
	Long userCnt;

	@Schema(description = "팔로잉 여부")
	Boolean isFollow;

	@Schema(description = "자주 투자한 종목")
	List<IndustryDTO> industries;

	@Schema(description = "날짜 별 티어 점수")
	List<TierByDateDTO> tierByDates;

	@Schema(description = "시뮬레이션 전적")
	List<SimulationRecordDTO> simulationRecords;
}
