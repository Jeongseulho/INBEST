interface Props {
  title: string;
  content: string;
  imgUrl: string;
  linkUrl: string;
}

const News = ({ title, content, imgUrl, linkUrl }: Props) => {
  return (
    <a className=" flex items-center" href={linkUrl} target="_black">
      <img src={imgUrl} />
      <div className=" flex flex-col">
        <p>{title}</p>
        <p>{content}</p>
      </div>
    </a>
  );
};
export default News;
