import TotalInfoItem from "../molecules/TotalInfoItem";
import investing_status from "../../../asset/image/investing_status.png";
import { useQuery } from "react-query";
import { getInvestingStatus } from "../../../api/group";

const InvestingTotalInfo = () => {
  const { data, isLoading } = useQuery(["investingTotalInfo"], getInvestingStatus);
  console.log(data, isLoading);
  return (
    <div className=" shadow-component h-full p-4 flex flex-col gap-10 w-11/12">
      <div className=" flex items-center gap-2">
        <img src={investing_status} width={70} />
        <h3 className=" mb-6">모의투자 진행 현황</h3>
      </div>
      <div className=" flex items-center gap-8 justify-around">
        <TotalInfoItem
          title="총 회원"
          number={data?.totalUserNum || 1}
          scale="명"
          fluctuation={data?.totalUserNumFluctuation || 1}
        />
        <TotalInfoItem
          title="접속중 회원"
          number={data?.currentUserNum || 1}
          scale="명"
          fluctuation={data?.currentUserNumFluctuation || 1}
        />
        <TotalInfoItem
          title="모의투자 진행 인원"
          number={data?.inprogressUserNum || 1}
          scale="명"
          fluctuation={data?.inprogressUserNumFluctuation || 1}
        />
        <TotalInfoItem
          title="모의투자 진행 그룹"
          number={data?.inprogressGroupNum || 1}
          scale="개"
          fluctuation={data?.inprogressGroupNumFluctuation || 1}
        />
        <TotalInfoItem
          title="모의투자 완료 그룹"
          number={data?.finishedGroupNum || 1}
          scale="개"
          fluctuation={data?.finishedGroupNumFluctuation || 1}
        />
        <TotalInfoItem
          title="평균 수익률"
          number={data?.revenueRate || 1}
          scale="%"
          fluctuation={data?.revenueRateFluctuation || 1}
        />
      </div>
    </div>
  );
};
export default InvestingTotalInfo;
