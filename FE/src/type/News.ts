export interface MainNews {
  title: string;
  content: string;
  image_url: string;
  link_url: string;
}

export interface BreakingNews extends MainNews {
  time: string;
}

export interface IndustryNews extends MainNews {}

export interface CompanyNews extends Omit<BreakingNews, "content"> {
  sentiment_analysis: number;
}
