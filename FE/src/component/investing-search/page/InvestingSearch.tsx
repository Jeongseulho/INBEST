import SearchInput from "../organisms/SearchInput";
import SearchList from "../organisms/SearchList";
import { useQuery } from "react-query";
import { getSearchCompany } from "../../../api/investingStockInfo";
import { useState } from "react";

interface Props {
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const InvestingSearch = ({ setCompanyCode }: Props) => {
  const [searchCompanyKeyword, setSearchCompanyKeyword] = useState<string>("");
  const onChangeSearchCompanyKeyword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchCompanyKeyword(e.target.value);
  };
  const { data, isLoading } = useQuery(
    ["searchCompany", searchCompanyKeyword],
    () => getSearchCompany(searchCompanyKeyword),
    {
      enabled: searchCompanyKeyword.length > 0,
    }
  );

  return (
    <div className=" flex gap-4">
      <SearchList searchList={data || []} setCompanyCode={setCompanyCode} isLoading={isLoading} />
      <SearchInput
        searchCompanyKeyword={searchCompanyKeyword}
        onChangeSearchCompanyKeyword={onChangeSearchCompanyKeyword}
      />
    </div>
  );
};
export default InvestingSearch;
