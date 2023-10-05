export interface FinancialDictionary {
  seq: string;
  title: string;
  content: string;
}
export interface GetFinancialDictionary {
  success: boolean;
  financial_dictionary: FinancialDictionary[];
}
