import {
  Accordion,
  AccordionItem,
  AccordionItemHeading,
  AccordionItemButton,
  AccordionItemPanel,
  AccordionItemState,
} from "react-accessible-accordion";
import { useFinancialDictionary } from "./useFinancialDictionary";
import { BsSearch } from "react-icons/bs";

const FinancialDictionary = () => {
  const { dictionary, inputText, setInputText } = useFinancialDictionary();
  return (
    <div>
      <div className="h-20 flex justify-center mt-5">
        <div className="w-2/3 border-b-2 flex items-end justify-between">
          <div className="text-3xl mb-2">금융사전</div>
          <div className="relative w-1/2 mb-2">
            <input
              type="text"
              className="px-3 border-gray-400 border bg-main bg-opacity-10 h-10 w-full rounded-md pe-8"
              placeholder="검색어를 입력하세요"
              value={inputText}
              onChange={(e) => setInputText(e.target.value)}
            />
            <BsSearch
              style={{
                position: "absolute",
                top: "50%",
                transform: "translate(-50%, -50%)",
                right: "0",
                cursor: "pointer",
              }}
            />
          </div>
        </div>
      </div>
      <div className="flex justify-center">
        <Accordion
          allowMultipleExpanded
          allowZeroExpanded={true}
          className="bg-white w-2/3  rounded-lg mt-10  border-black border-opacity-30 border"
        >
          {dictionary &&
            dictionary.map((item, idx) => (
              <AccordionItem className="w-full" key={idx}>
                <AccordionItemHeading>
                  <AccordionItemState>
                    {({ expanded }) => (
                      <AccordionItemButton
                        className={`${expanded ? "accordion_selected_button" : ""}  ${
                          idx === 0 ? "accordion_top_button" : ""
                        } accordion__button`}
                      >
                        {item.title}
                      </AccordionItemButton>
                    )}
                  </AccordionItemState>
                </AccordionItemHeading>
                <AccordionItemPanel>{item.content}</AccordionItemPanel>
              </AccordionItem>
            ))}
        </Accordion>
      </div>
    </div>
  );
};
export default FinancialDictionary;
