import InvestSidebar from "./InvestSidebar";
import { useState } from "react";
import { INVESTING_TAB } from "../../constant/INVESTING_TAB";
import InvestingInfo from "../investing-info/page/InvestingInfo";
import InvestingMyInfo from "../investing-myinfo/page/InvestingMyInfo";
import InvestingNews from "../investing-news/page/InvestingNews";
import InvestingDomestic from "../investing-domestic/InvestingDomestic";
import InvestingSearch from "../investing-search/page/InvestingSearch";
import InvestingCrypto from "../investing-crypto/InvestingCrypto";
import InvestingAbroad from "../investing-abroad/InvestingAbroad";
import InvestingLayout from "../common/InvestingLayout";
import InvestingDetail from "../Investing-detail/page/InvestingDetail";
import DetailPortal from "../common/DetailPortal";
import { AnimatePresence } from "framer-motion";

const Investing = () => {
  const [activeTab, setActiveTab] = useState(INVESTING_TAB.INFO);
  const [companyCode, setCompanyCode] = useState("");

  return (
    <div className=" flex max-w-[100vw] ">
      <InvestSidebar activeTab={activeTab} setActiveTab={setActiveTab} />
      <InvestingLayout>
        {activeTab === INVESTING_TAB.INFO && <InvestingInfo />}
        {activeTab === INVESTING_TAB.MY_INFO && <InvestingMyInfo />}
        {activeTab === INVESTING_TAB.SEARCH && <InvestingSearch setCompanyCode={setCompanyCode} />}
        {activeTab === INVESTING_TAB.DOMESTIC && <InvestingDomestic />}
        {activeTab === INVESTING_TAB.ABROAD && <InvestingAbroad setCompanyCode={setCompanyCode} />}
        {activeTab === INVESTING_TAB.CRYPTO && <InvestingCrypto setCompanyCode={setCompanyCode} />}
        {activeTab === INVESTING_TAB.NEWS && <InvestingNews />}

        <DetailPortal>
          <AnimatePresence>
            {companyCode && <InvestingDetail companyCode={companyCode} setCompanyCode={setCompanyCode} />}
          </AnimatePresence>
        </DetailPortal>
      </InvestingLayout>
    </div>
  );
};
export default Investing;
