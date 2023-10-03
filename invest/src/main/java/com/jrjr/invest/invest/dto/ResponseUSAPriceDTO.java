package com.jrjr.invest.invest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResponseUSAPriceDTO {
	@Schema(description = "실시간종목코드(라고는 하는데 이것저것 섞여있는 값이라 종목 코드는 symb를 사용하는 것이 좋음)")
	private String rsym;

	@Schema(description = "종목코드")
	private String symb;

	@Schema(description = "소숫점자리수")
	private String zdiv;

	@Schema(description = "현지일자")
	private String xymd;

	@Schema(description = "현지시간")
	private String xhms;

	@Schema(description = "한국일자")
	private String kymd;

	@Schema(description = "한국시간")
	private String khms;

	@Schema(description = "매수총잔량")
	private Integer bvol;

	@Schema(description = "매도총잔량")
	private Integer avol;

	@Schema(description = "매수총잔량대비")
	private Integer bdvl;

	@Schema(description = "매도총잔량대비")
	private Integer advl;

	@Schema(description = "매수호가1")
	private Integer pbid1;

	@Schema(description = "매도호가1")
	private Integer pask1;

	@Schema(description = "매수잔량1")
	private Integer vbid1;

	@Schema(description = "매도잔량1")
	private Integer vask1;

	@Schema(description = "매수잔량대비1")
	private Integer dbid1;

	@Schema(description = "매도잔량대비1")
	private Integer dask1;

	@Schema(description = "매수호가2")
	private Integer pbid2;

	@Schema(description = "매도호가2")
	private Integer pask2;

	@Schema(description = "매수잔량2")
	private Integer vbid2;

	@Schema(description = "매도잔량2")
	private Integer vask2;

	@Schema(description = "매수잔량대비2")
	private Integer dbid2;

	@Schema(description = "매도잔량대비2")
	private Integer dask2;

	@Schema(description = "매수호가3")
	private Integer pbid3;

	@Schema(description = "매도호가3")
	private Integer pask3;

	@Schema(description = "매수잔량3")
	private Integer vbid3;

	@Schema(description = "매도잔량3")
	private Integer vask3;

	@Schema(description = "매수잔량대비3")
	private Integer dbid3;

	@Schema(description = "매도잔량대비3")
	private Integer dask3;


	@Schema(description = "매수호가4")
	private Integer pbid4;

	@Schema(description = "매도호가4")
	private Integer pask4;

	@Schema(description = "매수잔량4")
	private Integer vbid4;

	@Schema(description = "매도잔량4")
	private Integer vask4;

	@Schema(description = "매수잔량대비4")
	private Integer dbid4;

	@Schema(description = "매도잔량대비4")
	private Integer dask4;

	@Schema(description = "매수호가5")
	private Integer pbid5;

	@Schema(description = "매도호가5")
	private Integer pask5;

	@Schema(description = "매수잔량5")
	private Integer vbid5;

	@Schema(description = "매도잔량5")
	private Integer vask5;

	@Schema(description = "매수잔량대비5")
	private Integer dbid5;

	@Schema(description = "매도잔량대비5")
	private Integer dask5;
	//
	// @Schema(description = "매수호가6")
	// private String pbid6;
	//
	// @Schema(description = "매도호가6")
	// private String pask6;
	//
	// @Schema(description = "매수잔량6")
	// private String vbid6;
	//
	// @Schema(description = "매도잔량6")
	// private String vask6;
	//
	// @Schema(description = "매수잔량대비6")
	// private String dbid6;
	//
	// @Schema(description = "매도잔량대비6")
	// private String dask6;
	//
	// @Schema(description = "매수호가7")
	// private String pbid7;
	//
	// @Schema(description = "매도호가7")
	// private String pask7;
	//
	// @Schema(description = "매수잔량7")
	// private String vbid7;
	//
	// @Schema(description = "매도잔량7")
	// private String vask7;
	//
	// @Schema(description = "매수잔량대비7")
	// private String dbid7;
	//
	// @Schema(description = "매도잔량대비7")
	// private String dask7;
	//
	// @Schema(description = "매수호가8")
	// private String pbid8;
	//
	// @Schema(description = "매도호가8")
	// private String pask8;
	//
	// @Schema(description = "매수잔량8")
	// private String vbid8;
	//
	// @Schema(description = "매도잔량8")
	// private String vask8;
	//
	// @Schema(description = "매수잔량대비8")
	// private String dbid8;
	//
	// @Schema(description = "매도잔량대비8")
	// private String dask8;
	//
	// @Schema(description = "매수호가9")
	// private String pbid9;
	//
	// @Schema(description = "매도호가9")
	// private String pask9;
	//
	// @Schema(description = "매수잔량9")
	// private String vbid9;
	//
	// @Schema(description = "매도잔량9")
	// private String vask9;
	//
	// @Schema(description = "매수잔량대비9")
	// private String dbid9;
	//
	// @Schema(description = "매도잔량대비9")
	// private String dask9;
	//
	//
	// @Schema(description = "매수호가10")
	// private String pbid10;
	//
	// @Schema(description = "매도호가10")
	// private String pask10;
	//
	// @Schema(description = "매수잔량10")
	// private String vbid10;
	//
	// @Schema(description = "매도잔량10")
	// private String vask10;
	//
	// @Schema(description = "매수잔량대비10")
	// private String dbid10;
	//
	// @Schema(description = "매도잔량대비10")
	// private String dask10;
}