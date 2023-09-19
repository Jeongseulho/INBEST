interface Props {
  groupCode: string | undefined;
}

const InvestingNews = ({ groupCode }: Props) => {
  return <div>{groupCode}</div>;
};
export default InvestingNews;
