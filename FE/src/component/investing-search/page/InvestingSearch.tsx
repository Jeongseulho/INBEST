import SearchInput from "../organisms/SearchInput";
import SearchList from "../organisms/SearchList";
import { useQuery } from "react-query";
import { getSearchCompany } from "../../../api/investingStockInfo";
import { useState } from "react";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";

interface Props {
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}

const InvestingSearch = ({ setCompanyInfo }: Props) => {
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
      <SearchList searchList={data || []} setCompanyInfo={setCompanyInfo} isLoading={isLoading} />
      <SearchInput
        searchCompanyKeyword={searchCompanyKeyword}
        onChangeSearchCompanyKeyword={onChangeSearchCompanyKeyword}
      />
    </div>
  );
};
export default InvestingSearch;
