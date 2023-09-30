import { truncateContent } from "../../../util/formatContent";
import { COMPANY_TYPE } from "../../../constant/COMPANY_TYPE";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";

interface Props {
  name: string;
  code: string;
  type: number;
  index: number;
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}

const SearchItem = ({ name, code, type, index, setCompanyInfo }: Props) => {
  return (
    <div
      onClick={() =>
        setCompanyInfo({
          name,
          code,
        })
      }
      className=" flex justify-center gap-20 border-b-2 items-center py-2 cursor-pointer hover:bg-gray-400 hover:bg-opacity-20 transition-colors duration-300"
    >
      <p className=" w-2 text-center">{index}</p>
      <p className=" w-28 text-center">{truncateContent(name, 6)}</p>
      <p className=" w-28 text-center">{code}</p>
      <p className=" w-16 text-center">{COMPANY_TYPE[type]}</p>
    </div>
  );
};
export default SearchItem;
