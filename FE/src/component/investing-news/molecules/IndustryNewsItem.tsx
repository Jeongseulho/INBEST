import { truncateContent } from "../../../util/formatContent";

interface Props {
  title: string;
  content: string;
  imgUrl: string;
  linkUrl: string;
}

const IndustryNewsItem = ({ title, content, imgUrl, linkUrl }: Props) => {
  return (
    <a
      className=" col-span-1 flex items-center gap-3 hover:bg-gray-200 hover:bg-opacity-70 rounded-md transition-colors duration-300"
      href={linkUrl}
      target="_black"
    >
      <img src={imgUrl} style={{ width: "100px", height: "100px", objectFit: "cover", display: "block" }} />
      <div className=" flex flex-col">
        <p>{title}</p>
        <p className=" text-myGray font-light text-sm">{truncateContent(content)}</p>
      </div>
    </a>
  );
};
export default IndustryNewsItem;
