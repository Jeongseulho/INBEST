import InvestSidebar from "./InvestSidebar";
import { useState } from "react";
import { INVESTING_TAB } from "../../constant/INVESTING_TAB";
import InvestingInfo from "../investing-info/page/InvestingInfo";
import InvestingMyInfo from "../investing-myinfo/page/InvestingMyInfo";
import InvestingNews from "../investing-news/page/InvestingNews";
import InvestingDomestic from "../investing-domestic/page/InvestingDomestic";
import InvestingSearch from "../investing-search/page/InvestingSearch";
import InvestingCrypto from "../investing-crypto/page/InvestingCrypto";
import InvestingAbroad from "../investing-abroad/page/InvestingAbroad";
import { useParams } from "react-router-dom";
import InvestingLayout from "../common/InvestingLayout";

const Investing = () => {
  const [activeTab, setActiveTab] = useState(INVESTING_TAB.INFO);
  const { groupCode } = useParams();

  return (
    <div className=" flex max-w-[100vw] ">
      <InvestSidebar activeTab={activeTab} setActiveTab={setActiveTab} />
      <InvestingLayout>
        {activeTab === INVESTING_TAB.INFO && <InvestingInfo groupCode={groupCode} />}
        {activeTab === INVESTING_TAB.MY_INFO && <InvestingMyInfo groupCode={groupCode} />}
        {activeTab === INVESTING_TAB.NEWS && <InvestingNews groupCode={groupCode} />}
        {activeTab === INVESTING_TAB.DOMESTIC && <InvestingDomestic groupCode={groupCode} />}
        {activeTab === INVESTING_TAB.SEARCH && <InvestingSearch groupCode={groupCode} />}
        {activeTab === INVESTING_TAB.CRYPTO && <InvestingCrypto groupCode={groupCode} />}
        {activeTab === INVESTING_TAB.ABROAD && <InvestingAbroad groupCode={groupCode} />}
      </InvestingLayout>
    </div>
  );
};
export default Investing;
