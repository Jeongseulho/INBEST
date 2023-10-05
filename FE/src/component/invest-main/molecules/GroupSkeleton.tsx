import Skeleton from "react-loading-skeleton";

const GroupSkeleton = () => {
  return (
    <div className=" flex justify-between h-16 gap-2">
      <Skeleton inline={true} height={64} containerClassName=" w-1/12" />
      <Skeleton inline={true} height={64} containerClassName=" w-2/12" />
      <Skeleton inline={true} height={64} containerClassName=" w-[20%]" />
      <Skeleton inline={true} height={64} containerClassName=" w-[20%]" />
      <Skeleton inline={true} height={64} containerClassName=" w-[20%]" />
      <Skeleton inline={true} height={64} containerClassName=" grow" />
    </div>
  );
};
export default GroupSkeleton;
