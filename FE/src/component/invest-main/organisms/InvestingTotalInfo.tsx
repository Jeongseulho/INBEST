import TotalInfoItem from "../molecules/TotalInfoItem";
import investing_status from "../../../asset/image/investing_status.png";
import { useQuery } from "react-query";
import { getInvestingStatus } from "../../../api/group";

const InvestingTotalInfo = () => {
  const { data } = useQuery(["investingTotalInfo"], getInvestingStatus, {
    retry: 3,
  });
  return (
    <div className=" shadow-component h-full p-4 flex flex-col gap-10 w-11/12">
      <div className=" flex items-center gap-2">
        <img src={investing_status} width={70} />
        <h3 className=" mb-6">모의투자 진행 현황</h3>
      </div>
      <div className=" flex items-center gap-8 justify-around">
        <TotalInfoItem
          title="총 회원"
          number={data?.totalUserNum}
          scale="명"
          fluctuation={data?.totalUserNumFluctuation}
        />
        <TotalInfoItem
          title="오늘 접속한 회원"
          number={data?.currentUserNum}
          scale="명"
          fluctuation={data?.currentUserNumFluctuation}
        />
        <TotalInfoItem
          title="모의투자 진행 인원"
          number={data?.inprogressUserNum}
          scale="명"
          fluctuation={data?.inprogressUserNumFluctuation}
        />
        <TotalInfoItem
          title="모의투자 진행 그룹"
          number={data?.inprogressGroupNum}
          scale="개"
          fluctuation={data?.inprogressGroupNumFluctuation}
        />
        <TotalInfoItem
          title="모의투자 완료 그룹"
          number={data?.finishedGroupNum}
          scale="개"
          fluctuation={data?.finishedGroupNumFluctuation}
        />
        <TotalInfoItem
          title="평균 수익률"
          number={data?.revenueRate}
          scale="%"
          fluctuation={data?.revenueRateFluctuation}
        />
      </div>
    </div>
  );
};
export default InvestingTotalInfo;
