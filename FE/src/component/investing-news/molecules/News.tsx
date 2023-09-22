interface Props {
  title: string;
  content: string;
  imgUrl: string;
  linkUrl: string;
}

const News = ({ title, content, imgUrl, linkUrl }: Props) => {
  return (
    <a
      className=" flex items-center gap-3 hover:bg-gray-200 hover:bg-opacity-70 rounded-md transition-colors duration-300"
      href={linkUrl}
      target="_black"
    >
      <img src={imgUrl} width={100} />
      <div className=" flex flex-col">
        <p>{title}</p>
        <p className=" text-myGray font-light text-sm">{content}</p>
      </div>
    </a>
  );
};
export default News;
