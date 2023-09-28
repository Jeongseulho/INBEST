import SearchInput from "../organisms/SearchInput";
import SearchList from "../organisms/SearchList";

interface Props {
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const InvestingSearch = ({ setCompanyCode }: Props) => {
  const searchList = [
    {
      logo: "",
      name: "삼성전자",
      code: "005930",
      type: "국내주식",
    },
    {
      logo: "",
      name: "삼성전자",
      code: "005930",
      type: "해외주식",
    },
    {
      logo: "",
      name: "삼성전자",
      code: "005930",
      type: "가상자산",
    },
  ];

  return (
    <div className=" flex gap-4">
      <SearchList searchList={searchList} setCompanyCode={setCompanyCode} />
      <SearchInput />
    </div>
  );
};
export default InvestingSearch;
