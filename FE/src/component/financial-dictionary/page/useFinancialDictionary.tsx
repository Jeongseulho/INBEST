import { useQuery } from "react-query";
import { getDictionary } from "../../../api/account";
import { useState, useEffect } from "react";
export const useFinancialDictionary = () => {
  const [inputText, setInputText] = useState("");
  const { data } = useQuery(["getDictionary", inputText], () => getDictionary(inputText), { retry: 5 });
  const [dictionary, setDictionary] = useState(data?.financial_dictionary);
  useEffect(() => {
    setDictionary(data?.financial_dictionary);
  }, [inputText, data]);
  const highlightText = (text?: string, searchText?: string) => {
    const regex = new RegExp(`(${searchText})`, "gi");
    return text?.replace(regex, (match: string) => `<mark>${match}</mark>`);
  };
  return { dictionary, setDictionary, inputText, setInputText, highlightText };
};
