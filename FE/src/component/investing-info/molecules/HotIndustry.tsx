import ICT from "../../../asset/image/ICT.png";
import factory from "../../../asset/image/factory.png";
import shop from "../../../asset/image/shop.png";
import tech from "../../../asset/image/tech.png";
import construction from "../../../asset/image/construction.png";
import financial from "../../../asset/image/financial.png";
import restaurant from "../../../asset/image/restaurant.png";
import estate from "../../../asset/image/estate.png";
import rent from "../../../asset/image/rent.png";
import warehouse from "../../../asset/image/warehouse.png";
import leisure from "../../../asset/image/leisure.png";
import agriculture from "../../../asset/image/agriculture.png";
import mine from "../../../asset/image/mine.png";
import recycle from "../../../asset/image/recycle.png";
import gas from "../../../asset/image/gas.png";
import etc from "../../../asset/image/etc.png";
import education from "../../../asset/image/education.png";

interface Props {
  topIndustry: {
    industry: string;
    amount: number;
  };
}

const HotIndustry = ({ topIndustry }: Props) => {
  const industryImg: {
    [key: string]: string;
  } = {
    정보통신: ICT,
    제조: factory,
    도매소매: shop,
    기술서비스: tech,
    건설: construction,
    금융: financial,
    숙박음식점: restaurant,
    부동산: estate,
    임대서비스: rent,
    운수창고: warehouse,
    여가서비스: leisure,
    농업_임업_어업: agriculture,
    교육서비스: education,
    광업: mine,
    전기가스: gas,
    원료재생: recycle,
    기타: etc,
  };
  return (
    <div className=" flex items-center gap-2 flex-col">
      <img src={industryImg[topIndustry.industry]} width={100} />
      <p>{topIndustry.industry.replace("_", " ")}</p>
      <p className=" text-myGray">거래량 : {topIndustry.amount}</p>
    </div>
  );
};
export default HotIndustry;
