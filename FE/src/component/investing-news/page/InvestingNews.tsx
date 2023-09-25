import MainNews from "../organisms/MainNews";
import IndustryNews from "../organisms/IndustryNews";
import BreakingNews from "../organisms/BreakingNews";

const InvestingNews = () => {
  return (
    <div className=" grid grid-cols-2 grid-rows-12 gap-4">
      <MainNews />
      <BreakingNews />
      <IndustryNews />
    </div>
  );
};
export default InvestingNews;
