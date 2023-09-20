import SearchInput from "../organisms/SearchInput";
import SearchList from "../organisms/SearchList";

const InvestingSearch = () => {
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
      <SearchList searchList={searchList} />
      <SearchInput />
    </div>
  );
};
export default InvestingSearch;
