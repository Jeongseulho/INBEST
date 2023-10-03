import {
  Accordion,
  AccordionItem,
  AccordionItemHeading,
  AccordionItemButton,
  AccordionItemPanel,
  AccordionItemState,
} from "react-accessible-accordion";
import { useFinancialDictionary } from "./useFinancialDictionary";

const FinancialDictionary = () => {
  const { dictionary } = useFinancialDictionary();
  return (
    <div>
      <div className="h-20 flex justify-center">
        <div className="w-2/3 border-b-2">
          <h3>금융사전</h3>
        </div>
      </div>
      <div className="flex justify-center">
        <Accordion allowMultipleExpanded allowZeroExpanded={true} className="bg-white w-2/3 border rounded-lg mt-20">
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
