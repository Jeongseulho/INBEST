import MainNews from "../organisms/MainNews";
import IndustryNews from "../organisms/IndustryNews";
import BreakingNews from "../organisms/BreakingNews";

interface Props {
  groupCode: string | undefined;
}

const InvestingNews = ({ groupCode }: Props) => {
  console.log(groupCode);
  return (
    <div className=" grid grid-cols-2 grid-rows-3 gap-4">
      <MainNews />
      <BreakingNews />
      <IndustryNews />
    </div>
  );
};
export default InvestingNews;
