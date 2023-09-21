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
      price: 60000,
    },
    {
      logo: "",
      name: "삼성전자",
      code: "005930",
      price: 60000,
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
