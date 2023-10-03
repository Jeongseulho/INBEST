import { motion } from "framer-motion";
import Summary from "../organisms/Summary";
import StockChart from "../organisms/StockChart";
import FinancialAndNews from "../organisms/FinancialAndNews";
import { useState } from "react";
import Trade from "../organisms/Trade";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import AbroadChart from "../organisms/AbroadChart";
import AbroadTrade from "../organisms/AbroadTrade";
import CryptoChart from "../organisms/CryptoChart";
import CryptoTrade from "../organisms/CryptoTrade";

interface Props {
  companyInfo: CompanyInfo;
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}

const InvestingDetail = ({ companyInfo, setCompanyInfo }: Props) => {
  const isDomestic = companyInfo.type === 0;
  const isAbroad = companyInfo.type === 1;
  const isCrypto = companyInfo.type === 2;
  const [companyDetailTab, setCompanyDetailTab] = useState(isDomestic ? "summary" : "chart");
  return (
    <>
      <motion.div
        initial="hidden"
        animate="visible"
        exit="exit"
        variants={{
          hidden: { opacity: 0 },
          visible: { opacity: 0.5 },
          exit: { opacity: 0 },
        }}
        transition={{
          duration: 0.5,
        }}
        className="fixed inset-0 bg-black z-10"
        onClick={() => setCompanyInfo({ code: "", name: "", type: 0, logo: "" })}
      ></motion.div>

      <motion.div
        initial="hidden"
        animate="visible"
        exit="exit"
        variants={{
          hidden: { x: "100%" },
          visible: { x: "0%" },
          exit: { x: "100%" },
        }}
        transition={{
          type: "spring",
          stiffness: 100,
          damping: 20,
        }}
        className="absolute top-0 right-0 w-3/5 min-h-[120vh] bg-gray-100 z-50 p-4"
      >
        <div className=" border-b-2 border-gray-600 border-opacity-50 flex justify-between items-end">
          <div className=" flex items-center gap-2">
            <img src={companyInfo.logo} width={40} />
            <h3>{companyInfo.name}</h3>
          </div>
          <div className=" flex h-full w-1/2 justify-center gap-10">
            {isDomestic ? (
              <>
                <button
                  className={`border-b-4 hover:text-mainMoreDark transition-colors duration-300 ${
                    companyDetailTab === "summary"
                      ? " text-mainMoreDark border-main "
                      : " text-gray-500 border-opacity-0"
                  }`}
                  onClick={() => setCompanyDetailTab("summary")}
                >
                  분석 요약
                </button>
                <button
                  className={`border-b-4 hover:text-mainMoreDark transition-colors duration-300  ${
                    companyDetailTab === "news" ? "text-mainMoreDark border-main" : "text-gray-500 border-opacity-0 "
                  }`}
                  onClick={() => setCompanyDetailTab("news")}
                >
                  재무제표 / 뉴스
                </button>
              </>
            ) : (
              <></>
            )}
            <button
              className={`border-b-4 hover:text-mainMoreDark transition-colors duration-300  ${
                companyDetailTab === "chart" ? "text-mainMoreDark border-main" : "text-gray-500 border-opacity-0 "
              }`}
              onClick={() => setCompanyDetailTab("chart")}
            >
              시세 차트
            </button>
            <button
              className={`border-b-4 hover:text-mainMoreDark transition-colors duration-300  ${
                companyDetailTab === "trade" ? "text-mainMoreDark border-main" : "text-gray-500 border-opacity-0 "
              }`}
              onClick={() => setCompanyDetailTab("trade")}
            >
              매도 / 매수
            </button>
          </div>
        </div>
        <div className=" p-4">
          {isDomestic && companyDetailTab === "summary" && <Summary companyInfo={companyInfo} />}
          {isDomestic && companyDetailTab === "news" && <FinancialAndNews companyInfo={companyInfo} />}
          {isDomestic && companyDetailTab === "chart" && <StockChart companyInfo={companyInfo} />}
          {isDomestic && companyDetailTab === "trade" && <Trade companyInfo={companyInfo} />}
          {isAbroad && companyDetailTab === "chart" && <AbroadChart companyInfo={companyInfo} />}
          {isAbroad && companyDetailTab === "trade" && <AbroadTrade companyInfo={companyInfo} />}
          {isCrypto && companyDetailTab === "chart" && <CryptoChart companyInfo={companyInfo} />}
          {isCrypto && companyDetailTab === "trade" && <CryptoTrade />}
        </div>
      </motion.div>
    </>
  );
};

export default InvestingDetail;
