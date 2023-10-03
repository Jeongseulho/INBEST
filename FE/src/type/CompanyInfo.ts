export type CompanySummary = [
  {
    seq: number;
    company_seq_id: number;
    stability: number;
    size: number;
    growth: number;
    profitability: number;
    revenue_growth: number;
    operating_profit_growth: number;
  },
];

export interface Income {
  net_income_2023: number | null;
  net_income_2022: number | null;
  net_income_2021: number | null;
}

export interface Revenue {
  revenue_2023: number | null;
  revenue_2022: number | null;
  revenue_2021: number | null;
}

export type FinancialStatements = [
  {
    seq: number;
    company_seq_id: number;
    current_assets: number;
    non_current_assets: number;
    total_assets: number;
    current_liabilities: number;
    non_current_liabilities: number;
    total_liabilities: number;
    capital: number;
    total_equity: number;
    revenue: number;
    gross_profit: number;
    operating_profit: number;
    income_before_tax: number;
    income_tax_expense: number;
    net_income: number;
    total_asset_growth_rate: number;
    revenue_asset_growth_rate: number;
    net_income_growth_rate: number;
    operating_profit_margin: number;
    roe: number;
    roic: number;
    debt_to_equity_ratio: number;
  },
];
