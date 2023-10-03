import { useQuery } from "react-query";
import { getDictionary } from "../../../api/account";

export const useFinancialDictionary = () => {
  const { data } = useQuery(["getDictionary"], () => getDictionary());
  const dictionary = data?.financial_dictionary;
  return { dictionary };
};
