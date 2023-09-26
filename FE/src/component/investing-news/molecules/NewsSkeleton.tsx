import Skeleton from "react-loading-skeleton";

const NewsSkeleton = () => {
  return (
    <div className=" flex h-[100px] gap-2">
      <Skeleton inline={true} height={100} containerClassName=" w-[100px]" />
      <div className=" flex flex-col grow">
        <Skeleton inline={true} height={30} containerClassName=" grow w-2/3" />
        <Skeleton inline={true} height={20} containerClassName=" grow w-full" />
        <Skeleton inline={true} height={20} containerClassName=" grow w-full" />
        <Skeleton inline={true} height={20} containerClassName=" grow w-full" />
      </div>
    </div>
  );
};
export default NewsSkeleton;
